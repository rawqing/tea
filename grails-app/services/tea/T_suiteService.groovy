package tea

import constant.SUITE
import grails.transaction.Transactional
import groovy.json.JsonSlurper

@Transactional
class T_suiteService {

    def getSuitesNamesByProduct(Product product){
        def suites = T_suite.findAllByProduct(product)
        return suites*.getS_name()
    }
    def getCasesIdBySuiteName(String suiteName){
        def cList = []
        def suite = T_suite.findByS_name(suiteName)
        return getCasesIdBySuite(suite)
    }
    def getCasesIdBySuite(T_suite suite){
        def cList = []
        def caseSet = suite.getCaseSuites()
        def cases = new JsonSlurper().parseText(caseSet) as Map
        def cSet = cases[SUITE.case_.name()] ?: null
        println("cSet :"+ cSet)
        println(cSet.class)
        cList += cSet
        def sSet = cases[SUITE.suite_.name()] ?: null
        for(def sId : sSet){
            def childSuite = T_suite.get(sId)
            cList += getCasesIdBySuite(childSuite)
        }
        def oCList = cList.unique()
        println("OcList: "+oCList)
        return oCList
    }

    def getSuiteById(int id){
        return T_suite.get(id)
    }
}
