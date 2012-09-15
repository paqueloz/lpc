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

        personRelationInstance.properties = params

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

class PersonRelationSaveCommand {

    Person          person
    Relationship    relationship
    int             other_id
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
    }

}
