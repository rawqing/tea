package mock

import com.google.gson.JsonObject
import grails.converters.JSON
import grails.converters.XML
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.xml.XmlUtil
import utils.FileRW

class MockServiceController {
    def suffix = ".json"

    def index(){
//        render "Welcome mok server !"
        work()
    }
    def work(){
        String dirPath = request.getSession().getServletContext().getRealPath("/")
        String uri = request.getRequestURI()
        String method = request.getMethod()
        String contextPath = request.getContextPath()
        if(contextPath){
            uri = uri.substring(uri?.indexOf(contextPath) >= 0 ? contextPath.length() : 0)
        }
        File file = new File(dirPath+ uri + suffix)
        // 检查mock json文件是否设置
        if(! file.exists()){
            println "file : ${file.getAbsolutePath()} not exists"
            notFound()
            return
        }
        def jsonLoad = file.text
        def json = new JsonSlurper().parseText(jsonLoad)
        // 解析出 request
        def req = json.request
        def mMethod = req?.method
        if(mMethod != null && ! method.equalsIgnoreCase(mMethod)){
            println "did not matched method .\n request method : ${method} , preinstall method : ${mMethod}"
            notFound()
            return
        }
        def rep = json.response
        if(! rep) {
            render("bad response")
            return
        }
        // set headers
        def headers = rep.headers
        for(def h : headers){
            header(h.key ,h.value)
        }
        // set Content-Type
        def contentType = rep.contentType
        if(contentType) response.setContentType(contentType)
        // set status code
        def scode = rep.status
        if(scode && scode.isInteger())   response.setStatus(scode as Integer)
        // render text
        def text = rep.text
        if(text instanceof String){
            render(text: text)
        }else{
            render(text: text as JSON)
        }
    }
    def aa() {
        String dirPath = request.getSession().getServletContext().getRealPath("/")
        String uri = request.getRequestURI()
        String contextPath = request.getContextPath()
        if(contextPath){
            uri = uri.substring(uri?.indexOf(contextPath) >= 0 ? contextPath.length() : 0)
        }
        File file = new File(dirPath+ uri + suffix)
        def json = null
        def jsonLoad = "{}"
        if(file.exists()){
            jsonLoad = file.text
            json = new JsonSlurper().parseText(jsonLoad)
            println("text : ${json.rep.text}")

        }else{
            println "file : ${file.getAbsolutePath()} not exists"
        }

        def xmlText = '<?xml version="1.0"?>' +
                '<soap:Envelope xmlns:soap="http://www.w3.org/2001/12/soap-envelope" ' +
                'soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding"> ' +
                '</soap:Envelope>'
        String xml2Text = '''<xml>
                    <people>
                       <person firstName="John" lastName="Doe"/>
                       <person firstName="Jane" lastName="Williams"/>
                    </people>
                    </xml>
                    '''
        def xml = new XmlParser().parseText(xml2Text)
//        render "hello mock ~!<br>" +
//                "method : ${request.getMethod()}<br> uri : ${uri} <br>" +
//                "url : ${request.getRequestURL()} <br> root dir : ${dirPath} <br>" +
//                "json : ${JsonOutput.prettyPrint(jsonLoad)} <br>" +
//                "file path : ${file.getAbsolutePath()} <br> Context Path : ${request.getContextPath()} <br>" +
//                "Servlet Context : ${request.getServletContext().getRealPath("/")} <br>" +
//                "Servlet Path : ${request.getServletPath()} <br>" +
//                ""
        def json2 = JsonOutput.toJson([req:[method:"GET",path:"/xxx/xx"] ,rep:[headers:[contentType:"text/xml",X_xx:"12333"] ,text: xml2Text]])
        println "json2 class : ${json2.getClass()}"
        response.setContentType("text/json; charset=utf-8")
//        render (json as JSON)
        header("userid",11223 )
        header("uu","uu111")
        render "text":json.rep.text
//        render(
//                "status":203,
//                "text":jsonLoad
//        )
//        render json as JSON
//        render(contentType: "text/xml") {
//            people {
//                person(firstName:'John', lastName:'Doe')
//                person(firstName:'Jane', lastName:'Williams')
//            }
//        }
    }

    def _admin(){
        return "hello"
//        render(123)
//        forward(view :"_admin")
    }

    def notFound(){
        render(status:404 ,text:"page not found !")
    }
}
