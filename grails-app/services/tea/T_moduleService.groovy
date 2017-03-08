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
        def modules = T_module.findAll()
        def mMap = [:]
        for(def tm : modules){
            mMap += [(tm.getM_name()):tm.getId()]
        }
        return mMap
    }
}
