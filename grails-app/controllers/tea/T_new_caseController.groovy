package tea

import grails.converters.JSON
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
        Module m = new Module(m_name: "m1" ,mAuthor: User.get(1), product: Product.get(1))
        moduleService.saveInitModule(m)

        render "changed"
    }

    def productChange(){
        def productName = params.product
        def product = productService.getEnabledProductByName(productName)
        def col =  t_caseService.createColumns(product)
        println(col)
        render(col)
    }

    def upload(){
        def req = request
        def p = params
        def uploadedFile = request.getFile('cases_file')

        if(uploadedFile != null && uploadedFile.size > 0){
            println "Class: ${uploadedFile.class}"
            println "Name: ${uploadedFile.name}"
            println "OriginalFileName: ${uploadedFile.originalFilename}"
            println "Size: ${uploadedFile.size}"
            println "ContentType: ${uploadedFile.contentType}"
        }
        FileRW rw = new FileRW()
        def path = rw.writeTempFile(uploadedFile)
        println(path)
        ExcelHandle eh = new ExcelHandle(path)
        def out = eh.getTitle()
        eh.close()
        render(out)
    }
}
