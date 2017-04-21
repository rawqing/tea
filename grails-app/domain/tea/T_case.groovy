package tea

class T_case {
    int id
    String c_name=""
    String precondition
    Integer prio
    String descr
    String keyword
    String judge

    Date dateCreated
    Date lastUpdated

    static constraints = {
        c_name(blank: false)

        precondition(blank: true,nullable: true)
        judge(blank: true,nullable: true)
        keyword(blank: true,nullable: true)
        descr(blank: true,nullable: true)
    }
    static hasMany = [steps:T_step]
    static belongsTo = [product:Product ,mAuthor:User]
    static mapping = {
        id generator:'identity'
        descr type: "text"

    }

    String toString(){
        return "${c_name}"
    }
}
