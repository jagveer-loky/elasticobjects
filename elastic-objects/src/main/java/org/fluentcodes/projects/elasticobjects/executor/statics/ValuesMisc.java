package org.fluentcodes.projects.elasticobjects.executor.statics;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.executor.ValueParamsHelper;
import org.fluentcodes.projects.elasticobjects.utils.ReplaceUtil;
import org.fluentcodes.projects.elasticobjects.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by werner.diwischek on 06.02.18.
 */
public class ValuesMisc extends ValueParamsHelper {
    public static final String DEFAULT_SET = "ValuesMisc.set(eo,path,value)";

    protected static final Pattern linkPattern = Pattern.compile("(.*)://(.*)");
    protected static final Map<String, String> PROTOCOLS = initProtocols();

    private static final Map<String, String> initProtocols() {
        Map<String, String> result = new HashMap<>();
        result.put("http", "remote");
        result.put("https", "remote");
        result.put("wiki", "wiki");
        result.put("git", "git");
        return result;
    }

    public static String replace(Object... values)  {
        EO adapter = getEO(0, values);
        String replace = getString(1, values);
        return ReplaceUtil.replace(replace, adapter);
    }

    public static String upperFirstChar(Object... values)  {
        final String in = getString(0, values);
        return Util.upperFirstCharacter(in);
    }

    public static String setter(Object... values)  {
        final String in = getString(0, values);
        return "set" + Util.upperFirstCharacter(in);
    }

    public static String getter(Object... values)  {
        final String in = getString(0, values);
        return "get" + Util.upperFirstCharacter(in);
    }

    public static String getterIs(Object... values)  {
        final String in = getString(0, values);
        return "is" + Util.upperFirstCharacter(in);
    }

    public static String upper(Object... values)  {
        String in = getString(0, values);
        return Util.upper(in);
    }

    public static String lower(Object... values)  {
        String in = getString(0, values);
        return Util.lower(in);
    }

    public static String repeat(Object... values)  {
        String repeat = getString(0, values);
        Integer timesValue = getInt(1, values);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < timesValue; i++) {
            builder.append(repeat);
        }
        return builder.toString();
    }

    public static String join(Object[] values)  {
        EO adapter = getEO(0, values);
        String valueKey = getString(1, values);
        String delimiter = getString(2, values);
        List keyValues = new ArrayList();
        for (Object key : adapter.keys().toArray()) {
            EO nextAdapter = adapter.getChild((String) key);
            if (nextAdapter == null) {
                continue;
            }
            if (nextAdapter.get(valueKey) != null) {
                keyValues.add(nextAdapter.get(valueKey));
            }
        }
        return String.join(delimiter, keyValues);
    }

    public static Object set(Object[] values)  {
        EO adapter = getEO(0, values);
        String path = getString(1, values);
        String value = getString(2, values);
        adapter.setPathValue(path, value);
        return "";
    }
}
