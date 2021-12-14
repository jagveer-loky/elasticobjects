package org.fluentcodes.projects.elasticobjects;

/**
 * Offers an adapter for scalar wrapper to access elements via path.
 */

public interface IEOScalar extends IEOBase, IEOModel, IEOCall, IEOLog, IEORole, IEOSerialize {
    String compare(final IEOScalar other);
}
