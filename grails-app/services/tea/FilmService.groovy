package tea

import grails.transaction.Transactional

@Transactional
class FilmService {

    def saveFilm(Film film){
        if(film == null){
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        if(film.hasErrors()){
            transactionStatus.setRollbackOnly()
            respond(film.errors)
            return
        }
        film.save(flush:true)
    }
    def createUri(controller , id){
        "/${controller}/show/${id}"
    }
    def createFilm(table , t_id ,controller){
        new Film(table_name: table,
                target_id: t_id,
                uri: createUri(controller,t_id))
    }
}
