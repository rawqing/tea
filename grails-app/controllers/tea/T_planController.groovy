package tea

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class T_planController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond T_plan.list(params), model:[t_planCount: T_plan.count()]
    }

    def show(T_plan t_plan) {
        respond t_plan
    }

    def create() {
        respond new T_plan(params)
    }

    @Transactional
    def save(T_plan t_plan) {
        if (t_plan == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (t_plan.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond t_plan.errors, view:'create'
            return
        }

        t_plan.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 't_plan.label', default: 'T_plan'), t_plan.id])
                redirect t_plan
            }
            '*' { respond t_plan, [status: CREATED] }
        }
    }

    def edit(T_plan t_plan) {
        respond t_plan
    }

    @Transactional
    def update(T_plan t_plan) {
        if (t_plan == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (t_plan.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond t_plan.errors, view:'edit'
            return
        }

        t_plan.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 't_plan.label', default: 'T_plan'), t_plan.id])
                redirect t_plan
            }
            '*'{ respond t_plan, [status: OK] }
        }
    }

    @Transactional
    def delete(T_plan t_plan) {

        if (t_plan == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        t_plan.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 't_plan.label', default: 'T_plan'), t_plan.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 't_plan.label', default: 'T_plan'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
