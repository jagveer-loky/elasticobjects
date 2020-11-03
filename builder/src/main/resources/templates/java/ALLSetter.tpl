

    /**
    \
    =>{description|> }.
    */\
    ==>{TemplateCall->, final ne true}|
    \
    ==>{JavaFieldOverrideCall->override}.
    public \
    =>{/modelKey}. set
       ==>{StringUpperFirstCharCall->fieldKey}.(
       ==>{JavaFieldTypeCall->modelKeys}. \
       =>{fieldKey}.) {
        this.
       =>{fieldKey}. = \
       =>{fieldKey}.;
        return this;
    }
    =>{}.

    \
    ==>{JavaFieldOverrideCall->override}.
    public \
    ==>{JavaFieldTypeCall->modelKeys}. get
    ==>{StringUpperFirstCharCall->fieldKey}. () {
       return this.
    =>{fieldKey}.;
    }

    \
    ==>{JavaFieldOverrideCall->override}.
    public boolean has
    ==>{StringUpperFirstCharCall->fieldKey}. () {
        return \
    =>{fieldKey}.!= null
    ==>{JavaFieldNotEmptyCall->modelKeys}.;
    }