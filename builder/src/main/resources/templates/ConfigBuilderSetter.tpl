<call loopPath="fieldKeys/*"}<call loopPath="/fields/$[_value]"}
    this.$[fieldKey] = EOConverter.transform(configsCache,
         "$[modelKeys]",
         attributes.get(KEYS.$[fieldKey].name(),
         "$[dbFieldParam/default|]");</call></call>