package tea

class T_step {
    int id
    String s_step
    String s_expect

    Date dateCreated
    Date lastUpdated

    static constraints = {
    }

    static belongsTo = [t_case:T_case ,mAuthor:User]
    static mapping = {
        id generator:'identity'
    }
}
