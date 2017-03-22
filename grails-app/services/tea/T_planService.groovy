package tea

import constant.SUITE
import grails.transaction.Transactional
import groovy.json.JsonSlurper

@Transactional
class T_planService {

    def getPlanNamesByProduct(Product product){
        def plans = T_plan.findAllByProduct(product)
        return plans*.getP_name()
    }

//    def getCasesIdByPlanName(String name , Product product){
//        def plan = getPlan(name ,product)
////        return getCasesIdByPlan(plan)
//    }
//    def getCasesIdByPlan(T_plan t_plan){
//        def cList = []
//        def suiteSet = t_plan.getHasSuites()
//        def suites = new JsonSlurper().parseText(suiteSet) as Map
//
//        def sSet = suites[SUITE.suite_.name()] ?: null
//        def sList = t_suiteService.getSuites(sSet)
//        for(def childSuite : sList){
//            cList += t_suiteService.getCasesId(childSuite)
//        }
//        def oCList = cList.unique()
//        println("OcList: "+oCList)
//        return oCList
//    }

    def getPlan(String name){
        return T_plan.findByP_name(name)
    }
    def getPlan(String name ,Product product){
        return T_plan.findByP_nameAndProduct(name,product)
    }
}
