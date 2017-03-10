package tea

import grails.converters.JSON

class T_case_handController {
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def t_moduleService
    def productService

    def index() {
        [
            products:productService.getEnabledProductMap().keySet() as JSON
        ]
    }

    def loadModule(){
        def productName = params.product
        def pMap = productService.getEnabledProductMap()

        def m = t_moduleService.getModulesMapByProduct(pMap[productName]).keySet() as JSON
        println(m)
        render(m)
    }
}
