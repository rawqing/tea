package tea

class T_case {
    String id
    String c_name=""
    String precondition
    Integer prio
    String descr
    String keyword
    String judge
    String _author
    Film film
    T_module module
    List<T_step> steps

    Date dateCreated
    Date lastUpdated

    static constraints = {
        c_name(blank: false)
        precondition(blank: false)
        _author(blank: false)

        judge(blank: true)
        keyword(blank: true)
        descr(blank: true)
    }
    static hasOne = [film:Film]
    static hasMany = [steps:T_step]
    static belongsTo = [module:T_module]
    static mapping = {
        id generator: 'uuid'
    }

    String toString(){
        return "${c_name}"
    }
}
