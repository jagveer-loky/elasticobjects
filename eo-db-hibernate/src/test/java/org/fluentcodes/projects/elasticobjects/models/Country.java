package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

//<call keep="JAVA" templateKey="BeanImport.tpl">
//</call>
//<call keep="JAVA" templateKey="BeanHeader.tpl">

/**
 * $[description]
 * Created by Werner Diwischek on 09.10.2016.
 */
@Entity
@Table(name = "Country")
//</call>
public class Country extends ModelAnnotated {
    private static transient Logger LOG = LogManager.getLogger(Country.class);

    //<call keep="JAVA" templateKey="BeanInstanceVars.tpl">

    private String name;
    private String countryCode;
//</call>

    //<call keep="JAVA" templateKey="BeanSetter.tpl">

    /**
     * $[description]
     */
    @Transient()
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
    //</call>

    // Test outside should be kept
}
