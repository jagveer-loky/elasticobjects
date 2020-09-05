package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Constants for module $[module]/$[subModule].
 * Created by Werner Diwischek on 09.10.2016.
 */
public $[name] {
    private static transient Logger LOG = LogManager.getLogger($[name]);
//<call templateKey="ConstantsLoop.tpl" keep="JAVA" loopPath="/fields"/>
}
