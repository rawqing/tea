package tea

import grails.converters.JSON

class VersioningController {
    def productService
    def versioningService
    def t_planService
    def showVersionTitle = ["0":["id","id"] ,"1":["platform","platform"] ,"2":["edition","edition"] ,
                         "3":["t_version","version"] ,"4":["releaseTime","release time"],"5":["","operate"]]

    def index() {
        [
                products:productService.getEnabledProductNames() as JSON
        ]
    }

    def loadVersioning(){
        def versionList,
            versionCount = 0;
        def sortColumn= params["order[0][column]"]
        def sortTitle = showVersionTitle[sortColumn].get(0)
        int max = Integer.parseInt(params.length)
        def pName = params.t_product
        def planName = params.t_plan
        params.max = Math.min(max ?: 10, 100)
        params.offset = Integer.parseInt(params.start)
        params.sort = sortTitle ?: "id"
        params.order = params["order[0][dir]"] ?: "asc"

        if(pName && planName){

        }else{
            if(pName){
                def product = productService.getEnabledProductByName(pName)

                println("pr >>>"+ product)
                def tmp = versioningService.getVersioning(product ,params)
                versionList = tmp.versions
                versionCount = tmp.count
            }
            if(planName){
                def plan = t_planService.getPlan(planName)
                def tmp = versioningService.getVersioning(plan , params)
                versionList = tmp.versions
                versionCount = tmp.count
            }
        }
        def res = [recordsTotal: versionCount,
                   recordsFiltered: versionCount,
                   data: versionList
        ]
        def t = res as JSON
        println(t)
        render t
    }

    def create(){

    }
}
