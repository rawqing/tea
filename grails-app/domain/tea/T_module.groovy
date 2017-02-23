package tea

class T_module {
    String id
    String m_name=""
    static constraints = {
    }
    static hasMany = [t_case:T_case]
    static mapping = {
        id generator: 'uuid'
    }

    String toString(){
        return "${m_name}"
    }
}
