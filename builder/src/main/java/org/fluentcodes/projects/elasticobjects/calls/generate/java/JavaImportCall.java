package org.fluentcodes.projects.elasticobjects.calls.generate.java;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.calls.templates.KeepCalls;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/

/**
 * Replaces new lines in description with a starting " * " for new line. 
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 07:41:22 CET 2020
 */
public class JavaImportCall extends CallImpl implements SimpleCommand {
/*=>{}.*/

/*==>{ALLStaticNames.tpl, fieldBeans/*, override eq false, JAVA|>}|*/
/*=>{}.*/

/*==>{ALLInstanceVars.tpl, fieldBeans/*, , JAVA|>}|*/
/*=>{}.*/

    @Override
    public void setByParameter(final String values) {
        if (values == null||values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length>2) {
            throw new EoException("Short form should have form '<sourcePath>[,<targetPath>][,<condition>]' with max length 3 but has size " + array.length + ": '" + values + "'." );
        }
        if (array.length>0) {
            if (array[0].replaceAll("\\s","").isEmpty()) {
                setSourcePath(PathElement.SAME);
            }
            else {
                setSourcePath(array[0]);
            }
        }
        if (array.length>1) {
            setKeepCall(KeepCalls.valueOf(array[1]));
        }
    }

    @Override
    public String execute(final EO eo) {
        /*
        if (!(eo.get() instanceof ModelBeanGenProperties)) {
            throw new EoException("Input must be instance of '" + ModelBeanGenProperties.class.getSimpleName() + "' but is '" + eo.get().getClass().getSimpleName() + "'");
        }*/
        EO eoRoot = eo.getRoot();
        ModelBean modelBean = (ModelBean)eo.get();
        if (!modelBean.hasFieldBeans()) {
            return "";
        }
        Set<String> fieldModelSet = new HashSet<>();
        String modelPackage = modelBean.getPackagePath();
        for (FieldBeanInterface fieldBean: modelBean.getFieldBeans().values()) {
            if (fieldBean == null) {
                continue;
            }
            if (!fieldBean.hasModelKeys()) {
                continue;
            }
            if (fieldBean.isSuper()) {
                continue;
            }
            String[] modelKeys = fieldBean.getModelKeys().split(",");
            for (String modelKey: modelKeys) {
                fieldModelSet.add(modelKey);
            }
        }
        Set<String> importSet = new TreeSet<>();
        for (String fieldModelKey: fieldModelSet) {
            if (!eoRoot.hasEo(fieldModelKey)) {
                throw new EoException("Problem resolving model '" + fieldModelKey + "'");
            }
            EO eoFieldModel = eoRoot.getEo(fieldModelKey);
            /*
            if (!(eoFieldModel.get() instanceof ModelBeanGenProperties)) {
                throw new EoException("Input must be instance of '" + ModelBeanGenProperties.class.getSimpleName() + "' but is '" + eo.get().getClass().getSimpleName() + "'");
            }*/
            ModelBean fieldModel = (ModelBean) eoFieldModel.get();
            if (!fieldModel.hasPackagePath()) {
                throw new EoException("Problem resolving packagePath '" + fieldModelKey + "'");
            }
            String packagePath = fieldModel.getPackagePath();
            if (packagePath.equals("java.lang")) {
                continue;
            }
            if (packagePath.equals(modelPackage)) {
                continue;
            }
            importSet.add("import " + packagePath + "." + fieldModelKey + ";\n");
        }
        return "\n" + importSet.stream().collect(Collectors.joining(""));
    }
/*==>{ALLSetter.tpl, fieldBeans/*, , JAVA|>}|*/
/*=>{}.*/
}
