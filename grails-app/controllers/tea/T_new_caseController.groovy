package tea

import grails.converters.JSON
import grails.transaction.Transactional


class T_new_caseController {
    def t_caseService

    def index() {
        [title:t_caseService.caseTitle as JSON]
    }

    @Transactional
    def ajax(){
        String pc = params.case
        def t_cases = t_caseService.spitCases(pc)
        for(T_case tc : t_cases){
            if (tc.hasErrors()) {
                println("has errors")
            }
            tc.save(flush:true)
        }

        render "hello"
    }
    def modified(){
        println(params.data)
        render "changed"
    }
}
