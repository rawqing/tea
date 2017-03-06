package tea

class T_module {
    String id
    String m_name=""
    Product product
//    Film film
    String descr
    String _author
    Date dateCreated
    Date lastUpdated

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
