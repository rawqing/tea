package httpx

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder

import static groovyx.net.http.Method.GET
import static groovyx.net.http.Method.POST

/**
 * Created by king on 17/5/8.
 */
class OuterHttp {

    HTTPBuilder http
    String bo = '''
{
Name:"Demo Test Case3",
Project:{ID:3514},

TestSteps:{
Items:[
{Description:"Step #1 Action",Result:"Step #1 Expected result"},
{Description:"Step #2 Action",Result:"Step #2 Expected result"},
{Description:"Step #3 Action",Result:"Step #3 Expected result"} 
]
}

}
'''

    OuterHttp(String url){
        this.http = new HTTPBuilder(url)
    }

    def doPost(String bodyStr ,def callBack){
        http.request(POST){ req ->
            headers.'Authorization' = 'Basic emhhby5kZW5nQGp1Y2FpZGFvLmNvbTphQTEyMzMyMQ=='
            headers.'Content-Type' = 'application/json;charset=utf-8'
            body = bodyStr
            requestContentType = ContentType.JSON
            response.success = { resp ,reader->
                println "Tweet response status: ${resp.statusLine}"
                callBack(resp ,reader)
                println reader.toString()

                println reader.getClass()
                println reader.attributes()
                println reader.attributes()["Id"]

            }
        }
    }
    def doGet(){
        http.request(GET ,ContentType.XML){ req ->
            headers.'User-Agent' = 'Mozilla/5.0'
            headers.'Authorization' = 'Basic emhhby5kZW5nQGp1Y2FpZGFvLmNvbTphQTEyMzMyMQ=='
            headers.'Content-Type' = 'application/json;charset=utf-8'
            //请求成功
            response.success = { resp, reader ->
                assert resp.statusLine.statusCode == 200
                println "My response handler got response: ${resp.statusLine}"
                println "Response length: ${resp.headers.'Content-Length'}"
//                def t = new XmlSlurper().parseText(reader)
                println resp.entity // print response stream
            }
//            groovyx.net.http.HttpResponseDecorator
            //404
            response.'404' = { resp ->
                println 'Not found'
            }

            // 401
            http.handler.'401' = { resp ->
                println "Access denied"
            }

            //其他错误，不实现则采用缺省的：抛出异常。
            http.handler.failure = { resp ->
                println "Unexpected failure: ${resp.statusLine}"
            }
        }
    }
}
