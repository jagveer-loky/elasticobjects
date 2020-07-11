package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.calls.ListParams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ScsIO extends ListIO implements ListIOInterface {
    private Scanner scanner;

    public ScsIO(ScsConfig scsConfig)  {
        super(scsConfig);
        //http://www.java2s.com/Code/Java/File-Input-Output/CreateBufferedReader
    }

    public Object read()  {
        return super.read(new ListParams());
    }

    public void write(Object entry)  {
        write((List) entry);
    }


    protected ScsConfig getScsConfig() {
        return (ScsConfig) getListConfig();
    }

    public void reset()  {
        close();
        URL url = getScsConfig().getFileConfig().getUrl();
        try {
            scanner = new Scanner(new InputStreamReader(url.openStream()));
            scanner.useDelimiter(getScsConfig().getRowDelimiter());
        } catch (IOException e) {
            throw new EoException(e);
        }
    }

    public void close()  {
        if (scanner != null) {
            scanner.close();
            scanner = null;
        }
    }

    public List readRow() {
        if (scanner.hasNext()) {
            return Arrays.asList(scanner.next().split(getScsConfig().getFieldDelimiter()));
        }
        return null;
    }

    public List readHead(int rownum)  {
        return readRow(rownum);
    }

    public List readRow(int rownum)  {
        if (rownum < 0) {
            return null;
        }
        reset();
        for (int i = 0; i <= rownum; i++) {
            if (scanner.hasNext()) {
                if (i == rownum) {
                    return readRow();
                }
                scanner.next();
            } else {
                return null;
            }
        }
        return null;
    }

    public void write(List rows)  {
        if (rows == null || rows.isEmpty()) {
            throw new EoException("Strange - no list values - nothing to write! Will return without doing anything.");
        }
        StringBuilder buffer = new StringBuilder();
        for (Object row : rows) {
            if (row == null) {
                buffer.append(getScsConfig().getRowDelimiter());
                continue;
            }
            if (!(row instanceof List)) {
                buffer.append(getScsConfig().getRowDelimiter());
                continue;
            }
            List rowList = (List) row;
            if (rowList.isEmpty()) {
                buffer.append(getScsConfig().getRowDelimiter());
                continue;
            }
            for (int i = 0; i < rowList.size(); i++) {
                Object entry = rowList.get(i);
                if (entry == null) {
                    buffer.append(getScsConfig().getFieldDelimiter());
                }
                buffer.append(entry.toString());
                if (i + 1 < rowList.size()) {
                    buffer.append(getScsConfig().getFieldDelimiter());
                }
            }
            buffer.append(getScsConfig().getRowDelimiter());
        }

        URL url = getScsConfig().getFileConfig().getUrl();
        File file;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new EoException(e);
            }
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(buffer.toString().getBytes());
        } catch (Exception e) {
            throw new EoException(e);
        } finally {
            try {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            catch (Exception e) {
                throw new EoException(e);
            }
        }
    }

}
