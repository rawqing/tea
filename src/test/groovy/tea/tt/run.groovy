package tea.tt

/**
 * Created by king on 17/3/7.
 */
import utils.Gadget

class run {


    static void main(String[] args) {
        Gadget g = new Gadget()
        def s = g.subEndStringWith("aa/bb/cd/q","q")
        println(s.size())
        println(s)
        println(s[1])
        println(s[1].size())
    }

}
