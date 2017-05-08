package tpondemand

import tea.T_case

/**
 * Created by king on 17/5/8.
 */
class CasesHandle {


    def createTpCase(T_case tCase ,int projectId){
        def tpc = [:]
        tpc["Name"] = tCase.getC_name()
        tpc["Project"] = [ID:projectId]

        def desc = createTpDesc(tCase)
        if(desc) tpc["Description"] = desc

        def tpts = [:]
        def items = new ArrayList()
        def tts = tCase.getSteps()
        def rtts = tts.sort{
            it.site
        }
        for(def t:rtts){
            items.add([description:t.getSite() + ". "+ t.getS_step() , result:t.getSite() + ". "+ t.getS_expect()])
        }
        if(items)   tpts["Items"] = items
        if(tpts)      tpc["TestSteps"] = tpts

        return tpc
    }
    def createTpDesc(T_case tCase){
        def nbsp = "        "

        String precondition = tCase.getPrecondition()?.replaceAll("\n" ,nbsp + "<br>")
        String module = tCase.getModuleMapping()
        String desc = tCase.getDescr()?.replaceAll("\n" ,nbsp + "<br>")

        def tpDesc = ""
        if(precondition)    tpDesc += "precondition :<br>${nbsp}${precondition}<br>"
        if(module)          tpDesc += "module :<br>${nbsp}${module}<br>"
        if(desc)            tpDesc += "description :<br>${nbsp}${desc}<br>"

        return tpDesc
    }

}
