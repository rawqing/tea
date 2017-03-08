package tea

import grails.converters.JSON
import grails.transaction.Transactional

import java.nio.ReadOnlyBufferException


class T_new_caseController {
    def t_caseService
    def t_moduleService

    def index() {
        [title:t_caseService.caseTitle as JSON]
    }

    @Transactional
    def ajax(){
        String pc = params.case
        def t_cases = t_caseService.spitCases(pc)
        t_caseService.saveAllCases(t_cases)
        render "data saved"
    }
    @Transactional
    def modified(){
//        println(params.data)
        println(t_moduleService.getModulesMap())
        render "changed"
    }
}
