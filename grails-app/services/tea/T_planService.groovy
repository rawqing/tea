package tea

import grails.transaction.Transactional

@Transactional
class T_planService {

    def getPlanNameByProduct(Product product){
        def plans = T_plan.findAllByProduct(product)
        return plans*.getP_name()
    }
}
