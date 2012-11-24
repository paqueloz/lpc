/*
 *   This program manages the database and supports the work processes
 *   of the Luethi-Peterson Camps association (http://luethipetersoncamps.org/).
 *
 *   Copyright (C) 2012 Pierre-Antoine Queloz
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package jaf

import javax.management.relation.RelationService;

import org.springframework.dao.DataIntegrityViolationException

class PersonRelationController {
    
    def relationService

    static allowedMethods = [save: "POST", update: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [personRelationInstanceList: PersonRelation.list(params), personRelationInstanceTotal: PersonRelation.count()]
    }

    def create() {
        /* BEGIN MANUAL EDIT */
        [personRelationInstance: new PersonRelation(params), addrCount: (Person.findById(params.person?.id)?.address.size()) ?: 0]
        /* END MANUAL EDIT */
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
        /* BEGIN MANUAL EDIT */
        // create other person on the fly if there is no other_id and a non-empty other
        if (!params.other_id && params.other?.trim().length() > 0) {
            String other = params.other.trim()
            int split = other.lastIndexOf(' ')
            otherPerson = new Person(firstName: split<0 ? "" : other.substring(0,split).trim(),
                 lastName: other.substring(split+1),
                 status: PersonStatus.Friend).save(flush:true)
        }
        
        def pri = new PersonRelation( person : aPerson,
                            relationship    : params.relationship,
                            other           : otherPerson,
                            comment         : params.comment )
        
        if (pri.validate() && pri.relationship == Relationship.livesWith) {
            // forbidden to connect aPerson if it already has an outgoing livesWith
            if (PersonRelation.countByRelationshipAndPerson(Relationship.livesWith, aPerson)) {
                pri.errors.reject( 'personrelation.liveswith.already', [] as Object[], "The relationship is already defined for this person")
                // The following helps with field highlighting in your view
                pri.errors.rejectValue( 'relationship', '' )
                render(view: "create", model: [personRelationInstance: pri]) // FIXME breaks the autocomplete
                return
            }
            // forbidden to connect aPerson if it already has an incoming livesWith
            // unless otherPerson has no outgoing livesWith and no incoming livesWith and no address
            // then the relation is inverted
            if (PersonRelation.countByRelationshipAndOther(Relationship.livesWith, aPerson)) {
                if (PersonRelation.countByRelationshipAndOther(Relationship.livesWith, otherPerson)
                    || PersonRelation.countByRelationshipAndPerson(Relationship.livesWith, otherPerson)
                    || otherPerson.address?.size()) 
                {
                    pri.errors.reject( 'personrelation.liveswith.invalid', [] as Object[], "Adding this relationship would cause an invalid network")
                    // The following helps with field highlighting in your view
                    pri.errors.rejectValue( 'relationship', '' )
                    render(view: "create", model: [personRelationInstance: pri]) // FIXME breaks the autocomplete
                    return
                } else {
                    pri.person = otherPerson    // FIXME add a message about the inverted relation
                                                // TODO, add a simple way to find incoming "liveswith"
                                                // for instance when the address is displayed with
                                                // usage count
                    pri.other = aPerson
                }
            }
            // remove existing addresses
            relationService.deleteAddresses(pri.person)
        }
 
        /* END MANUAL EDIT */

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
