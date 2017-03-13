package tea

import grails.transaction.Transactional

@Transactional
class T_planService {

    def getPlanMapByProduct(Product product){
        def plans = T_plan.getAll
    }
}
