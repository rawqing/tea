package tea

class T_case {
    String id
    String c_name=""
    String precondition
    Integer prio
    String descr
    String keyword
    String judge
    String c_author

    Date dateCreated
    Date lastUpdated

    static constraints = {
        c_name(blank: false)
        c_author(blank: false)

        precondition(blank: true,nullable: true)
        judge(blank: true,nullable: true)
        keyword(blank: true,nullable: true)
        descr(blank: true,nullable: true)
    }
    static hasMany = [steps:T_step]
    static belongsTo = [t_module:T_module]
    static mapping = {
        id generator: 'uuid'
    }

    String toString(){
        return "${c_name}"
    }
}
