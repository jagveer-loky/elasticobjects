$[(TemplateCall)/FieldConfig/eo->_value. ]
    /**
    $[description]
    */
    $[(TemplateCall). localCondition="javaGenFieldFinal ne final"]
    $[javaGenFieldOverride]
    public final $[/modelKey] set$[(StringUpperFirstCharCall)fieldKey /]($[javaGenFieldType] $[fieldKey]) {
        this.$[fieldKey] = $[fieldKey];
        return this;
    }$[/]
    $[javaGenFieldOverride]
    public final $[javaGenFieldType] get$[(StringUpperFirstCharCall)fieldKey /] () {
            return this.$[fieldKey];
    }
    $[javaGenFieldOverride]
    public boolean has$[(StringUpperFirstCharCall)fieldKey /] () {
        return $[fieldKey] != null $[(TemplateCall). localCondition="javaGenFieldType eq String || javaGenFieldType like Map || javaGenFieldType like List" ] && !$[fieldKey].isEmpty()$[/];
    }
$[/]