package tea

import constant.Common
import grails.transaction.Transactional
import static constant.Common.CON
import tea.Product
import tea.T_plan
import tea.Versioning

@Transactional
class VersioningService {

    def getAllVersioning(setting) {
        return [versions:Versioning.findAll(setting),
                count: Versioning.count()]
    }
    def getAllVersioning(Product product, setting){
        return [versions:Versioning.findAllByProduct(product ,setting),
                count:Versioning.countByProduct(product)]
    }
    def getAllVersioning(T_plan plan, setting){
        return [versions:Versioning.findAllByPlan(plan ,setting),
                count: Versioning.countByPlan(plan)]
    }
    def getVersioning(Product product, String name){
        def s = name?.split(CON)
        if(s.size() < 3){return }
        def platform = s[0]
        def ver = s[1]
        def edition = s[2]
//        Versioning.findWhere(product: product ,platform: platform ,edition: edition ,t_version: ver)
        return Versioning.findByProductAndPlatformAndEditionAndT_version(product ,platform ,edition ,ver)
    }
    def getVersioningNames(){
        def names = []
        def vs = Versioning.getAll()
        for(Versioning v : vs){
            names += v.getPlatform()+ CON +v.getT_version()+ CON +v.getEdition()
        }
        println(names)
        return names
    }
}
