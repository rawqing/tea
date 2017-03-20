package versioning

import grails.transaction.Transactional
import tea.Product
import tea.T_plan
import tea.Versioning

@Transactional
class VersioningService {

    def getVersioning(setting) {
        return [versions:Versioning.findAll(setting),
                count: Versioning.count()]
    }
    def getVersioning(Product product ,setting){
        return [versions:Versioning.findAllByProduct(product ,setting),
                count:Versioning.countByProduct(product)]
    }
    def getVersioning(T_plan plan , setting){
        return [versions:Versioning.findAllByPlan(plan ,setting),
                count: Versioning.countByPlan(plan)]
    }
}
