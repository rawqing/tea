package tea

import grails.converters.JSON
import grails.transaction.Transactional

import java.nio.ReadOnlyBufferException


class T_new_caseController {
    def t_caseService
    def t_moduleService
    def productService


    def index() {
        [title:t_caseService.caseTitle as JSON ,
         products:productService.getEnabledProductNames() as JSON,
         nullable:t_caseService.nullableColumn as JSON
        ]
    }

    @Transactional
    def ajax(){
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
//        def mo = t_moduleService.getModulesMap()
//        println(mo)
//        println(t_caseService.createColumns())

        render "changed"
    }

    def productChange(){
        def productName = params.product
        def product = productService.getEnabledProductByName(productName)
        def col =  t_caseService.createColumns(product)
        println(col)
        render(col)
    }
}
