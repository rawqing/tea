package tea

import constant.Conf
import constant.OUTER
import grails.converters.JSON
import groovy.json.JsonSlurper
import httpx.OuterHttp
import tpondemand.CasesHandle
import upAndDown.Upload
import utils.ExcelHandle
import utils.FileRW
import grails.transaction.Transactional
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest

import java.nio.ReadOnlyBufferException


class T_new_caseController {
    def t_caseService
    def moduleService
    def productService
    def versioningService


    def index() {
        [title:t_caseService.caseTitle as JSON ,
         products:productService.getEnabledProductNames() as JSON,
         nullable:t_caseService.nullableColumn as JSON
        ]
    }

    @Transactional
    def saveCases(){
        String pc = params.case
        String p_name = params.product
        def t_cases = t_caseService.spitCases(pc,p_name)
        if(!t_caseService.saveAllCases(t_cases)) {
            render "data saved"
        }else{
            render("data not saved")
        }
    }
    @Transactional
    def modified(){
        println(params.data)
        def product = Product.get(1)
        def user = User.get(1)
//        T_case t_case = new T_case(module: Module.get(10) ,mAuthor: user,c_name: "case01" ,product: product)
//        t_case.setOuterId(([tp:"0031" ,zentao:"123"] as JSON).toString())
//        t_caseService.saveCase(t_case)
        def c = T_case.get(4)
//        def o = c.getOuterId()
//        def oid = new JsonSlurper().parseText(o) as Map
//        println("oid class = "+oid.getClass())
//        def os = oid["tp"]
//        println("os = "+os)

        def tpc = new CasesHandle().createTpCase(c ,3514) as JSON
        println(tpc)
//
        def oh = new OuterHttp("https://trubuzz.tpondemand.com/api/v1/testCases?resultInclude=[Id]")
//        oh.doPost(tpc.toString())
//        def oh = new OuterHttp("https://trubuzz.tpondemand.com/api/v1/testCases/6357")
//        def oh = new OuterHttp("http://www.baidu.com")
//        oh.doGet()
        render "changed"
    }

    def productChange(){
        def productName = params.product
        def product = productService.getEnabledProductByName(productName)
        def col =  t_caseService.createColumns(product)
        println(col)
        render(col)
    }

    @Transactional
    def upload(){
        def uploadedFile = request.getFile('cases_file')

        if(uploadedFile != null && uploadedFile.size > 0){
            println "Class: ${uploadedFile.class}"
            println "Name: ${uploadedFile.name}"
            println "OriginalFileName: ${uploadedFile.originalFilename}"
            println "Size: ${uploadedFile.size}"
            println "ContentType: ${uploadedFile.contentType}"
        }else   return

        FileRW rw = new FileRW()
        def path = rw.writeTempFile(uploadedFile)
        println(path)
        ExcelHandle eh = new ExcelHandle(path)
        def allData = eh.getAllCases()
        def cases = []
        for(def data : allData){
            cases.add(t_caseService.createCase(data ,"p1"))
        }
        t_caseService.saveAllCases(cases)
        eh.close()
        render("hello")
    }

    @Transactional(readOnly = true)
    def pushTp(T_case tCase , int pid){
        def tpCasePath = "/testCases?resultInclude=[Id]"
        def callBack = { resp ,reader ->
            def tid = reader.attributes()["Id"]
            t_caseService.updateOuterId(tCase ,["${OUTER.TP}":tid])
        }
        OuterHttp oh = new OuterHttp(Conf.tp_serverurl + tpCasePath )
        def tpc = new CasesHandle().createTpCase(tCase ,pid) as JSON
        oh.doPost(tpc.toString() ,callBack)
    }

}
