package jaf

import org.springframework.dao.DataIntegrityViolationException

class PersonRelationController {

    static allowedMethods = [save: "POST", update: "POST"]

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

    /**
     * Valid input:
     * - person.id designates a person
     * - other_id designates another person
     * - relationship is not null
     */
    def save = {
        Person aPerson;
        if (params.person?.id) {
            aPerson = Person.findById(params.person?.id)
        }
        Person otherPerson;
        if (params.other_id) {
            if (Long.valueOf(params.other_id) != Long.valueOf(params.person?.id)) {
                otherPerson = Person.findById(params.other_id)
            }
        }
        def pri = new PersonRelation( person : aPerson,
                            relationship    : params.relationship,
                            other           : otherPerson,
                            comment         : params.comment )
        if (!pri.save(flush: true)) {
            render(view: "create", model: [personRelationInstance: pri])
            return
        }

        // create the opposite
        if (pri.relationship != Relationship.livesWith) {
            def opposite = new PersonRelation( person : pri.other,
                    relationship    : PersonRelation.opposite(pri.relationship,pri.other),
                    other           : pri.person,
                    comment         : pri.comment )
            if (!opposite.save(flush: true)) {
                render(view: "create", model: [personRelationInstance: pri])
                return
            }
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

    /**
     * Expected input:
     * - a valid PersonRelation id in params.id
     * - params.other_id (takes precedence if user has modified the value) or params.other_id_old, different from person.id
     */
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
        if (params.other_id && Long.valueOf(params.other_id) != Long.valueOf(params.person?.id)) {
            personRelationInstance.other = Person.findById(params.other_id)
        } else {
            personRelationInstance.other = null
        }
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
 * Doesn't work very well; and potentially triggers a Grails bug
 */
//class PersonRelationSaveCommand {
//
//    Person          person
//    Relationship    relationship
//    String          other_id
//    Person          other
//    String          comment
//
//    static constraints = {
//        person(nullable : false)
//        relationship(nullable : false)
//        comment(nullable : true)
//        other_id(nullable : false,
//            validator : { other_id, prsc ->
//                return Long.valueOf(other_id) != prsc.person?.id
//            }   
//        )
//        other(nullable : true)
//    }
//
//}
