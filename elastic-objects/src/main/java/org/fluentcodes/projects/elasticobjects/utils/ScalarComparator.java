package org.fluentcodes.projects.elasticobjects.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

/**
 * Created by Werner on 17.07.2014.
 */
//==========================
// Start of the ls class
//==========================


public class ScalarComparator {
    private static final Logger LOG = LogManager.getLogger(ScalarComparator.class);


    public static boolean compare(Object left, Object right) {
        if (left == null && right == null) {
            return true;
        }
        if (right == null) {
            return false;
        }
        if (left == null) {
            return false;
        }

        if ((right instanceof Date) || (left instanceof Date)) {
            return compareDate(right, left);
        }
        if (left instanceof Boolean || right instanceof Boolean) {
            return compareBoolean(left, right);
        }
        if ((left instanceof Number) || (right instanceof Number)) {
            return compareNumber(left, right);
        }
        if ((left instanceof String) || (right instanceof String)) {
            return compareString(left, right);
        }
        if ((left instanceof byte[]) || (right instanceof byte[])) {
            return compareBytes(left, right);
        }

        //http://stackoverflow.com/questions/16627813/why-is-comparing-a-float-to-a-double-inconsistent-in-java
        //http://stackoverflow.com/questions/17898266/why-cant-we-use-to-compare-two-float-or-double-numbers
        LOG.warn("No compare defined " + left + " <> " + right + "(" + left.getClass().getSimpleName() + " <> " + right.getClass().getSimpleName() + ")");
        return false;
    }

    public static boolean compareBoolean(Object left, Object right) {
        Boolean bRight = ScalarConverter.toBoolean(right);
        Boolean bLeft = ScalarConverter.toBoolean(left);
        if (bRight == null && bLeft == null) {
            return true;
        }
        if (bRight != null && bLeft == null) {
            return false;
        }
        if (bLeft != null && bRight == null) {
            return false;
        }
        return bRight.equals(bLeft);
    }

    public static boolean compareBytes(Object left, Object right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        String sRight = ScalarConverter.toString(right);
        String sLeft = ScalarConverter.toString(left);
        if (sRight == null || sLeft == null) {
            return false;
        }
        return sRight.equals(sLeft);
    }

    public static boolean compareString(Object left, Object right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        String sRight = ScalarConverter.toString(right);
        String sLeft = ScalarConverter.toString(left);
        if (sRight == null || sLeft == null) {
            return false;
        }
        return sRight.equals(sLeft);
    }

    public static boolean compareDouble(Object left, Object right) {
        if (right == null && left == null) {
            return true;
        }
        if (right == null || left == null) {
            return false;
        }
        Double sLeft = ScalarConverter.toDouble(left);
        Double sRight = ScalarConverter.toDouble(right);
        if (sLeft == null || sRight == null) {
            return false;
        }
        return sLeft.equals(sRight);
    }

    public static boolean compareLong(Object left, Object right) {
        if (right == null && left == null) {
            return true;
        }
        if (right == null || left == null) {
            return false;
        }
        Long sLeft = ScalarConverter.toLong(left);
        Long sRight = ScalarConverter.toLong(right);
        if (sLeft == null || sRight == null) {
            return false;
        }
        return sLeft.equals(sRight);
    }


    public static boolean compareFloat(Object left, Object right) {
        if (right == null && left == null) {
            return true;
        }
        if (right == null || left == null) {
            return false;
        }
        Float sLeft = ScalarConverter.toFloat(left);
        Float sRight = ScalarConverter.toFloat(right);
        if (sLeft == null || sRight == null) {
            return false;
        }
        return sLeft.equals(sRight);
    }

    public static boolean compareInteger(Object left, Object right)  {
        if (right == null && left == null) {
            return true;
        }
        if (right == null || left == null) {
            return false;
        }
        Integer sLeft = ScalarConverter.toInt(left);
        Integer sRight = ScalarConverter.toInt(right);
        if (sLeft == null || sRight == null) {
            return false;
        }
        return sLeft.equals(sRight);
    }

    public static boolean compareNumber(Object left, Object right) {
        if (right == null && left == null) {
            return true;
        }
        if (right == null || left == null) {
            return false;
        }
        String sLeft = ScalarConverter.toString(left);
        String sRight = ScalarConverter.toString(right);
        if (sLeft == null || sRight == null) {
            return false;
        }
        sLeft = sLeft.replaceAll("[\\.,]0*$", "");
        sRight = sRight.replaceAll("[\\.,]0*$", "");
        sRight = sRight.replaceAll(",", "\\.");
        sLeft = sLeft.replaceAll(",", "\\.");
        return sLeft.equals(sRight);
    }


    public static boolean compareDate(Object left, Object right) {
        if (left == null && right == null) {
            return true;
        }
        if (left != null && right == null) {
            return false;
        }
        if (left == null && right != null) {
            return false;
        }
        Date dateLeft = ScalarConverter.toDate(left);
        Date dateRight = ScalarConverter.toDate(right);
        if (dateLeft == null || dateRight == null) {
            return false;
        }
        if (dateLeft.equals(dateRight)) {
            return true; // TODO removed Joda time. Try to reimplement ist
        }
        return false;
    }
}

