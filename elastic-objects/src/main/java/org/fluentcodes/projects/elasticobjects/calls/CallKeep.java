package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.calls.templates.KeepCalls;

public interface CallKeep {
    String KEEP_CALL = "keepCall";

    boolean hasKeepCall();
    KeepCalls getKeepCall();
    void setKeepCall(KeepCalls keepCalls);
    default String getKeepEndSequence() {
        if (!hasKeepCall()) {
            return "";
        }
        return getKeepCall().getEndComment();
    }
    default String getKeepStartSequence() {
        if (!hasKeepCall()) {
            return "";
        }
        return getKeepCall().getStartComment();
    }
}
