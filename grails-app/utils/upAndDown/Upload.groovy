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
        for(def oneCase : eh.getAllData()){

        }
    }
}
