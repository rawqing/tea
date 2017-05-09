package tea

import constant.OUTER
import groovy.json.JsonSlurper

class T_case {
    int id
    String c_name=""
    String precondition
    Integer prio
    String descr
    String keyword
    String judge
    String outerId

    Date dateCreated
    Date lastUpdated

    static constraints = {
        c_name(blank: false)

        precondition(blank: true,nullable: true)
        prio(blank: true,nullable: true)
        judge(blank: true,nullable: true)
        keyword(blank: true,nullable: true)
        descr(blank: true,nullable: true)
        outerId(blank: true,nullable: true)
    }
    static hasMany = [steps:T_step]
    static belongsTo = [product:Product ,mAuthor:User , module:Module]
    static mapping = {
        id generator:'identity'
        descr type: "text"

    }

    def getModuleMapping(){
        return this.module.getPathMapping()
    }
    def getOuterId(OUTER outer){
        if(this.outerId)    return
        def oidMap = new JsonSlurper().parseText(this.outerId) as Map
        return oidMap["${outer}"]
    }
    String toString(){
        return "${c_name}"
    }
}
