package org.fluentcodes.projects.elasticobjects.calls.generate.java;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.models.FieldBeanProperties;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelBeanProperties;

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
        if (!(eo.get() instanceof FieldBeanProperties)) {
            throw new EoException("Input must be instance of '" + FieldBeanProperties.class.getSimpleName() + "' but is '" + eo.get().getClass().getSimpleName() + "'");
        }
        EO eoModel = eo.getParent().getParent(); //hashmap
        if (!(eoModel.get() instanceof ModelBeanProperties)) {
            throw new EoException("Input parent must be instance of '" + ModelBeanProperties.class.getSimpleName() + "' but is '" + eo.get().getClass().getSimpleName() + "'");
        }
        if (!((ModelBeanProperties)eoModel.get()).isDbAnnotated()) {
            return "";
        }
        try {
            FieldBeanProperties properties = (FieldBeanProperties) eo.get();
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
