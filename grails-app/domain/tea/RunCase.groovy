package tea

class RunCase {
    int id
    String res
    String actual
    Date executionTime
    String bugId
    String bugUrl
    String bugStatus

    Date dateCreated

    static constraints = {
    }

    static mapping = {
        id generator:'identity'
    }

    static belongsTo = [plan:T_plan ,product:Product ,t_case:T_case ,
                        t_step:T_step ,versioning:Versioning , performer:User]



}
