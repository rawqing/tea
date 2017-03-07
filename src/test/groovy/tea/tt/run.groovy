package tea.tt

/**
 * Created by king on 17/3/7.
 */
class run {
    static void main(String[] args) {
        def caseTitle= ["module","name","precondition","steps","expectation","prio","descr","keyword"]
        def data = [1,2,3,4,5,6,7,8]
        def cMap = [:]
        if(data.size() != caseTitle.size()){
            return
        }
        for (int i = 0; i < data.size(); i++) {
            cMap+=[(caseTitle[i]):data[i]]
        }
        println(cMap)
        println(cMap["module"])
        data += 3
        println(data)

    }
    def t={
        assert 1==2
    }
}
