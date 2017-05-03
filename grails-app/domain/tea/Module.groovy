package tea

class Module {
    int id
    String m_name
    String path
    String pathMapping
    String descr

    Date dateCreated
    Date lastUpdated

    static constraints = {
        m_name(blank: false)
        path(nullable: true)
        pathMapping(nullable: true)
        descr(nullable: true)
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
