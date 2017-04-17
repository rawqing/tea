package tea

class ModuleController {

//    def index() { }
    static scaffold = Module

    def child(){
        Module m1 = new Module()
        m1.setM_name("mm1")

        Set<Module> ms = new ArrayList<>()
        ms = [new Module(m_name: "mm2"),new Module(m_name: "mm3")]
        m1.setSubmodule(ms)

        m1.save(flush:true)
        render("hello2")
    }
    def te(){
        Module m = Module.get(2)
        Set<Module> ms = new ArrayList<>()
        ms = [new Module(m_name: "mm4")]
        m.setSubmodule(ms)

        m.save(flush:true)
        render("ok")
    }

    def pp(){
        println "hello"
        Module m = Module.get(4)
        def children = m.getSubmodule()
        def cs = children ? children + children*.getSubmodule().flatten() : []
        println cs
        render m.m_name
    }
}

