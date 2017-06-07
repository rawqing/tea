package tea.tt

import groovy.json.JsonSlurper

/**
 * Created by king on 17/5/8.
 */
class SubR {

    def a = 'coffee'



    def run(){
       def path = "E:\\data\\workspace\\grails\\tea\\src\\main\\webapp\\mock\\hello.json"
        File file = new File(path)
        def jl = file.text
        def json = new JsonSlurper().parseText(jl)
        def hs = (json.rep.headers)
        println(hs.getClass())
        for(def h: hs){
            println h.key
            println h.value
        }
        println "${json.req.metho == null}"
    }
    def pc(def c){
        println(c())
    }
}
