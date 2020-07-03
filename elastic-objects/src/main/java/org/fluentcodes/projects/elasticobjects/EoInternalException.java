package org.fluentcodes.projects.elasticobjects;

public class EoInternalException extends RuntimeException{
    public EoInternalException(final String message) {
        super(message);
    }
    public EoInternalException(final String message, final Exception e) {
        super(message, e);
    }
    public EoInternalException(final Exception e) {
        super(e);
    }
}
