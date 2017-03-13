package tea

import grails.converters.JSON

class T_case_handController {
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def t_moduleService
    def productService
    def t_planService

    def index() {
        [
            products:productService.getEnabledProductMap().keySet() as JSON
        ]
    }

    def loadByProduct(){
        def data = [:]
        def productName = params.product
        def pMap = productService.getEnabledProductMap()

        def modules = t_moduleService.getModulesMapByProduct(pMap[productName]).keySet()
        def plans = t_planService.getPlanNameByProduct(pMap[productName])
        data = [module:modules ,plan:plans] as JSON
        println(data)
        render(data)
    }
}
