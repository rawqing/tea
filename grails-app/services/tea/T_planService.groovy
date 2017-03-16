package tea

import constant.SUITE
import grails.transaction.Transactional
import groovy.json.JsonSlurper

@Transactional
class T_planService {
    def t_suiteService

    def getPlanNamesByProduct(Product product){
        def plans = T_plan.findAllByProduct(product)
        return plans*.getP_name()
    }

    def getCasesIdByPlanName(String name){
        def plan = T_plan.findByP_name(name)
        return getCasesIdByPlan(plan)
    }
    def getCasesIdByPlan(T_plan t_plan){
        def cList = []
        def suiteSet = t_plan.getHasSuites()
        def suites = new JsonSlurper().parseText(suiteSet) as Map

        def sSet = suites[SUITE.suite_.name()] ?: null
        for(def sId : sSet){
            def childSuite = t_suiteService.getSuiteById(sId)
            cList += t_suiteService.getCasesIdBySuite(childSuite)
        }
        def oCList = cList.unique()
        println("OcList: "+oCList)
        return oCList
    }
}
