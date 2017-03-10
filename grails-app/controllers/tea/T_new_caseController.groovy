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
         products:productService.getEnabledProductMap().keySet() as JSON,
         nullable:t_caseService.nullableColumn as JSON
        ]
    }

    @Transactional
    def ajax(){
        String pc = params.case
        def t_cases = t_caseService.spitCases(pc)
        if(t_caseService.saveAllCases(t_cases)) {
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
        def pMap = productService.getEnabledProductMap()

        def col =  t_caseService.createColumns(pMap[productName])
        println(col)
        render(col)
    }
}
