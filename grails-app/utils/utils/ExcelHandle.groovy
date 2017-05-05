package utils

import constant.Common
import constant.Conf
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
         rowEnd = Math.min(2000,sheet.getLastRowNum())
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
        return getRowText(row)
    }
    def getRowText(Row row){
        def rowText = []
        for(int i=0; i<lastColumn ; i++){
            Cell cell = row?.getCell(i ,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)
            rowText += formatter.formatCellValue(cell)
        }
        return rowText
    }

    def getAllData(){
        def allData = []
        for(int i = rowStart +1 ;i <= rowEnd ;i++){
            allData.add(getRowText(i))
        }
        return allData
    }
    def getAllCases(){
        def allData = []
        def module = getRowText(rowStart+1).get(0)?:Common.default_module_name
        for(int i = rowStart +1 ;i <= rowEnd ;i++){
            def row = getRowText(i)
            if(row.get(0)){
                module = row.get(0)
            }else{
                row[0] = module
            }
            //校验一行中不能为空的数据
            for(int j=0;j<row.size() ; j++){
                if (!(j in Conf.nullableColumns)) {
                    if(!row[j]){
                        println("row : ${row} 第 ${j+1} 列数据为空 ,将不再继续读取 !")
                        return allData
                    }
                }
            }
            allData.add(row)
        }
//        for(int i = rowStart +1 ;i <= rowEnd ;i++){
//
//        }
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
