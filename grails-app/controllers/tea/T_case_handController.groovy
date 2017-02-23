package tea

class T_case_handController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {

        respond T_case.list(), model:[userCount: User.count()]
    }
}
