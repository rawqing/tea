package tea

import grails.transaction.Transactional

@Transactional
/**
 * module 最大数量不能超过100个 (多了也看不过来)
 */
class T_moduleService {
    def productService

    def getModulesMap(){
        def modules = T_module.findAll()
        def mMap = [:]
        for(def tm : modules){
            mMap += [(tm.getM_name()):tm]
        }
        return mMap
    }

    def getModulesMapByProduct(Product product){
        def modules = T_module.findAllByProduct(product)
        def mMap = [:]
        for(def tm : modules){
            mMap += [(tm.getM_name()):tm]
        }
        return mMap
    }

    def getModulesMapByProductName(String name){
        return getModulesMapByProduct(productService.getEnabledProductByName(name))
    }

    def getModuleNamesByProduct(Product product){
        def modules = T_module.findAllByProduct(product)
        return modules*.getM_name()
    }

    def getModuleByName(String name){
        return T_module.findAllByM_name(name)
    }

    def getModule(Map m){
        def a = T_module.createCriteria()
        def c = a.list {
            def sql = ""
            def value = []
            if(m.product){
                sql += "product_id = ?"
                value += m.product.getId()
            }
            if(m.name){
                sql += sql?" AND ":""
                sql += "m_name = ?"
                value += m.name
            }
            println(sql)
            sqlRestriction sql ,value
        }
        return c
    }
}
