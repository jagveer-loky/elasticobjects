

  /**
  * \
  =>{description}.
  */
   \
  ==>{JavaFieldTypeCall->modelKeys}. get
  ==>{StringUpperFirstCharCall->fieldKey}.();
   \
  =>{/modelKey}. set
  ==>{StringUpperFirstCharCall->fieldKey}. (
  ==>{JavaFieldTypeCall->modelKeys}. \
  =>{fieldKey}.);
   default Boolean has
  ==>{StringUpperFirstCharCall->fieldKey}. () {
      return get==>{StringUpperFirstCharCall->fieldKey}.()!= null
    ==>{JavaFieldNotEmptyCall->modelKeys}.;
    }