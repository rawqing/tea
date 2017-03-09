package tea

class Product {
    String id
    Integer f_id
    String p_name
    String descr
    /* 表明该产品的状态 , 默认mull==启用, 禁用状态将不展示*/
    String p_status

    Date dateCreated
    Date lastUpdated

    static constraints = {
    }

//    static hasMany = []
    static mapping = {
        id generator: 'uuid'
    }
}
