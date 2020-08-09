package org.fluentcodes.projects.elasticobjects.calls.templates;

public enum KeepCalls {
    HTML("<!--", "-->"),
    JAVA("\n//", " "),
    TARGET("", "");
    private String startComment;
    private String endComment;

    KeepCalls(String startComment, String endComment) {
        this.startComment = startComment;
        this.endComment = endComment;
    }

    public String createDirective(final String directive) {
        return startComment + "$[" + directive + "]" + endComment;
    }
}
