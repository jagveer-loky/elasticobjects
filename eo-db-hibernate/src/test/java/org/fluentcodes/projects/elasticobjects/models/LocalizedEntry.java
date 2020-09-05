package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//<call keep="JAVA" templateKey="BeanImport.tpl">
//
//</call>

//<call keep="JAVA" templateKey="BeanHeader.tpl">
//

/**
 * Created by Werner Diwischek on 09.10.2016.
 */
@Entity
@Table(name = "LocalizedEntry")

//</call>

public class LocalizedEntry extends ModelAnnotated {
    private static transient Logger LOG = LogManager.getLogger(LocalizedEntry.class);

    //<call keep="JAVA" templateKey="BeanInstanceVars.tpl">
//
    private String countryCode;
    private LocalizedString localizedString;
//</call>

    //<call keep="JAVA" templateKey="BeanSetter.tpl">
//

    /**
     * A two-digit country code as per ↗ ISO 3166-1 alpha-2 .
     */
    @Column(name = "countryCode", length = 2, nullable = true)
    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * A field with the localized String. {@link LocalizedString}
     */
    @ManyToOne()

    public LocalizedString getLocalizedString() {
        return this.localizedString;
    }

    public void setLocalizedString(LocalizedString localizedString) {
        this.localizedString = localizedString;
    }
//</call>

}
