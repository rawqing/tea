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

        data = [module:modules ,plan:plans ,suite:suites] as JSON
        println(data)
        render(data)
    }

    def loadCase(){
        def caseList,
             caseCount;
        int max = Integer.parseInt(params.length)
        def sortColumn= params["order[0][column]"]
        def sortTitle = t_caseService.showCaseTitle[sortColumn].get(0)
        params.max = Math.min(max ?: 10, 100)
        params.offset = Integer.parseInt(params.start)
        params.sort = sortTitle ?: "id"
        params.order = params["order[0][dir]"] ?: "asc"
        def pName = params.t_product
        def action = params.t_action
        def name = params.t_name
        def product = pName?productService.getEnabledProductByName(pName):Product.get(2)
        if(action){
            switch (action){
                case "all":
                    caseList = t_caseService.getCases(product ,params)
                    caseCount = t_caseService.getCount(product)
                    break
                case "module":
                    if(name){
                        def tmp = t_caseService.getCaseByModuleName(name ,product ,params)
                        caseList = tmp.case
                        caseCount = tmp.count
                    }
                    break
                case "plan":
                    if(name){
                        def tmp = t_caseService.getCaseByPlanName(name ,product ,params)
                        caseList = tmp.case
                        caseCount = tmp.count
                    }
                    break
                case "suite":
                    if(name){
                        def tmp = t_caseService.getCaseBySuiteName(name ,product ,params)
                        caseList = tmp.case
                        caseCount = tmp.count
                    }
                    break
                default:
                    caseList = t_caseService.getCases(product ,params)
                    caseCount = t_caseService.getCount(product)
            }
        }else {
            caseList = t_caseService.getCases(product ,params)
            caseCount = t_caseService.getCount(product)
        }
        def res = [recordsTotal: caseCount,
                   recordsFiltered: caseCount,
                   data: caseList
            ]
        def t = res as JSON
        println(t)
        render t
    }
}
