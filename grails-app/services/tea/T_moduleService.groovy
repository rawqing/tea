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

    def getModule(String name ,Product product ){
        return T_module.findByM_nameAndProduct(name ,product)
    }
}
