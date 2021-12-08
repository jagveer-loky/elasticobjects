package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.Call;

import java.util.Set;

/**
 * Offers an adapter for calls.
 */

public interface IEOCall extends IEOScalar {
    default EO addCall(Call call) {
        return getRoot().addCall(call);
    }

    default Set<String> getCallKeys() {
        return getRoot().getCallKeys();
    }

    default EO getCallEo(String key) {
        return getRoot().getCallEo(key);
    }

    default EO getCallsEo() {
        return getRoot().getCallsEo();
    }

    default boolean hasCalls() {
        return getRoot().hasCalls();
    }

    default boolean execute() {
        return getRoot().execute();
    }
}
