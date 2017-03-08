package tea

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.json.JsonSlurper

@Transactional
class T_caseService {
    def t_moduleService
    def filmService
    def caseTitle= ["module","name","precondition","steps","expectation","prio","descr","keyword"]
    String mUser = "admin"
    String showCase = "t_case_hand"

    def serviceMethod() {

    }
    /**
     * 保存一个case , 并将其注册到 Film
     * @param t_case
     * @return
     */
    def saveCase(T_case t_case){
        if(t_case == null){
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        if(t_case.hasErrors()){
            transactionStatus.setRollbackOnly()
            respond(t_case.errors)
            return
        }
        t_case.save(flush:true)
        def c_id = t_case.getId()
        filmService.saveFilm(filmService.createFilm("t_case",c_id ,showCase))
        println(c_id)
        return c_id
    }
    def saveAllCases(List<T_case> t_cases){
        for(T_case tc : t_cases){
            saveCase(tc)
        }
    }
    def spitCases(String caseStr){
        def c = new JsonSlurper().parseText(caseStr) as List
        def cases = []
        for(int i=0; i < c.size()-1;i++){
            cases += createCase(c[i] as List)
        }
        return cases
    }
    def createCase(List<String> data){
        Map<?,String> cMap = [:]
        if(data.size() != caseTitle.size()){
            return
        }
        for (int i = 0; i < data.size(); i++) {
            cMap += [(caseTitle[i]):data[i]]
        }

        String mId = t_moduleService.modulesMap[cMap["module"]]
        T_case t_case = new T_case(module_id: mId,
                c_name: cMap["name"],
                precondition: cMap["precondition"],
                prio: cMap["prio"] as Integer,
                descr: cMap["descr"],
                keyword: cMap["keyword"],
                c_author: mUser);
        for(T_step t_step : createSteps(cMap["steps"],cMap["expectation"],mUser)){
            t_case.addToSteps(t_step)
        }
        return t_case
    }
    def createSteps(String step_desc ,String expectation ,String user){
        def sd = step_desc.split("\n")
        def es = expectation.split("\n")
        def steps = []
        for (int i = 0; i < sd.size(); i++) {
            String ex
            try {
                ex = es[i]
            }catch(ArrayIndexOutOfBoundsException a){
                ex = ""
            }catch(Exception e){e.printStackTrace()}
            T_step step = new T_step(s_step:sd[i],s_expect: ex ,s_author: user)
            steps += step
        }
        return steps
    }

    def createColumns(){
        def modulesNames = t_moduleService.getModulesMap().keySet()
        def columns = []
        for (int i = 0; i < caseTitle.size(); i++) {
            def col = [:]
            switch (caseTitle[i]){
                case "module":
                    col = [type: 'autocomplete',
                           source:modulesNames,
                           strict: true,
                           allowInvalid: false
                    ]
                    break
                case "prio":
                    col = [type: 'autocomplete',
                           source:['1','2','3','4'],
                           strict: true,
                           allowInvalid: false
                    ]
                    break
            }
            columns += col
        }
        return columns as JSON
    }
}
