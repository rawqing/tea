package tea

import constant.Conf
import grails.converters.JSON
import grails.transaction.Transactional
import groovy.json.JsonSlurper

@Transactional
class T_caseService {
    def moduleService
    def filmService
    def t_suiteService
    def t_planService
    def productService
    def versioningService
    def caseTitle= ["module","name","precondition","steps","expectation","prio","descr","keyword"]
    def showCaseTitle = ["0":["id","id"] ,"1":["c_name","名称"] ,"2":["judge","评审结果"] ,
                         "3":["prio","优先级"] ,"4":["lastUpdated","最后更新"],"5":["","操作"]]
    def nullableColumn = Conf.nullableColumns

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
        if(!t_case.save(flush:true)){
            println(t_case.errors)
        }
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
    /**
     * 将字符串case 转化为case对象
     * @param caseStr
     * @return
     */
    def spitCases(String caseStr , String productName){
        def c = new JsonSlurper().parseText(caseStr) as List
        def cases = []
        for(int i=0; i < c.size()-1;i++){
            cases += createCase(c[i] as List ,productName)
        }
        return cases
    }
    def createCase(List<String> data ,String productName){
        Map<?,String> cMap = [:]
        if(data.size() != caseTitle.size()){
            return
        }
        for (int i = 0; i < data.size(); i++) {
            cMap += [(caseTitle[i]):data[i]]
        }
        if(cMap["name"]==null || "" == cMap["name"]){
            return
        }
        def product = productService.getEnabledProductByName(productName)
        def module = moduleService.getModule(cMap["module"] ,product)

        //如果module不存在则创建
        if(!module){
           module = moduleService.cascadeSave(cMap["module"] ,product)
        }
        def mUser = User.get(1)
        T_case t_case = new T_case(
                c_name: cMap["name"],
                precondition: cMap["precondition"],
//                prio: cMap["prio"] as Integer,
                descr: cMap["descr"],
                keyword: cMap["keyword"]);
        for(T_step t_step : createSteps(cMap["steps"],cMap["expectation"],mUser)){
            t_case.addToSteps(t_step)
        }
        if(cMap["prio"] && cMap["prio"].isInteger()) t_case.setPrio(cMap["prio"] as Integer)
        t_case.setModule(module)
        t_case.setProduct(product)
        t_case.setmAuthor(mUser)
        return t_case
    }
    def createSteps(String step_desc ,String expectation ,User user){
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
            T_step step = new T_step(s_step:sd[i],s_expect: ex ,mAuthor: user)
            steps += step
        }
        return steps
    }

    def createColumns(Product product){
//        def mPathMapping = moduleService.getModulesMapByProduct(product).keySet()
        def mPathMapping = moduleService.getModuleMappingsByProduct(product)
        def columns = []
        for (int i = 0; i < caseTitle.size(); i++) {
            def col = [:]
            switch (caseTitle[i]){
                case "module":
                    col = [type: 'autocomplete',
                           source:mPathMapping,
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

    def getCaseBySuiteName(String t_suiteName ,Product product ,settings){
        def cIds = t_suiteService.getCasesId(t_suiteName ,product)
        return [count: cIds.size(),case:T_case.findAllByIdInList(cIds ,settings)?:[]]
    }
    def getCaseByModuleName(String moduleName ,Product product ,settings){
        Module module = moduleService.getModule(moduleName ,product)

        return [count: T_case.countByModule(module),
                case:T_case.findAllByModule(module,settings)?:[]
        ]
    }
    def getCasesByVersionName(String verName ,Product product ,settings){
        def ver = versioningService.getVersioning(product ,verName)
        return [count: T_case.countByVersioning(ver),case: T_case.findByVersioning(ver ,settings)?:[]]
    }

    def getCases(Product product ,setting){
        return T_case.findAllByProduct(product ,setting)
    }
    def getCount(){
        return T_case.getCount()
    }
    def getCount(Product product){
        return T_case.countByProduct(product)
    }
}
