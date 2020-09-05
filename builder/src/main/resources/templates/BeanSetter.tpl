<call loopPath="fieldKeys/*">
    <call execute="ValueFieldsJava.createDescription(adapter, _value)" />

    public final void set$[!ValuesMisc.upperFirstChar(_value)]({insert
execute="ValueFieldsJava.createType(adapter, _value)" /> $[_value]) {
        this.$[_value] = $[_value];
    }

    public final {insert
    execute="ValueFieldsJava.createType(adapter, _value)" /> get$[!ValuesMisc.upperFirstChar(_value)] () {
            return this.$[_value];
    }</call>