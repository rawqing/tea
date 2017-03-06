package tea

class Product {
    String id
    Integer f_id
    String p_name
    String descr
    Date dateCreated
    Date lastUpdated

    static constraints = {
    }

    static mapping = {
        id generator: 'uuid'
    }
}
