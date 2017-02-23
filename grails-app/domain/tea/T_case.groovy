package tea

class T_case {
    String id
    String c_name=""
    T_module module
//    T_step step

    static constraints = {
    }
    static hasMany = [step:T_step]
    static belongsTo = [module:T_module]
    static mapping = {
        id generator: 'uuid'
    }

    String toString(){
        return "${c_name}"
    }
}
