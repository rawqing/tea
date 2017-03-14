package tea

class User {
    int id
    String u_name
    String password
    String real_name
    String team
    Boolean valid
    String tel
    String u_mail
    Date dateCreated
    Date lastUpdated


    static constraints = {
        u_name(blank: false)
        password(blank: false,password:true)
        team(inList: ["TE","ADMIN","RD","PM"])
    }
    static mapping = {
        id generator:'identity'
    }
}
