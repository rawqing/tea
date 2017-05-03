package tea

import constant.Common
import grails.transaction.Transactional

@Transactional
class ModuleService {

    def saveModule(Module module){
        if(module == null){
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        if(module.hasErrors()){
            transactionStatus.setRollbackOnly()
            respond(module.errors)
            return
        }
        if(!module.save(flush:true)){
            println(module.errors)
        }
        def m_id = module.getId()
    }
    def saveInitModule(Module module){
        def mName = module.getM_name()
        def mid = saveModule(module)
        Module cModule = Module.get(mid)
        cModule.setPath(mid.toString())
        cModule.setPathMapping(mName)
        updateModule(cModule)
    }

    def updateModule(Module module) {
        if (module == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        if (module.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond module.errors, view: 'edit'
            return
        }
        module.save flush: true
    }
    def cascadeSave(String moduleName){
        String[] ms = moduleName.split(Common.cascade_separator)
        if(ms.length == 1){
            return saveModule(new Module(m_name: ms[0]))
        }
        Module parent = new Module(m_name: ms[0])
        for(int i=1; i<ms.length ; i++){
            parent = parent.setSubmodule()
        }
    }

    def getModuleNamesByProduct(Product product){

        def modules = Module.findAllByProduct(product)
        println("<<>>>>> modules"+modules)
        return modules*.getM_name()
    }

    def getModule(String name ,Product product ){
        return Module.findByM_nameAndProduct(name ,product)
    }

}
