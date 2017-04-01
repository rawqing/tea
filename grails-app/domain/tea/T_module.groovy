package tea

class T_module {
    int id
    String m_name=""
    String descr

    Date dateCreated
    Date lastUpdated

    static constraints = {
    }
    static hasMany = [t_case:T_case]
    static belongsTo = [product:Product ,mAuthor:User]
    static mapping = {
        id generator:'identity'
        descr type: "text"
    }

    String toString(){
        return "${m_name}"
    }
}
