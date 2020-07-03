package org.fluentcodes.projects.elasticobjects.executor.statics;

import org.fluentcodes.projects.elasticobjects.executor.ValueParamsHelper;

/**
 * Wrapper for Math functions...
 *
 * @author Werner Diwischek
 * @version 0.01
 */
public class ValuesMath extends ValueParamsHelper {

    public static Double sin(Object[] args)  {
        Double myDouble = getDouble(0, args);
        return Math.sin(myDouble);
    }

    public static Double cos(Object[] args)  {
        Double myDouble = getDouble(0, args);
        return Math.cos(myDouble);
    }

    public static Double log(Object[] args)  {
        Double myDouble = getDouble(0, args);
        return Math.log(myDouble);
    }
}
