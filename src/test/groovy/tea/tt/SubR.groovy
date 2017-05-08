package tea.tt

/**
 * Created by king on 17/5/8.
 */
class SubR {

    def a = 'coffee'



    def run(){
        def c = {
            def b = 'tea'
            a + ' and ' + b //a refers to the variable a outside the closure,
            //and is remembered by the closure
        }
        pc(c)
    }
    def pc(def c){
        println(c())
    }
}
