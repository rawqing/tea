package tea

class Product {
    int id
    String p_name
    String descr
    /* 表明该产品的状态 , 默认mull==启用, 禁用状态将不展示*/
    String p_status
    String p_author

    Date dateCreated
    Date lastUpdated

    static constraints = {
    }

    static mapping = {
        id generator:'identity'
    }

    String toString(){
        return "${p_name}"
    }
}
