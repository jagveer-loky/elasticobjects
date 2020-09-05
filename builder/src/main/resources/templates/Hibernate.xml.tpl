<?xml version="1.0"?>
<!DOCTYPE hibernate-mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">
        <hibernate-mapping package="org.clearcodes.projects.elasticobjects.assets">
<call path="*" if="'$[table]'">
  <class name="$[modelKey]" table="$[table]">
<call path="fieldHelperMap/*">
    $[hibernateXml]</call>
  </class>
</call>
</hibernate-mapping>