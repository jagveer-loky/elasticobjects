package org.fluentcodes.projects.elasticobjects.config;

import au.com.bytecode.opencsv.CSVReader;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListIO;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListIOInterface;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

;

public class CsvIO extends ListIO implements ListIOInterface {
    private CSVReader csvReader;

    public CsvIO(CsvConfig csvConfig)  {
        super(csvConfig);
    }

    public Object read()  {
        return super.read(new ListParams());
    }

    public void write(Object object)  {
        this.write((List) object);
    }

    public CsvConfig getCsvConfig() {
        return (CsvConfig) getListConfig();
    }

    public void reset()  {
        close();
        URL url = getCsvConfig().getFileConfig().getUrl();
        csvReader = new CSVReader(new InputStreamReader(url.openStream()), getCsvConfig().getFieldDelimiter().charAt(0));
    }

    public void close()  {
        if (csvReader != null) {
            csvReader.close();
            csvReader = null;
        }
    }

    public List readRow()  {
        String[] row = csvReader.readNext();
        if (row == null) {
            return null;
        }
        return Arrays.asList(row);
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
            String[] row = csvReader.readNext();
            if (row == null) {
                return null;
            }
            if (i == rownum) {
                return Arrays.asList(row);
            }
        }
        return null;
    }

    public void write(List rows)  {
        if (rows == null || rows.isEmpty()) {
            throw new Exception("Strange - no list values - nothing to write! Will return without doing anything.");
        }
        StringBuilder buffer = new StringBuilder();
        for (Object row : rows) {
            if (row == null) {
                buffer.append(getCsvConfig().getRowDelimiter());
                continue;
            }
            if (!(row instanceof List)) {
                buffer.append(getCsvConfig().getRowDelimiter());
                continue;
            }
            List rowList = (List) row;
            if (rowList.isEmpty()) {
                buffer.append(getCsvConfig().getRowDelimiter());
                continue;
            }
            for (int i = 0; i < rowList.size(); i++) {
                Object entry = rowList.get(i);
                if (entry == null) {
                    buffer.append(getCsvConfig().getFieldDelimiter());
                }
                buffer.append(entry.toString());
                if (i + 1 < rowList.size()) {
                    buffer.append(getCsvConfig().getFieldDelimiter());
                }
            }
            buffer.append(getCsvConfig().getRowDelimiter());
        }

        URL url = getCsvConfig().getFileConfig().getUrl();
        File file;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        }
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
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
