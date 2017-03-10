package tea

import constant.STATUS
import grails.transaction.Transactional

@Transactional
class ProductService {

    def getProductMap(){
        def product = Product.findAll()
        def pMap = [:]
        for(def tp : product){
            pMap += [(tp.getP_name()):tp]
        }
        return pMap
    }

    /**
     * 去除禁用的产品
     * @return
     */
    def getEnabledProductMap(){
        def product = Product.findAll()
        def pMap = [:]
        for(def tp : product){
            if(tp.getP_status() == STATUS.disabled.name()){
                continue
            }
            pMap += [(tp.getP_name()):tp]
        }
        return pMap
    }

}
