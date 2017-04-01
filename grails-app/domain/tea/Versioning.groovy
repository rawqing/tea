package tea

class Versioning {
    int id
    String platform
    String edition
    String t_version
    String descr

    Date releaseTime
    Date dateCreated

    static constraints = {
        plan(nullable: true)
    }

    static mapping = {
        id generator:'identity'
        descr type: "text"
    }

    static belongsTo = [plan:T_plan ,product:Product ,mAuthor:User]



}
