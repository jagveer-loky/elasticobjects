package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Map;

//<call keep="JAVA" templateKey="BeanImport.tpl">
//
//</call>

//<call keep="JAVA" templateKey="BeanHeader.tpl">
//

/**
 * Created by Werner Diwischek on 09.10.2016.
 */
@Entity
@Table(name = "LocalizedString")

//</call>

public class LocalizedString extends ModelAnnotated {
    private static transient Logger LOG = LogManager.getLogger(LocalizedString.class);

    //<call keep="JAVA" templateKey="BeanInstanceVars.tpl">
//
    private Map<String, LocalizedEntry> localizedEntryMap;
//</call>

    //<call keep="JAVA" templateKey="BeanSetter.tpl">
//

    /**
     * A map of localized Entries.
     */
    @OneToMany()

    public Map<String, LocalizedEntry> getLocalizedEntryMap() {
        return this.localizedEntryMap;
    }

    public void setLocalizedEntryMap(Map<String, LocalizedEntry> localizedEntryMap) {
        this.localizedEntryMap = localizedEntryMap;
    }
//</call>

}
