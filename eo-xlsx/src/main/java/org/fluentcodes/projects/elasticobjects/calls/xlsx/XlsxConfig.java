package org.fluentcodes.projects.elasticobjects.calls.xlsx;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListConfigInterface;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListMapper;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParams;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 30.10.2016.
 */
public class XlsxConfig extends FileConfig implements ListConfigInterface {
    private static final String SHEET_NAME = "sheetName";
    private final String sheetName;
    private final ListParams listParams;
    private final ListMapper listMapper;

    public XlsxConfig(final EOConfigsCache configsCache, final Map map) {
        super(configsCache, map);
        this.sheetName = map.containsKey(SHEET_NAME)? (String) map.get(SHEET_NAME): "0";
        this.listParams = map.containsKey(LIST_PARAMS) ? new ListParams((Map)map.get(LIST_PARAMS)) : new ListParams();
        this.listMapper = map.containsKey(LIST_MAPPER) ? new ListMapper((Map)map.get(LIST_MAPPER)) : new ListMapper();
    }

    @Override
    public ListParams getListParams() {
        return listParams;
    }

    @Override
    public ListMapper getListMapper() {
        return listMapper;
    }

    /**
     * A name for the sheet selected.
     */
    public String getSheetName() {
        return this.sheetName;
    }

    public Sheet getSheet()  {
        Workbook wb = readWorkbook();
        if (hasNoSheetName()) {
            return wb.getSheetAt(0);
        } else {
            return wb.getSheet(getSheetName());
        }
    }

    public boolean hasNoSheetName() {
        return getSheetName() == null || getSheetName().isEmpty();
    }


    public List<List> read() {
        resolve();
        List<List> result = new ArrayList<>();
        Sheet sheet = getSheet();
        if (sheet == null) {
            throw new EoException("The sheet for '" + getNaturalId() + "' is null. Perhaps the sheet name '" + sheetName + "' is undefined.");
        }

        Row row = sheet.getRow(1);

        List rowAsList;
        int counter = 0;
        while((rowAsList = getRowAsList(sheet.getRow(counter))) != null) {
            counter++;
            result.add(rowAsList);
        }

        if (sheet != null) {
            try {
                sheet.getWorkbook().close();
            } catch (IOException e) {
                sheet = null;
                throw new EoException(e);
            }
            sheet = null;
        }
        return result;
    }

    public Workbook readWorkbook()  {
        URL url = findUrl();
        if (url == null) {
            throw new EoException("Could not load url from " + getKey());
        }
        InputStream inp = null;
        try {
            inp = url.openStream();
        } catch (IOException e) {
            throw new EoException(e);
        }

        Workbook wb = null;
        try {
            return WorkbookFactory.create(inp);
        } catch (IOException e) {
            throw new EoException(e);
        } catch (InvalidFormatException e) {
            throw new EoException(e);
        }
    }

    private List getRowAsList(Row row) {
        if (row == null) {
            return null;
        }
        List rowValues = new ArrayList();
        boolean containsData = false;

        for (int i = 0; i < row.getLastCellNum(); i++) {
            final Cell cell = row.getCell(i);
            if (cell == null) {
                rowValues.add(null);
                continue;
            }
            String value = "";
            CellType cellType = cell.getCellTypeEnum();
            //dateFormatted=HSSFDateUtil.isCellDateFormatted(cell);
            //formulaResultType = cell.getCachedFormulaResultType();
            //https://stackoverflow.com/questions/7608511/java-poi-how-to-read-excel-cell-value-and-not-the-formula-computing-it
            try {
                if (cellType == CellType.STRING) {
                    String myValue = cell.getStringCellValue();
                    if (myValue != null && !myValue.isEmpty()) {
                        containsData = true;
                        rowValues.add(myValue);
                    } else {
                        rowValues.add(null);
                    }
                    continue;
                } else if (cellType == CellType.BOOLEAN) {
                    rowValues.add(cell.getBooleanCellValue());
                } else if (cellType != CellType.FORMULA && HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date dateValue = cell.getDateCellValue();
                    rowValues.add(dateValue);
                } else if (cellType == CellType.NUMERIC) {
                    Double doubleValue = cell.getNumericCellValue();
                    rowValues.add(doubleValue);
                } else if (cellType == CellType.BLANK) {
                    rowValues.add(null);
                } else if (cellType == CellType.FORMULA) {
                    switch (cell.getCachedFormulaResultType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                rowValues.add(cell.getDateCellValue());
                            } else {
                                rowValues.add(cell.getNumericCellValue());
                            }
                            break;
                        case Cell.CELL_TYPE_STRING:
                            rowValues.add(cell.getStringCellValue());
                            break;
                    }
                } else {
                    //http://apache-poi.1045710.n5.nabble.com/CELL-TYPE-FORMULA-String-vs-Numeric-td2304091.html
                    String formulaValue = cell.getStringCellValue();
                    rowValues.add(formulaValue);
                }
            } catch (Exception e) {
                e.printStackTrace();
                rowValues.add(null);
            }
        }
        if (containsData) {
            return rowValues;
        } else {
            return null;
        }
    }

    public void write(List rows) {
        Workbook wb = null;
        Sheet sheet = null;
        try {
            sheet = getSheet();
        } catch (Exception e) {
            throw new EoException(e);
        }
        int rowCounter = -1;
        for (Object row : rows) {
            rowCounter++;
            if (row == null) {
                Row xlsxRow = sheet.createRow(rowCounter);
                continue;
            }
            if (!(row instanceof List)) {
                Row xlsxRow = sheet.createRow(rowCounter);
                continue;
            }
            if (((List) row).isEmpty()) {
                Row xlsxRow = sheet.createRow(rowCounter);
                continue;
            }
            Row xlsxRow = sheet.getRow(rowCounter);
            if (xlsxRow == null) {
                xlsxRow = sheet.createRow(rowCounter);
            }
            List values = (List) row;
            for (int i = 0; i < values.size(); i++) {
                Object value = values.get(i);
                Cell cell = xlsxRow.createCell(i);
                try {
                    cell.setCellValue(ScalarConverter.toString(value));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            writeWorkbook(sheet.getWorkbook());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeWorkbook(Workbook wb)  {
        URL url = getUrl();
        //URLConnection connection = url.openConnection();

        //if (connection==null) {
        //    throw new Exception ("Could not load url from " + url.getFile());
        //}

        //file.createNewFile();

        try {
            OutputStream out = null;
            try {
                out = new FileOutputStream(url.getFile());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            wb.write(out);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            throw new EoException(e);
        }
    }

    //https://poi.apache.org/spreadsheet/quick-guide.html#NewWorkbook
    protected Sheet createSheet(final Workbook wb) {
        if (getSheetName().isEmpty()) {
            return wb.createSheet(new Integer(wb.getNumberOfSheets()).toString());
        } else {
            return wb.createSheet(getSheetName());
        }
    }



}
