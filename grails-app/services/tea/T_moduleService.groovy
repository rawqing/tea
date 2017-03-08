package tea

import grails.transaction.Transactional

@Transactional
/**
 * module 最大数量不能超过100个 (多了也看不过来)
 */
class T_moduleService {

    def serviceMethod() {

    }
    def getModulesMap(){
        println(T_module.findByM_name("m1"))
        def modules = T_module.findAll()
        println(modules.class)
//        println(modules[0].getM_name())
//        println(modules[0].getId())
        def mMap = [:]
        for(def tm : modules){
            mMap += [(tm.getM_name()):tm.getId()]
        }
        return mMap
    }
}
