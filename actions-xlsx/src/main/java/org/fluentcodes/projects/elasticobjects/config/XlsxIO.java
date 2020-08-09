package org.fluentcodes.projects.elasticobjects.config;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListIO;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListIOInterface;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParams;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

;

public class XlsxIO extends ListIO implements ListIOInterface {
    private Sheet sheet;
    private int actualRow = 0;
    private int emptyCounter = 0;

    public XlsxIO(XlsxConfig xlsxConfig)  {
        super(xlsxConfig);
        sheet = getSheet();
    }

    public XlsxConfig getXlsxConfig() {
        return (XlsxConfig) getListConfig();
    }

    public Object read()  {
        return super.read(new ListParams());
    }

    public void write(Object object)  {
        this.write((List) object);
    }

    public void reset()  {
        actualRow = 0;
        if (sheet == null) {
            this.sheet = getSheet();
        }
    }

    public void close()  {
        if (sheet != null) {
            sheet.getWorkbook().close();
            sheet = null;
        }
    }


    private final Sheet getSheet()  {
        Workbook wb = readWorkbook();
        if (getXlsxConfig().hasNoSheetName()) {
            return wb.getSheetAt(0);
        } else {
            return wb.getSheet(getXlsxConfig().getSheetName());
        }
    }

    private final Workbook readWorkbook()  {
        URL url = getXlsxConfig().getFileConfig().getUrl();
        if (url == null) {
            throw new Exception("Could not load url from " + getXlsxConfig().getFileConfig().getFileKey());
        }
        InputStream inp = url.openStream();

        Workbook wb = WorkbookFactory.create(inp);
        if (wb == null) {
            throw new Exception("Could not create Workbook " + url.toString());
        }
        return wb;
    }

    public List readRow()  {
        List nextRow = readRow(actualRow + 1);
        if (nextRow == null) {
            emptyCounter++;
            return null;
        }
        emptyCounter = 0;
        return nextRow;
    }

    public List readHead(int rownum)  {
        return readRow(rownum);
    }

    public List readRow(int rowNum)  {
        if (rowNum < 0) {
            return null;
        }
        this.actualRow = rowNum;
        return getRowAsList(sheet.getRow(rowNum));
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
        try {
            sheet = getSheet();
        } catch (Exception e) {
            try {
                sheet = createSheet(wb);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
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
        URL url = getXlsxConfig().getFileConfig().getUrl();
        //URLConnection connection = url.openConnection();

        //if (connection==null) {
        //    throw new Exception ("Could not load url from " + url.getFile());
        //}

        //file.createNewFile();
        OutputStream out = new FileOutputStream(url.getFile());
        wb.write(out);
        out.flush();
        out.close();
    }

    //https://poi.apache.org/spreadsheet/quick-guide.html#NewWorkbook
    protected Sheet createSheet(final Workbook wb) {
        if (getXlsxConfig().getSheetName().isEmpty()) {
            return wb.createSheet(new Integer(wb.getNumberOfSheets()).toString());
        } else {
            return wb.createSheet(getXlsxConfig().getSheetName());
        }
    }

}
