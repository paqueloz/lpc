package jaf

import org.springframework.dao.DataIntegrityViolationException

class PersonRelationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [personRelationInstanceList: PersonRelation.list(params), personRelationInstanceTotal: PersonRelation.count()]
    }

    def create() {
        [personRelationInstance: new PersonRelation(params)]
    }

    // FIXME error messages when the other field is empty or invalid (same as person)
    def save = { PersonRelationSaveCommand prsc ->
        if (prsc.hasErrors()) {
            render(view: "create", model: [personRelationInstance: prsc])
            return
        }
        def pri = new PersonRelation( person : prsc.person,
                            relationship    : prsc.relationship,
                            other           : Person.findById(prsc.other_id),
                            comment         : prsc.comment )
        if (!pri.save(flush: true)) {
            render(view: "create", model: [personRelationInstance: pri])
            return
        }

        // create the opposite
        def opposite = new PersonRelation( person : pri.other,
                relationship    : PersonRelation.opposite(pri.relationship,pri.other),
                other           : pri.person,
                comment         : pri.comment )
        if (!opposite.save(flush: true)) {
            render(view: "create", model: [personRelationInstance: pri])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'personRelation.label', default: 'PersonRelation'), pri.id])
        redirect(action: "show", id: pri.id)
    }

    def show() {
        def personRelationInstance = PersonRelation.get(params.id)
        if (!personRelationInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'personRelation.label', default: 'PersonRelation'), params.id])
            redirect(action: "list")
            return
        }

        [personRelationInstance: personRelationInstance]
    }

    def edit() {
        def personRelationInstance = PersonRelation.get(params.id)
        if (!personRelationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'personRelation.label', default: 'PersonRelation'), params.id])
            redirect(action: "list")
            return
        }

        [personRelationInstance: personRelationInstance]
    }

    def update() {
//      load person in the same Hibernate request but not necessary
//      def personRelationInstance = PersonRelation.findById(params.id,[fetch:[person:'eager']])
        def personRelationInstance = PersonRelation.get(params.id)
        if (!personRelationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'personRelation.label', default: 'PersonRelation'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (personRelationInstance.version > version) {
                personRelationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'personRelation.label', default: 'PersonRelation')] as Object[],
                          "Another user has updated this PersonRelation while you were editing")
                render(view: "edit", model: [personRelationInstance: personRelationInstance])
                return
            }
        }
        
        // the search field is causing trouble to the next line
        // personRelationInstance.properties = params

        personRelationInstance.relationship = params.relationship
        personRelationInstance.comment = params.comment
        if (!params.other_id) {
            params.other_id = params.other_id_old
        }
        if (Long.valueOf(params.other_id).equals(personRelationInstance.person.id)) {
            // FIXME the message is about the null "other"
            personRelationInstance.errors.rejectValue("other", "default.optimistic.locking.failure",
                [message(code: 'personRelation.label', default: 'PersonRelation')] as Object[],
                "Relation must be with a different person")
            personRelationInstance.other = null
            render(view: "edit", model: [personRelationInstance: personRelationInstance])
            return
        }
        personRelationInstance.other = Person.findById(params.other_id)
        if (!personRelationInstance.save(flush: true)) {
            render(view: "edit", model: [personRelationInstance: personRelationInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'personRelation.label', default: 'PersonRelation'), personRelationInstance.id])
        redirect(action: "show", id: personRelationInstance.id)
    }

    def delete() {
        def personRelationInstance = PersonRelation.get(params.id)
        if (!personRelationInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'personRelation.label', default: 'PersonRelation'), params.id])
            redirect(action: "list")
            return
        }

        try {
            personRelationInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'personRelation.label', default: 'PersonRelation'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'personRelation.label', default: 'PersonRelation'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}

/**
 * Command object to perform input validation (other_id differs from person.id)
 */
class PersonRelationSaveCommand {

    Person          person
    Relationship    relationship
    int             other_id
    Person          other
    String          comment

    static constraints = {
        person(nullable : false)
        relationship(nullable : false)
        comment(nullable : true)
        other_id(nullable : false,
            validator : { other_id, prsc ->
                return other_id != prsc.person?.id
            }   
        )
        other(nullable : true)
    }

}
