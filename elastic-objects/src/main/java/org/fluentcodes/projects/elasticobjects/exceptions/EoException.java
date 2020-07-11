package org.fluentcodes.projects.elasticobjects.exceptions;

public class EoException extends RuntimeException{
    public EoException(final String message) {
        super(message);
    }
    public EoException(final String message, final Exception e) {
        super(message, e);
    }
    public EoException(final Exception e) {
        super(e);
    }
}
