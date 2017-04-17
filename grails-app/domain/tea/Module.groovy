package tea

class Module {
    int id
    String m_name=""
    String descr

    Date dateCreated
    Date lastUpdated

    static constraints = {
        m_name(blank: false)
    }
    static hasMany = [submodule:Module ,t_case:T_case]
    static mappedBy = [ submodule: "submodule" ]
    static belongsTo = [product:Product ,mAuthor:User]

    static mapping = {
        id generator:'identity'
        descr type: "text"
    }

    String toString(){
        return "${m_name}"
    }
}
