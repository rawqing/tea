package tea

import grails.transaction.Transactional

@Transactional
/**
 * module 最大数量不能超过100个 (多了也看不过来)
 */
class T_moduleService {

    def getModuleNamesByProduct(Product product){
        def modules = T_module.findAllByProduct(product)
        return modules*.getM_name()
    }

    def getModule(Map m){
        if(m.id){
            return T_module.get(m.id)
        }
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
