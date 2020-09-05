    public boolean isIgnoreHeader() {
        return rowMapper.isIgnoreHeader();
    }

    public void setIgnoreHeader(boolean ignoreHeader) {
        rowMapper.setIgnoreHeader(ignoreHeader);
    }

    public Integer getRowStart() {
        return listParams.getRowStart();
    }

    public void setRowStart(Integer rowStart) {
        listParams.setRowStart(rowStart);
    }

    public Integer getRowEnd() {
        return listParams.getRowEnd();
    }

    public void setRowEnd(Integer rowEnd) {
        listParams.setRowEnd(rowEnd);
    }

    public Integer getLength() {
        return listParams.getLength();
    }

    public void setLength(Integer rowEnd) {
        listParams.setRowStart(rowEnd);
    }

    public String getOrAsString() {
      return listParams.getOrAsString();
    }

    public void setOrAsString(String orAsString) {
      listParams.setOrAsString(orAsString);
    }

    public String getPathPatternAsString() {
        return rowMapper.getPathPattern().toString();
    }

    public void setPathPatternAsString(String pathPattern) {
        rowMapper.setPathPattern(new PathPattern(pathPattern));
    }

    public String getColKeysAsString() {
        return String.join(",",rowMapper.getColKeys());
    }

    public void setColKeysAsString(String colKeys) {
        rowMapper.setColKeys(Arrays.asList(colKeys.split(",")));
    }

    @Override
    public String getModelKey() {
        return rowMapper.getModel().getModelKey();
    }

    @Override
    public void setModelKey(String modelKey)  {
        rowMapper.setModel(getProvider().getModel(modelKey));
    }
