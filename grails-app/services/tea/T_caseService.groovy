package tea

import grails.transaction.Transactional
import groovy.json.JsonSlurper

//@Transactional
class T_caseService {
    def caseTitle= ["module","name","precondition","steps","expectation","prio","descr","keyword"]
    String mUser = "admin"

    def serviceMethod() {

    }
    def saveCase(T_case t_case){
        t_case.save()
    }
    def saveAllCases(List<T_case> t_cases){
        for(T_case tc : t_cases){
            tc.save()
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

        T_module t_module = T_module.findByM_name(cMap["module"])
        return new T_case(module_id: t_module.getId(),
                c_name: cMap["name"],
                precondition: cMap["precondition"],
                prio: cMap["prio"] as Integer,
                descr: cMap["descr"],
                keyword: cMap["keyword"],
                steps: createSteps(cMap["steps"],cMap["expectation"],mUser),
                c_author: mUser)
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
}
