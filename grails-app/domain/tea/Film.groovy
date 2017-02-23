package tea

class Film {
    Integer id
    String table_name
    String target_id

    static constraints = {
        id generator:'sequence'
        table_name blank: false
        target_id blank: false
    }
}
