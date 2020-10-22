==>{TemplateCall->/FieldConfig/=>[_value].}|

    /**
    \
    =>{description|> }.
    */\
    ==>{TemplateCall->, javaGenFieldFinal ne final}|
       =>{javaGenFieldOverride}.
    public final \
    =>{/modelKey}. set
       ==>{StringUpperFirstCharCall->fieldKey}.(
       =>{javaGenFieldType}. \
       =>{fieldKey}.) {
        this.
       =>{fieldKey}. = \
       =>{fieldKey}.;
        return this;
    }
    =>{}.

    =>{javaGenFieldOverride}.
    public final \
    =>{javaGenFieldType}. get
    ==>{StringUpperFirstCharCall->fieldKey}. () {
       return this.
    =>{fieldKey}.;
    }

    =>{javaGenFieldOverride}.
    public boolean has
    ==>{StringUpperFirstCharCall->fieldKey}. () {
        return \
    =>{fieldKey}.!= null
    ==>{TemplateCall->., javaGenFieldType eq String || javaGenFieldType like Map || javaGenFieldType like List}| && !
    =>{fieldKey}..isEmpty()=>{}.;
    }
=>{}.