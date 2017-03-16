package tea

class T_case {
    int id
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
    static belongsTo = [t_module:T_module ,product:Product]
    static mapping = {
        id generator:'identity'
    }

    String toString(){
        return "${c_name}"
    }
}
