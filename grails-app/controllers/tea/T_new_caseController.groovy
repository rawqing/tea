package tea

class T_new_caseController {

    def index() { }

    def ajax(){
        println(params)
        println(params.get("case"))
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
