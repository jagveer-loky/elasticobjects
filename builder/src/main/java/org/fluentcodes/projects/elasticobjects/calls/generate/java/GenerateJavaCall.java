package org.fluentcodes.projects.elasticobjects.calls.generate.java;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.generate.GenerateCall;
import org.fluentcodes.projects.elasticobjects.calls.generate.java.helper.FieldHelper;
import org.fluentcodes.projects.elasticobjects.calls.generate.java.helper.JavaImportHelper;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserSqareBracket;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceStoreKeepCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;

import java.util.Date;

import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.FIELD_KEYS;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.MODEL_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.SHAPE_TYPE;

public class GenerateJavaCall extends GenerateCall {
    private final static String JAVA_GEN_MODEL = "/modelKey";

    public final static String GEN_IGNORE = "genIgnore";
    public final static String FIELD_MAP = "fieldMap";
    public final static String TARGET_CONFIG = "JavaClasses";
    private FileConfig targetConfig;
    public GenerateJavaCall() {
        super();
    }

    @Override
    protected boolean init(final EO eo) {
        targetConfig = eo.getConfigsCache().findFile(TARGET_CONFIG);
        targetConfig.hasPermissions(PermissionType.WRITE, eo.getRoles());
        super.init(eo);
        return true;
    }

    @Override
    public Object execute(EO eo) {
        if (eo.isEmpty()) {
            throw new EoException("Eo for generating json configuration files is empty! " + eo.getPathAsString());
        }
        if (!hasOverwrite()) {
            setOverwrite(true);
        }
        eo.set(new Date().toString(), "/date");
        StringBuilder feedback = new StringBuilder();

        init(eo);
        this.setNaturalId(ParserSqareBracket.replacePathValues(getNaturalId(), eo));
        int counter = 1;
        for (final String naturalId: eo.keys()) {
            EO eoModel = eo.getEo(naturalId);
            if (!filter(eoModel)) {
                continue;
            }
            if (hasNaturalId() && !getNaturalId().equals(naturalId)) {
                continue;
            }
            final String modelKey = (String)eoModel.get(MODEL_KEY);
            eo.set(modelKey, JAVA_GEN_MODEL);
            final String shapeType = (String)eoModel.get(Config.PROPERTIES, SHAPE_TYPE);
            if (shapeType == null || shapeType.isEmpty()) {
                continue;
            }
            // Enrich model entry with java stuff.
            FieldHelper fieldMap = new FieldHelper(eoModel.get(FIELD_KEYS), GEN_IGNORE);
            for (String fieldKey : fieldMap.getFieldKeys()) {
                FieldConfig fieldConfig = eo.getConfigsCache().findField(fieldKey);
                fieldMap.merge(fieldKey, fieldConfig);
            }
            eoModel.set(fieldMap.createValueMap(), FIELD_MAP);

            // set import list
            new JavaImportHelper(eoModel);

            // Now execute template template
            TemplateResourceStoreKeepCall templateCall = new TemplateResourceStoreKeepCall(shapeType + "Create.tpl", TARGET_CONFIG);
            String result = templateCall.execute(eoModel);
            feedback.append(result);
            counter++;
        }
        if (counter==1) {
            throw new EoException("No java entry in ModelConfig found matching " + getModule() + " " + getModuleScope() + " " + getNaturalId());
        }
        return feedback;
    }
}
