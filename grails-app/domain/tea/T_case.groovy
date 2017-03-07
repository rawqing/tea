package tea

class T_case {
    String id
    String module_id
    String c_name=""
    String precondition
    Integer prio
    String descr
    String keyword
    String judge
    String c_author
//    List<T_step> steps

    Date dateCreated
    Date lastUpdated

    static constraints = {
        c_name(blank: false)
        precondition(blank: false)
        c_author(blank: false)

        judge(blank: true,nullable: true)
        keyword(blank: true,nullable: true)
        descr(blank: true,nullable: true)
    }
//    static hasOne = [film:Film]
    static hasMany = [steps:T_step]
    static mapping = {
        id generator: 'uuid'
    }

    String toString(){
        return "${c_name}"
    }
}
