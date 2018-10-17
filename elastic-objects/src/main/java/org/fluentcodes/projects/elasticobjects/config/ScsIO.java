package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.calls.ListParams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ScsIO extends ListIO implements ListIOInterface {
    private Scanner scanner;

    public ScsIO(ScsConfig scsConfig) throws Exception {
        super(scsConfig);
        //http://www.java2s.com/Code/Java/File-Input-Output/CreateBufferedReader
    }

    public Object read() throws Exception{
        return super.read(new ListParams());
    }

    public void write(Object entry) throws Exception {
        write((List)entry);
    }


    protected ScsConfig getScsConfig() {
        return (ScsConfig) getListConfig();
    }

    public void reset() throws Exception {
        close();
        URL url = getScsConfig().getFileConfig().getUrl();
        scanner = new Scanner(new InputStreamReader(url.openStream()));
        scanner.useDelimiter(getScsConfig().getRowDelimiter());
    }

    public void close() throws Exception {
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

    public List readHead(int rownum) throws Exception {
        return readRow(rownum);
    }

    public List readRow(int rownum) throws Exception {
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

    public void write(List rows) throws Exception {
        if (rows == null || rows.isEmpty()) {
            throw new Exception("Strange - no list values - nothing to write! Will return without doing anything.");
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
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(buffer.toString().getBytes());
        } catch (Exception e) {
            throw e;
        } finally {
            fileOutputStream.flush();
            fileOutputStream.close();
        }
    }

}
