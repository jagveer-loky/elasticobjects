package org.fluentcodes.tools.xpect;

import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.tools.xpect.compators.XpectComparator;
import org.fluentcodes.tools.xpect.compators.XpectStringComparator;

import java.util.Map;

public class XpectEo<T>  extends Xpect<T> {
    public XpectEo() {
        super(new IOJsonEo<>());
    }
    public XpectEo(EOConfigsCache cache) {
        super(new IOJsonEo<>(cache));
    }
    public XpectEo(EOConfigsCache cache, final Class mappingClass) {
        this(cache);
        setMappingClass(mappingClass);
    }
    public XpectEo(Builder builder) {
        super(new IOJsonEo<>(builder));
    }

    public XpectEo(EOConfigsCache cache, final XpectComparator<T> objectComparator, final XpectStringComparator stringComparator) {
        super(new IOJsonEo<>(cache), objectComparator, stringComparator);
    }

    public static class Builder<T> {
        JSONSerializationType type = JSONSerializationType.STANDARD;
        Class[] classes = new Class[]{Map.class};
        EOConfigsCache cache;


        public JSONSerializationType getType() {
            return type;
        }

        public Builder setType(JSONSerializationType type) {
            this.type = type;
            return this;
        }

        public Builder setCache(EOConfigsCache cache) {
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
