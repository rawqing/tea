package utils

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

/**
 * Created by king on 17/5/3.
 * 表格必须由 A1 单元格开始
 */
class ExcelHandle {
    String excelPath
    Workbook wb
    Sheet sheet
    DataFormatter formatter = new DataFormatter()
    int rowStart
    int rowEnd
    int lastColumn

     ExcelHandle(String excelPath){
        this.excelPath = excelPath
         wb = WorkbookFactory.create(new File(excelPath))
         sheet = wb.getSheetAt(0)
         rowStart = Math.min(15,sheet.getFirstRowNum())
         rowEnd = Math.max(1400,sheet.getLastRowNum())
         lastColumn = Math.max(sheet.getRow(0).getLastCellNum(),5)
    }

    def readCellText(Cell cell){
        formatter.formatCellValue(cell)
    }
    def getTitle(){
        Row row = sheet.getRow(0)
        def titles = []
        for(Cell cell : row){
            titles.add(readCellText(cell))
        }
        return titles
    }
    def getRowText(int rowNumber){
        Row row = sheet.getRow(rowNumber)
        def rowText = []
        for(int i=0; i<lastColumn ; i++){
            rowText += row.getCell(i ,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)
        }
        return rowText
    }
    def getAllData(){
        def allData = []
        for(int i = rowStart +1 ;i < rowEnd ;i++){
            allData += getRowText(i)
        }
        return allData
    }

    def read(){
        Row row = sheet.getRow(0)
        Cell cell = row.getCell(0)
        cell.getCellTypeEnum()
        String text = formatter.formatCellValue(cell)
    }

    def close(){
        wb.close()
    }
}
