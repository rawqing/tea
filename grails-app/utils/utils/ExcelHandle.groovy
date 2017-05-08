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
    boolean isWrite = false

     ExcelHandle(String excelPath){
        this.excelPath = excelPath
         wb = WorkbookFactory.create(new File(excelPath))
         sheet = wb.getSheetAt(0)
         rowStart = Math.min(15,sheet.getFirstRowNum())
         rowEnd = Math.min(2000,sheet.getLastRowNum())
         lastColumn = Math.max(sheet.getRow(0).getLastCellNum(),5)
    }
    ExcelHandle(String excelPath , boolean isWrite){
        this.isWrite = isWrite
        this.excelPath = excelPath
        wb = new XSSFWorkbook()
        sheet = wb.createSheet()
    }
    /**
     * 格式化单元格数据 , 这里格式化为String
     * @param cell
     * @return String
     */
    def readCellText(Cell cell){
        formatter.formatCellValue(cell)
    }

    /****************************************  read data  *****************************************/

    /**
     * 获得表头
     * @return
     */
    def getTitle(){
        Row row = sheet.getRow(0)
        def titles = []
        for(Cell cell : row){
            titles.add(readCellText(cell))
        }
        return titles
    }
    /**
     * 获得一行数据
     * @param rowNumber
     * @return
     */
    def getRowText(int rowNumber){
        Row row = sheet.getRow(rowNumber)
        return getRowText(row)
    }
    def getRowText(Row row){
        def rowText = []
        for(int i=0; i<lastColumn ; i++){
            Cell cell = row?.getCell(i ,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)
            rowText += readCellText(cell)
        }
        return rowText
    }

    /**
     * 单纯的获取xlsx表中的所有数据
     * @return
     */
    def getAllData(){
        def allData = []
        for(int i = rowStart +1 ;i <= rowEnd ;i++){
            allData.add(getRowText(i))
        }
        return allData
    }
    /**
     * 获取xlsx文件中的所有 {@link tea.T_case}
     * @return List cases 数据 , 而不是实际的 T_case 对象
     */
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
        return allData
    }

    /****************************************  write data  *****************************************/
    def writeRow(Row row , List data){
        for(int i = 0 ; i < data.size() ; i++){
            row.createCell(i).setCellValue(data.get(i))
        }
    }
    def writeRow(int rowNumber , List data){
        writeRow(sheet.createRow(rowNumber) ,data)
    }

    def writeTitle(){
        writeRow(0 ,Conf.tpCaseTitle)
    }

    def writeAllData(List<List> data){
        int i = 0
        for(def rd : data){
            ++i
            writeRow(i , data)
        }
    }


    /****************************************  close res *****************************************/
    def close(){
        wb.close()
    }
}
