package tea

class Versioning {
    int id
    String platform
    String edition
    String t_version
    Date releaseTime

    String author

    Date dateCreated

    static constraints = {

    }

    static mapping = {
        id generator:'identity'
    }

    static belongsTo = [plan:T_plan ,product:Product ,mAuthor:User]



}
