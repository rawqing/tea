package tea

class T_suite {
    String id
    String s_name
    String caseSuites
    String descr

    String s_author

    Date dateCreated
    Date lastUpdated
    static constraints = {
        s_name(blank: false)
        descr(nullable: true)
    }

    static belongsTo = [product:Product]

    static mapping = {
        id generator: 'uuid'
    }

    String toString(){
        return "${s_name}"
    }
}
