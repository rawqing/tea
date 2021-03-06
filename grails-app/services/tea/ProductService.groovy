package tea

import constant.STATUS
import grails.transaction.Transactional

@Transactional
class ProductService {


    def getProductByName(String name){
        return Product.findByP_name(name)
    }
    def getEnabledProductByName(String name){
        println(name)
        def query = Product.where {
            p_name == name && p_status != STATUS.disabled.name()
        }
//        return Product.findByP_nameAndP_statusNotEqual(name ,STATUS.disabled.name())
        return query.find()
    }
    def getEnabledProductNames(){
        def query = Product.where {
            p_status != STATUS.disabled.name()
        }
//        def pn = Product.findByP_statusNotEqual(STATUS.disabled.name())*.getP_name()
        def pn = query.findAll()*.getP_name()
        println("pn >>>>> "+pn)
        return pn
    }


}
