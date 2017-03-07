package tea

import grails.converters.JSON


class T_new_caseController {
    T_caseService cs = new T_caseService()
    def index() {
        [col:cs.caseTitle as JSON]
    }

    def ajax(){
        String pc = params.case
        def t_cases = cs.spitCases(pc)
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
