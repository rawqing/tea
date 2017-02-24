package tea

class User {
    String id
    String u_name
    String password
    String real_name
    String team
    Boolean valid
    String tel
    String u_mail


    static constraints = {
        u_name(blank: false)
        password(blank: false,password:true)
        team(inList: ["TE","ADMIN","RD","PM"])
    }
    static mapping = {
        id generator: 'uuid'
    }
}
