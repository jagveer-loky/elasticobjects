package org.fluentcodes.tools.xpect;

import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfig;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.tools.xpect.compators.XpectComparator;
import org.fluentcodes.tools.xpect.compators.XpectStringComparator;

import java.util.Map;

public class XpectEo<T>  extends Xpect<T> {
    public XpectEo() {
        super(new IOJsonEo<>());
    }
    public XpectEo(ConfigMaps cache) {
        super(new IOJsonEo<>(cache));
    }
    public XpectEo(ConfigMaps cache, final Class mappingClass) {
        this(cache);
        setMappingClass(mappingClass);
    }
    public XpectEo(Builder builder) {
        super(new IOJsonEo<>(builder));
    }

    public XpectEo(ConfigMaps cache, final XpectComparator<T> objectComparator, final XpectStringComparator stringComparator) {
        super(new IOJsonEo<>(cache), objectComparator, stringComparator);
    }

    public static final String load(ConfigConfig config) {
        XpectEo xpect = new XpectEo(config.getConfigMaps());
        return xpect.load(config.toString());
    }

    public static final String load(final ConfigMaps configMaps, Object toCompare) {
        XpectEo xpect = new XpectEo(configMaps);
        return xpect.load(toCompare);
    }

    public static class Builder<T> {
        JSONSerializationType type = JSONSerializationType.STANDARD;
        Class[] classes = new Class[]{Map.class};
        ConfigMaps cache;


        public JSONSerializationType getType() {
            return type;
        }

        public Builder setType(JSONSerializationType type) {
            this.type = type;
            return this;
        }

        public Builder setCache(ConfigMaps cache) {
            this.cache = cache;
            return this;
        }

        public Builder setClasses(Class... classes) {
            this.classes = classes;
            return this;
        }
        public XpectEo<T> build() {
            return new XpectEo<>(this);
        }
    }
}
