package tea

import grails.converters.JSON

class T_new_caseController {

    def index() {
        List<String> column_title = ["module","name","precondition","steps","expectation","prio","descr","keyword"]

        [col:column_title as JSON]
    }

    def ajax(){
        println(params)
        println(params.get("case").class)
        println(params.case)
        for (int i = 0; i < params.size(); i++) {
            println(params[i])
        }
        render "hello"
    }
    def modified(){
        println(params.data)
        render "changed"
    }
}
