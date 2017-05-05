package tea

import constant.Common
import grails.transaction.Transactional
import utils.Gadget

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
        module.save(flush:true)
    }
    def saveInitModule(Module module){
        def mName = module.getM_name()
        Module cModule = saveModule(module)
        cModule.setPath(cModule.getId() as String)
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
    def cascadeSave(String pathMapping , Product product){
        Module module1 = Module.findByPathMapping(pathMapping)
        if(module1) return module1
        def ls = Gadget.subStringCrowdWith(pathMapping ,Common.cascade_separator)
        if(ls.size() == 1){
            Module m = Module.findByPathMapping(ls[0])
            if(! m){
                Module newM = new Module(m_name: ls[0] ,product: product ,mAuthor: User.get(1))
                return saveInitModule(newM)
            }
            return m
        }else{
            String path = ""
            Module m = null
            for(String s : ls){
                m = Module.findByPathMapping(s)
                if(!m) {
                    //不带分隔符, 表示该module为元模块
                    if (s.indexOf(Common.cascade_separator) < 0) {
                        Module newM = new Module(m_name: s ,product: product ,mAuthor: User.get(1))
                        path = saveInitModule(newM).getPath()
                    }else{
                        Module newM = new Module(m_name: Gadget.subEndStringWith(s,Common.cascade_separator)[1] ,
                                product: product ,mAuthor: User.get(1) ,pathMapping: s)
                        def sModule = saveModule(newM)
                        //拼接path , 并更新path的值
                        path = Gadget.separateAdd(path ,sModule.getId() ,Common.cascade_separator)
                        sModule.setPath(path)
                        m = updateModule(sModule)
                    }
                }else{
                    path = m.getPath()
                }
            }
            return m
        }
    }



    def getModuleMappingsByProduct(Product product){

        def modules = Module.findAllByProduct(product)
        println("<<>>>>> modules"+modules)
        return modules*.getPathMapping()
    }

    def getModule(String mapping ,Product product ){
        return Module.findByPathMappingAndProduct(mapping ,product)
    }

}
