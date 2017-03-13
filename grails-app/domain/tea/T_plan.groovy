package tea

class T_plan {
    String id;
    String p_name;
    Date predictStart;
    Date predictStop;
    Date realityStart;
    Date realityStop;
    //新建时状态为new , 其他时刻记录该plan的执行状态
    String p_status;
    String hasSuites;
    String descr;
    String p_author

    Date dateCreated
    Date lastUpdated

    static constraints = {
        p_name(blank: false)
        predictStart(nullable: true)
        predictStop(nullable: true)
        realityStart(nullable: true)
        realityStop(nullable: true)
        hasSuites(nullable: true)
        descr(nullable: true)

    }
    static belongsTo = [product:Product]

    static mapping = {
        id generator: 'uuid'
    }

    String toString(){
        return "${p_name}"
    }
}
