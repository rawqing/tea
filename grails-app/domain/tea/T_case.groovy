package tea

class T_case {
    Integer c_id
    String c_name=""
    T_module module
//    T_step step

    static constraints = {
    }
    static hasMany = [step:T_step]
    static belongsTo = [module:T_module]

    String toString(){
        return "${c_name}"
    }
}
