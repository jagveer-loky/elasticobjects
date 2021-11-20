package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.CallContent;
import org.fluentcodes.projects.elasticobjects.calls.ExecutorCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.ArrayList;
import java.util.List;

public class InterpreterJson extends InterpreterParams {

    static final Indicators indicator = Indicators.JSON;

    public Indicators getIndicator() {
        return indicator;
    }

    @Override
    public Object call() {
        if (getEo() == null) {
            return getDefaultValue();
        }
        EO eoCall = EoRoot.ofValue(getEo().getConfigsCache(),
                TemplateMarker.CURLY.getStart()
                        + getCallDirective()
                        + TemplateMarker.CURLY.getStop());
        if (!eoCall.isEmpty()) {
            getEo().mapObject(eoCall.get());
        }
        if (eoCall.getCallKeys().isEmpty()) {
            return "";
        }
        return executeCalls(eoCall);
    }

    private String executeCalls(EO eoCall) {
        List<String> callSet = new ArrayList<>(eoCall.getCallKeys());
        StringBuilder returnResult = new StringBuilder();
        String postPend = "";
        for (int i = 0; i < callSet.size(); i++) {
            Call loopCall = (Call) eoCall.getCallEo(Integer.toString(i)).get();
            if (i == callSet.size() - 1) {
                postPend = addKeepOrContentToLast(returnResult, loopCall);
            }
            try {
                returnResult.append(ExecutorCall.execute(getEo(), loopCall));
            } catch (Exception e) {
                if (getDefaultValue() == null) {
                    returnResult.append(e.getMessage());
                } else {
                    returnResult.append(getDefaultValue());
                }
            }
        }
        returnResult.append(postPend);
        return returnResult.toString();
    }

    private String addKeepOrContentToLast(StringBuilder returnResult, Call lastCall) {
        String postPend = "";
        if (lastCall.hasKeepCall()) {
            returnResult.insert(0, createCallDirective() +
                    lastCall.getKeepEndSequence());
            postPend = lastCall.getKeepStartSequence() +
                    getTemplateMarker().getCloseSequence();
        }
        if (getContent() != null && !getContent().isEmpty()) {
            if (lastCall instanceof CallContent) {
                ((CallContent) lastCall).setContent(getContent());
            } else {
                throw new EoException("Last call with content template directive is not instance of CallContent but '" + lastCall.getClass().getSimpleName() + "'");
            }
        }
        return postPend;

    }
}
