package org.fluentcodes.tools.xpect;

import org.fluentcodes.tools.xpect.compators.XpectComparator;
import org.fluentcodes.tools.xpect.compators.XpectStringComparator;

public class XpectEo<T>  extends Xpect<T> {
    public XpectEo() {
        super(new IOJsonEo<>());
    }
    public XpectEo(final Class mappingClass) {
        this();
        setMappingClass(mappingClass);
    }
    public XpectEo(final XpectComparator<T> objectComparator, final XpectStringComparator stringComparator) {
        super(new IOJsonEo<>(), objectComparator, stringComparator);
    }
}
