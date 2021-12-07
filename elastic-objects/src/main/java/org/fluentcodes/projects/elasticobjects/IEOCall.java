package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.Call;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Offers an adapter for calls.
 */

public interface IEOCall {
    EO addCall(Call callExecutor);
    Set<String> getCallKeys();
    EO getCallEo(String key);

    boolean execute();
}
