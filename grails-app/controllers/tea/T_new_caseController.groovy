package tea

import com.fasterxml.jackson.databind.util.JSONPObject
import grails.converters.JSON
import groovy.json.JsonSlurper
import org.grails.web.json.JSONObject

class T_new_caseController {
    T_caseService cs = new T_caseService()
    def index() {
//        List<String> column_title = ["module","name","precondition","steps","expectation","prio","descr","keyword"]

        [col:cs.caseTitle as JSON]
    }

    def ajax(){
        println(params)
        println(params.get("case"))
        String pc = params.case
//        pc = "{$pc}"
        println(pc)
        def a = new JsonSlurper().parseText(pc)
        println(a.class)
        println(a.size())
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
