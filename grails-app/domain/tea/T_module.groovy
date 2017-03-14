package tea

class T_module {
    int id
    String m_name=""
    String descr
    String m_author

    Date dateCreated
    Date lastUpdated

    static constraints = {
    }
    static hasMany = [t_case:T_case]
    static belongsTo = [product:Product]
    static mapping = {
        id generator:'identity'
    }

    String toString(){
        return "${m_name}"
    }
}
