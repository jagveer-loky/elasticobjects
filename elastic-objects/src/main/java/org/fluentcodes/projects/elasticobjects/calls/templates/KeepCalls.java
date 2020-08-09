package org.fluentcodes.projects.elasticobjects.calls.templates;

public enum KeepKeys {
    JAVA("\n//", " "), TARGET("", "");
    private String startComment;
    private String endComment;

    KeepKeys(String startComment, String endComment) {
        this.startComment = startComment;
        this.endComment = endComment;
    }

    public String wrapWithComment(final String element) {
        return startComment + element + endComment;
    }

    public String getStartComment() {
        return startComment;
    }

    public String getEndComment() {
        return endComment;
    }
}
