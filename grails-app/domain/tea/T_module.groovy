package tea

class T_module {
    Integer m_id
    String m_name=""
    static constraints = {
    }
    static hasMany = [t_case:T_case]

    String toString(){
        return "${m_name}"
    }
}
