package upAndDown

import utils.ExcelHandle

/**
 * Created by king on 17/5/3.
 */
class Upload {

    def uploadCases(){

    }

    def initCaseData(String path){
        ExcelHandle eh = new ExcelHandle(path)
        def allData = eh.getAllCases()
        for(def oneCase : allData){
//            println(oneCase)

        }
        eh.close()
    }
}
