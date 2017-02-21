package tea

class Entity {
    Integer entity_id

    static constraints = {
        entity_id(min: 1)
    }
}
