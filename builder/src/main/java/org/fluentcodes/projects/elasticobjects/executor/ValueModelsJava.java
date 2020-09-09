package org.fluentcodes.projects.elasticobjects.calls.executor;

import org.fluentcodes.projects.elasticobjects.CreatorParams;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.*;

import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.DB_PARAMS;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.MODEL_KEY;


/**
 * Created by werner.diwischek on 20.05.18.
 */
public class ValueModelsJava {
/*
    public final static EO getModelParams(final EO adapter, final String modelKey)  {
        if (adapter == null) {
            throw new Exception("Empty models adapter: Could not find model params for " + modelKey);
        }
        return adapter.getEo(CreatorParams.MODELS_MAP_PATH + Path.DELIMITER + modelKey);
    }

    public final static EO getModelEoParams(final EO modelParams)  {
        if (modelParams == null || modelParams.isEmpty()) {
            throw new Exception("Empty models adapter: Could not find eo params.");
        }
        return modelParams.getEo(EO_STATIC.F_EO_PARAMS);
    }

    public final static EO getModelDbParams(final EO modelParams)  {
        if (modelParams == null || modelParams.isEmpty()) {
            throw new Exception("Empty models adapter: Could not find db params.");
        }
        return modelParams.getEo(DB_PARAMS);
    }

    public final static EO getModelEoParams(final EO adapter, final String modelKey)  {
        return getModelEoParams(getModelParams(adapter, modelKey));
    }

    public final static EO getModelDbParams(final EO adapter, final String modelKey)  {
        return getModelDbParams(getModelParams(adapter, modelKey));
    }

    public final static String createClassHeader(final Object... values)  {
        EO modelParams = ValueParamsHelper.getAdapter(0, values);
        if (modelParams == null || modelParams.isEmpty()) {
            throw new Exception("Empty models adapter: Could not find eo params.");
        }
        final StringBuilder builder = new StringBuilder("public class ");
        builder.append(modelParams.get(MODEL_KEY));
        String superKey = ScalarConverter.toString(modelParams.get(EO_STATIC.F_SUPER_KEY));
        if (superKey != null && !superKey.isEmpty()) {
            builder.append(" extends ");
            builder.append(superKey);
        }
        String interfaces = ScalarConverter.toString(modelParams.get(EO_STATIC.F_INTERFACES));
        if (interfaces != null && !interfaces.isEmpty()) {
            builder.append(" implements ");
            builder.append(interfaces);
        }
        return builder.toString();
    }

    public static String createImport(final Object... values)  {
        EO modelParams = ValueParamsHelper.getAdapter(0, values);
        String packagePath = (String) modelParams.get("packagePath");
        try {
            List fieldKeyList = getFields(modelParams);
            final StringBuilder builder = new StringBuilder("\n");
            Map<String, String> importMap = new HashMap<>();
            for (Object fieldKey : fieldKeyList) {
                String[] modelKeys = ValueFieldsJava.getModelKeys(modelParams, (String) fieldKey);
                if (modelKeys == null) {
                    continue;
                }
                String modelKey = null;
                String childKey = null;
                switch (modelKeys.length) {
                    case 0:
                        continue;
                    case 1:
                        modelKey = modelKeys[0];
                        if (importMap.containsKey(modelKey)) {
                            continue;
                        }
                        break;
                    case 2:
                        modelKey = modelKeys[0];
                        childKey = modelKeys[1];
                        break;
                }
                if (modelKey != null) {
                    if (!importMap.containsKey(modelKey)) {
                        String path = getPackagePath(modelParams, modelKey);
                        importMap.put(modelKey, path);
                    }
                }
                if (childKey != null) {
                    if (!importMap.containsKey(childKey)) {
                        String path = getPackagePath(modelParams, childKey);
                    }
                }
            }
            List<String> importList = new ArrayList<>();
            for (String model : importMap.keySet()) {
                String path = importMap.get(model);
                if (path == null) {
                    continue;
                }
                if (path.equals(packagePath)) {
                    continue;
                }
                if (path.equals("java.lang")) {
                    continue;
                }
                importList.add(path + "." + model);
            }
            Collections.sort(importList);
            for (String toImport : importList) {
                builder.append("import ");
                builder.append(toImport);
                builder.append(";\n");
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String createInstanceVars(final Object... values)  {
        EO modelParams = ValueParamsHelper.getAdapter(0, values);
        try {
            List fieldKeyList = getFields(modelParams);
            final StringBuilder builder = new StringBuilder("\n");
            for (Object fieldKey : fieldKeyList) {
                builder.append(ValueFieldsJava.createInstanceVar(modelParams, (String) fieldKey));
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static final String getPackagePath(EO adapter, String modelKey)  {
        EO modelParams = getModelParams(adapter, modelKey);
        return (String) modelParams.get("packagePath");
    }

    private static final List getFields(EO adapter)  {
        if (adapter == null || adapter.isEmpty()) {
            throw new Exception("Adapter is null or empty!");
        }
        Object fieldKeys = adapter.get(EO_STATIC.F_FIELD_KEYS);
        if (fieldKeys == null) {
            throw new Exception("Field keys are empty!");
        }
        List fieldKeyList = (List) fieldKeys;
        if (fieldKeyList.isEmpty()) {
            throw new Exception("Field keys are empty!");
        }
        return fieldKeyList;
    }

    public enum MODEL_KEY_MAP {
        column(EO_STATIC.F_TABLE),
        idkey(EO_STATIC.F_ID_KEY);;
        private final String annotation;

        MODEL_KEY_MAP(String value) {
            this.annotation = value;
        }

        public String getAnnotation() {
            return this.annotation;
        }
    }
*/
}

