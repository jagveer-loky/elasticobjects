package org.fluentcodes.projects.elasticobjects.calls;

/**
 * Created by Werner on 7.2.2015
 */
public enum PermissionType {
    EXECUTE(4), DELETE(3), CREATE(2), WRITE(1), READ(0), NOTHING(-1);
    private int value = 0;

    PermissionType(int level) {
        this.value = level;
    }

    public int value() {
        return this.value;
    }
}
