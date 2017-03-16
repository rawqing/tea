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
    def getCasesId(String suiteName, Product product){
        def suite = getSuite(suiteName ,product)
        return getCasesId(suite)
    }
    def getCasesId(T_suite suite){
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
            cList += getCasesId(childSuite)
        }
        def oCList = cList.unique()
        println("OcList: "+oCList)
        return oCList
    }

    def getSuite(int id){
        return T_suite.get(id)
    }
    def getSuite(String name ,Product product){
        return T_suite.findByS_nameAndProduct(name ,product)
    }
    def getSuites(List<Integer> ids ){
        return T_suite.findAllByIdInList(ids)
    }
}
