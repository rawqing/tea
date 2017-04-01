package tea

class Product {
    int id
    String p_name
    String descr
    /* 表明该产品的状态 , 默认mull==启用, 禁用状态将不展示*/
    String p_status

    Date dateCreated
    Date lastUpdated

    static constraints = {
    }

    static belongsTo = [mAuthor:User]

    static mapping = {
        id generator:'identity'
        descr type: "text"
    }

    String toString(){
        return "${p_name}"
    }
}
