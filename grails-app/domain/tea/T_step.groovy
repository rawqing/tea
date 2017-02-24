package tea

class T_step {
    String c_step
    String c_expect
    String c_result
    String c_actual

    static constraints = {
    }

    static belongsTo = [t_case:T_case]
}
