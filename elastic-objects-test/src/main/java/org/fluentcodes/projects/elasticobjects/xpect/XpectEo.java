package org.fluentcodes.projects.elasticobjects.xpect;

import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.io.IOEo;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfig;
import org.fluentcodes.tools.io.IORuntimeException;
import org.fluentcodes.tools.io.IOString;
import org.fluentcodes.tools.xpect.Xpect;
import org.fluentcodes.tools.xpect.compators.ComparatorJunit4;

import java.io.File;

import static org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps.CONFIG_MAPS;

public class XpectEo {
    private XpectEo() {
    }

    public static File assertJunit(Object toCompare) {
        String persistedFiles = Xpect.determinePersistenceFile("json");
        IOEo<Object> ioEo = new IOEo<>(persistedFiles, CONFIG_MAPS, toCompare.getClass());
        ioEo.setType(JSONSerializationType.STANDARD);
        new ComparatorJunit4(ioEo).compare(toCompare);
        return new File(persistedFiles);
    }

    public static File assertEoJunit(Object toCompare) {
        String persistedFiles = Xpect.determinePersistenceFile("json");
        IOEo<Object> ioEo = new IOEo<>(persistedFiles, CONFIG_MAPS, toCompare.getClass());
        ioEo.setType(JSONSerializationType.EO);
        new ComparatorJunit4(ioEo).compare(toCompare);
        return new File(persistedFiles);
    }

    public static final String load(ConfigConfig config) {
        String persistedFiles = Xpect.determinePersistenceFile("json");
        try {
            return new IOString(persistedFiles).read();
        } catch (IORuntimeException ioRuntimeException) {
            return config.toString();
        }
    }

    public static final String load(ConfigBean bean) {
        String persistedFiles = Xpect.determinePersistenceFile("json");
        try {
            return new IOString(persistedFiles).read();
        } catch (IORuntimeException ioRuntimeException) {
            return new EOToJSON().toJson(CONFIG_MAPS, bean);
        }
    }

}
