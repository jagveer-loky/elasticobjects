package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.ListParams;

import java.util.List;

/**
 * Created by Werner on 09.10.2016.
 */
public class HQueryIO implements ListIOInterface {
    private static transient final Logger LOG = LogManager.getLogger(HQueryIO.class);

    private List result;
    private int pointer;

    protected HQueryIO(HQueryConfig config)  {
        String hql = config.getHql();
        HIO hio = new HIO(config.getHConfig());
        result = hio.read(config.getModelConfig(), new ListParams());
    }

    //TODO
    @Override
    public List read(ListParams listParams) {
        return null;
    }

    @Override
    public Object read() {
        return null;
    }

    @Override
    public void write(Object object) {

    }

    public Object readRow()  {
        pointer++;
        if (pointer < result.size()) {
            return result.get(pointer);
        } else {
            return null;
        }
    }

    public List readHead(int rownum)  {
        return null;
    }

    public Object readRow(int i)  {
        pointer = i - 1;
        return readRow();
    }

    public void write(List rows)  {

    }

    public void reset()  {
        pointer = -1;
    }

    public void close()  {
    }
}
