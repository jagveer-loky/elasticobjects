package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.CallContent;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*==>{ALLHeader.tpl, ., JAVA|>}|*/

/**
 * Executes a template content. 
 * Created by Werner Diwischek at date Thu Nov 05 22:26:46 CET 2020.
 */
public class TemplateCall extends CallImpl implements CallContent {
/*=>{}.*/
    private static final transient Logger LOG = LogManager.getLogger(TemplateCall.class);

    /*==>{ALLStaticNames.tpl, fieldMap/*, JAVA, override eq false|>}|*/
   public static final String CONTENT = "content";
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldMap/*, JAVA|>}|*/
   private  String content;
/*=>{}.*/

    public TemplateCall() {
        super.setTargetPath(TARGET_AS_STRING);
    }

    public TemplateCall(final String content) {
        this();
        this.content = content;
    }

    @Override
    public void setByParameter(final String values) {
        if (values == null||values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length>3) {
            throw new EoException("Short form should have form '<sourcePath>[,<targetPath>][,<condition>]' with max length 3 but has size " + array.length + ": '" + values + "'." );
        }
        setByString(array);
    }

    protected void setByString(final String[] array) {
        if (array == null||array.length == 0) {
            throw new EoException("Set by empty input values");
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
            setCondition(array[1]);
        }
    }

    public boolean isContentActive() {
        return hasContent() && ParserCurlyBracket.containsStartSequence(content);
    }
    /**
     */
    public String execute(EO eo)  {
        if (!hasContent()) {
            throw new EoException("No content for template?!");
        }
        if (!init(eo)) {
            return "";
        }
        return new ParserCurlyBracket(content).parse(eo);
    }
    public TemplateCall setTargetPath(String x) {
        super.setTargetPath(x);
        return this;
    }
    /*==>{ALLSetter.tpl, fieldMap/*, JAVA|>}|*/
    /**
    A content.
    */
    
    public TemplateCall setContent(String content) {
        this.content = content;
        return this;
    }
    
    public String getContent () {
       return this.content;
    }
    
    public boolean hasContent () {
        return content!= null && !content.isEmpty();
    }
/*=>{}.*/
}
