package org.fluentcodes.projects.elasticobjects.condition;

import org.fluentcodes.projects.elasticobjects.eo.EO;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by werner.diwischek on 08.01.18.
 */
public interface Condition {
    String EQ = "eq";
    String EQUALS = "equals";
    String MATCH = "match";
    String EX = "ex";
    String NEX = "nex";
    String LIKE = "like";
    String NE = "ne";
    //static final Pattern ifPattern = Pattern.compile("[\\s]*(.*?)[\\s]+(eq|ne|equals|le|ge|notEquals|like|notLike|in)[\\s]+(.*?)[\\s]*");
    Pattern ifPattern = Pattern.compile("[\\s]*([^\\s]*?)[\\s]+(eq\\s+|ne\\s+|equals\\s+|ex|nex|le\\s+|ge\\s+|notEquals\\s+|like\\s+|notLike\\s+|match\\s+|in\\s+)(.*)");

    boolean compare(Object object);

    String createQuery(Map<String, Object> keyValues);

    String getKey();

    Object getValue();

    boolean filter(List row);

    boolean filter(EO adapter);
}
