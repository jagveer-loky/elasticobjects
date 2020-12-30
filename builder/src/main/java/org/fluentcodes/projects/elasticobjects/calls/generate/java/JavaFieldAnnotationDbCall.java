package org.fluentcodes.projects.elasticobjects.calls.generate.java;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/

/**
 * Replaces new lines in description with a starting " * " for new line. 
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 07:41:22 CET 2020
 */
public class JavaFieldAnnotationDbCall extends CallImpl implements SimpleCommand {
/*=>{}.*/

/*==>{ALLStaticNames.tpl, fieldBeans/*, override eq false, JAVA|>}|*/
/*=>{}.*/

/*==>{ALLInstanceVars.tpl, fieldBeans/*, , JAVA|>}|*/
/*=>{}.*/

    @Override
    public String execute(final EO eo) {
        if (!(eo.get() instanceof FieldBeanInterface)) {
            throw new EoException("Input must be instance of '" + FieldBeanInterface.class.getSimpleName() + "' but is '" + eo.get().getClass().getSimpleName() + "'");
        }
        EO eoModel = eo.getParent().getParent(); //hashmap
        /*
        if (!(eoModel.get() instanceof ModelBeanGenProperties)) {
            throw new EoException("Input parent must be instance of '" + ModelBeanGenProperties.class.getSimpleName() + "' but is '" + eo.get().getClass().getSimpleName() + "'");
        }
        if (!((ModelBeanGenProperties)eoModel.get()).isDbAnnotated()) {
            return "";
        }*/
        try {
            FieldBeanInterface properties = (FieldBeanInterface) eo.get();
            if (properties.isTransient()) {
                return "@Transient";
            }
            if (properties.isGenerated()) {
                return "  @Id\n   @GeneratedValue(strategy = GenerationType.IDENTITY)\n";
            }
            StringBuilder builder = new StringBuilder("   @Column(");
            if (properties.hasFieldName()) {
                builder.append("name = \"");
                builder.append(properties.getFieldName());
                builder.append("\", ");
            }
            if (properties.isUnique()) {
                builder.append("unique = true, ");
            }
            if (properties.isNotNull()) {
                builder.append("nullable = false, ");
            }
            if (String.class.getSimpleName().equals(properties.getModelKeys())) {
                if (properties.hasLength()) {
                    builder.append(" length = ");
                    builder.append(properties.getLength());
                    builder.append(", ");
                }
            }
            return builder.toString().replaceAll(", $",")\n");
        }
        catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }
/*==>{ALLSetter.tpl, fieldBeans/*, , JAVA|>}|*/
/*=>{}.*/
}
