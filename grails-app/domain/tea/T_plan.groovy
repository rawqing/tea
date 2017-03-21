package tea

class T_plan {
    int id;
    String p_name;
    Date predictStart;
    Date predictStop;
    Date realityStart;
    Date realityStop;
    //新建时状态为new , 其他时刻记录该plan的执行状态
    String p_status;
//    String hasSuites; //计划不直接关联用例 , 而是分摊到每个版本
    String descr;

    Date dateCreated
    Date lastUpdated

    static constraints = {
        p_name(blank: false)
        predictStart(nullable: true)
        predictStop(nullable: true)
        realityStart(nullable: true)
        realityStop(nullable: true)
        descr(nullable: true)

    }
    static belongsTo = [product:Product ,mAuthor:User]

    static mapping = {
        id generator:'identity'
    }

    String toString(){
        return "${p_name}"
    }
}
