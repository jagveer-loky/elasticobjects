package org.fluentcodes.tools.xpect;

import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfig;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.Scope;
import org.fluentcodes.tools.io.IOEo;
import org.fluentcodes.tools.io.IOString;
import org.fluentcodes.tools.xpect.compators.ComparatorJunit4;

import java.io.File;

public class XpectEo {
    static final ConfigMaps CONFIG_MAPS = new ConfigMaps(Scope.TEST);
    static final ConfigMaps CONFIG_MAP_DEV = new ConfigMaps(Scope.DEV);
    public static File assertJunit(Object toCompare) {
        String persistedFiles = Xpect.determinePersistenceFile("json");
        IOEo ioEo = new IOEo(persistedFiles, CONFIG_MAPS, toCompare.getClass());
        ioEo.setType(JSONSerializationType.STANDARD);
        new ComparatorJunit4(ioEo).compare(toCompare);
        return new File(persistedFiles);
    }

    public static File assertEoJunit(Object toCompare) {
        String persistedFiles = Xpect.determinePersistenceFile("json");
        IOEo ioEo = new IOEo(persistedFiles, CONFIG_MAPS, toCompare.getClass());
        ioEo.setType(JSONSerializationType.EO);
        new ComparatorJunit4(ioEo).compare(toCompare);
        return new File(persistedFiles);
    }

    public static final String load(ConfigConfig config) {
        String persistedFiles = Xpect.determinePersistenceFile( "json");
        return new IOString(persistedFiles).read();
    }

    public static final String load(ConfigBean bean) {
        String persistedFiles = Xpect.determinePersistenceFile("json");
        return new IOString(persistedFiles).read();
    }

}
