/**
 * <p>
 * Some static utility classes....
 * <p>
 * http://www.jdbee.org<br/>
 * date 10.3.2012<br/>
 *
 * @author Werner Diwischek
 * @version 0.1
 */
package org.fluentcodes.projects.elasticobjects.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;


public class Util {
    private static final Logger LOG = LogManager.getLogger(Util.class);
    private static final Pattern varPattern = Pattern.compile("([\\$\\#\\!])\\{(.*?)\\}");
    private static SimpleDateFormat dayTimeFormat = new SimpleDateFormat("yyyyMMdd-hhmmss");
    private static SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");

    public Util() {
    }

    /**
     * Returns a String with the date in format yyyyMMdd-hhmms
     *
     * @param date - date to format
     * @return
     */
    private static String getDateTime(Date date) {
        return dayTimeFormat.format(date);
    }


    /**
     * Returns a String with the date in format yyyyMMdd
     *
     * @param date - date to format
     * @return
     */
    private static String getDay(Date date) {
        return dayFormat.format(date);
    }

    /**
     * Returns the getSerialized with an upper first character
     *
     * @param item A getSerialized item
     * @return Capitalized item
     */

    public static String upperFirstCharacter(String item) {
        if (item == null || item.isEmpty()) {
            LOG.error("String is null");
            return null;
        }

        int length = item.length();

        if (length == 0) {
            LOG.error("String is of zero size");
            return item;
        }

        return item.substring(0, 1).toUpperCase() + item.substring(1);
    }

    public static String upper(String item) {
        if (item == null || item.isEmpty()) {
            LOG.error("String is null");
            return "";
        }

        char[] chars = item.toCharArray();
        StringBuilder builder = new StringBuilder();
        char previous = 0;
        for (char entry : chars) {
            if ((entry > 96 && entry < 123) || entry == 228 || entry == 246 || entry == 252) {
                int upper = (int) entry - 32;
                builder.append((char) upper);
            } else if ((entry > 47 && entry < 58) || (entry > 64 && entry < 91) || entry == 196 || entry == 214 || entry == 220) {
                if ((previous > 47 && previous < 58) || (previous > 96 && previous < 123) || previous == 228 || previous == 246 || previous == 252) {
                    builder.append("_");
                }
                builder.append(entry);
            } else if (entry < 65 && !(entry > 47 && entry < 58)) {
                builder.append("_");
            } else if (entry > 90 && entry < 97) {
                builder.append("_");
            }
            previous = entry;
        }
        return builder.toString();
    }

    public static String lower(String item) {
        if (item == null || item.isEmpty()) {
            LOG.error("String is null");
            return "";
        }

        char[] chars = item.toCharArray();
        StringBuilder builder = new StringBuilder();
        char previous = 0;
        for (char entry : chars) {
            if ((entry > 96 && entry < 123) || entry == 228 || entry == 246 || entry == 252) {
                if (previous == 45 || previous == 95) {
                    int upper = (int) entry - 32;
                    builder.append((char) upper);
                } else {
                    builder.append(entry);
                }
            } else if ((entry > 64 && entry < 91) || entry == 196 || entry == 214 || entry == 220) {
                if (previous == 45 || previous == 95) {
                    builder.append(entry);
                } else {
                    int lower = (int) entry + 32;
                    builder.append((char) lower);
                }
            } else if (entry == 45 || entry == 95) {
            }
            previous = entry;
        }
        return builder.toString();
    }

    /**
     * Returns the getSerialized with an upper first character
     *
     * @param item A getSerialized item
     * @return Capitalized item
     */
    public static String lowerFirstCharacter(String item) {
        if (item == null) {
            LOG.error("String is null");
            return null;
        }

        int length = item.length();

        if (length == 0) {
            LOG.error("String is of zero size");
            return item;
        }
        return item.substring(0, 1).toLowerCase() + item.substring(1);
    }


}
