package tea

import grails.converters.JSON

class T_case_handController {
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def t_moduleService
    def productService
    def t_planService
    def t_suiteService
    def t_caseService

    def index(Integer max) {
        [
            products:productService.getEnabledProductNames() as JSON
        ]

    }

    def loadByProduct(){
        def data = [:]
        def productName = params.product

        def currentProduct = productService.getEnabledProductByName(productName)

        def modules = t_moduleService.getModuleNamesByProduct(currentProduct)
        def plans = t_planService.getPlanNamesByProduct(currentProduct)
        def suites = t_suiteService.getSuitesNamesByProduct(currentProduct)

        def mm = t_moduleService.getModule([product: currentProduct ,name: "m1"])
        println("mm >>> "+ mm)

        data = [module:modules ,plan:plans ,suite:suites] as JSON
        println(data)
        render(data)
    }

    def loadCase(){
        int max = Integer.parseInt(params.length)
        params.max = Math.min(max ?: 10, 100)
        params.offset = Integer.parseInt(params.start)
        //
//        t_suiteService.getCasesIdBySuiteName("s1")
        def c = t_caseService.getCaseBySuiteName("s2",productService.getProductByName("p1"),params)
        println("c :"+ (c as JSON))

//        def m = ["case_":[1,2,3],"suite_":[1]]
//        def sm = m as JSON
//        println(sm)
        //
        def caseList = T_case.list(params)
        def caseCount = T_case.count
        def res = [recordsTotal: caseCount,
                   recordsFiltered: caseCount,
                   data: caseList
            ]
        render res as JSON
    }
}
