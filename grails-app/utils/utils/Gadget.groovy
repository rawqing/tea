package utils

/**
 * Created by king on 17/5/4.
 */
class Gadget {

    /**
     * 将字符串按分隔符从末尾分割成两个
     * @param original
     * @param separate
     * @return 在不存在分割符的情况下返回原始字符串 , 若分隔符为最后一个字符则做空字符处理
     * 以保证size == 1 的情况下原始字符串不会存在分隔符
     */
    static subEndStringWith(String original, String separate){
        def ls = []
        //去除末尾的分隔符
        if(original.endsWith(separate))  original = original.substring(0,original.length()-1)
        int index = original.lastIndexOf(separate)
        if(index < 0)   return [original]
        ls += original.substring(0 ,index)
        ls += original.substring(index + 1)
        return ls
    }

    static subStartStringWith(String original, String separate){
        def ls = []
        int index = original.indexOf(separate)
        if(index == 0){
            original = original.substring(1)
            index = original.indexOf(separate)
        }
        if(index < 0)   return [original]
        ls += original.substring(0 ,index)
        ls += original.substring(index + 1)
        return ls
    }

    static subStringCrowdWith(String original, String separate){
        def ls = []
        int index = original.indexOf(separate)
        if(index == 0){
            original = original.substring(1)
            index = original.indexOf(separate)
        }
        if(index < 0)   return [original]
        //这个时候才给末尾加上分隔符 , 方便拆分
        if(!original.endsWith(separate)) original += separate
        while(index > 0){
            ls += original.substring(0,index)
            index = original.indexOf(separate ,index+1)
            println("index : "+index)
        }
        return ls
    }

    static separateAdd(String original, def addStr , String separate){
        if(original.endsWith(separate)){
            return original + addStr
        }
        return original + separate + addStr
    }
}
