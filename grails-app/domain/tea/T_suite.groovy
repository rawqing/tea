package tea

class T_suite {
    int id
    String s_name
    String caseSuites
    String descr

    Date dateCreated
    Date lastUpdated
    static constraints = {
        s_name(blank: false)
        descr(nullable: true)
    }

    static belongsTo = [product:Product ,mAuthor:User]

    static mapping = {
        id generator:'identity'
        descr type: "text"
        caseSuites type: "text"
    }

    String toString(){
        return "${s_name}"
    }
}
