package tea

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
        "/mock/**"(controller: "mockService",action: "index")
        "/mock/_admin"(view: "_admin")
    }
}
