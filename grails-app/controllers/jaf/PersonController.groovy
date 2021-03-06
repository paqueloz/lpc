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

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class PersonController {

    static allowedMethods = [save: "POST", update: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [personInstanceList: Person.list(params), personInstanceTotal: Person.count()]
    }

    def create() {
        /* BEGIN MANUAL EDIT */
        Person personInstance = new Person(params)
        personInstance.status = PersonStatus.CAmper 
        [personInstance: personInstance]
        /* END MANUAL EDIT */
    }

    def save() {
        def personInstance = new Person(params)
        if (!personInstance.save(flush: true)) {
            render(view: "create", model: [personInstance: personInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'person.label', default: 'Person'), personInstance.id])
        redirect(action: "show", id: personInstance.id)
    }

    def show() {
        def id = params.id
        if (params.person_id) { // auto_complete
            id = params.person_id
        }
        def personInstance = Person.get(id)
        if (!personInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])
            redirect(action: "list")
            return
        }
        // use relationship to find address
        def addr = personInstance.address;
        int shared = 0  // people sharing this address
        if (!addr?.size()) {
            for (relation in personInstance.relationships) {
                if (relation.relationship != Relationship.livesWith) {
                    continue
                }
                addr = relation.other.address
                shared = 1 + PersonRelation.countByRelationshipAndOther(Relationship.livesWith, relation.other)
                break
            }
        } else {
            shared = 1 + PersonRelation.countByRelationshipAndOther(Relationship.livesWith, personInstance)
        }
        // view should use address and not the value inside personInstance
        [personInstance: personInstance, address: addr, sharedAddress: shared>1, sharedCount: shared]
    }
    
    def edit() {
        def personInstance = Person.get(params.id)
        if (!personInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])
            redirect(action: "list")
            return
        }

        [personInstance: personInstance]
    }

    def update() {
        def personInstance = Person.get(params.id)
        if (!personInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (personInstance.version > version) {
                personInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'person.label', default: 'Person')] as Object[],
                          "Another user has updated this Person while you were editing")
                render(view: "edit", model: [personInstance: personInstance])
                return
            }
        }

        personInstance.properties = params

        if (!personInstance.save(flush: true)) {
            render(view: "edit", model: [personInstance: personInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'person.label', default: 'Person'), personInstance.id])
        redirect(action: "show", id: personInstance.id)
    }

    def delete() {
        def personInstance = Person.get(params.id)
        if (!personInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])
            redirect(action: "list")
            return
        }

        try {
            personInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'person.label', default: 'Person'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'person.label', default: 'Person'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    /**
     * Return a list of persons for the auto-complete search box
     */
    def autoCompleteJSON = {
        if (!params.query) {
            return;
        }
        def list = Person.search("${params.query}*", [ reload : true ])     // default limit 10 results
                                                                            // reload to concatenate birthDay and address
        def jsonList = list.results.collect { Person p -> [ id : p.id, name : p.toStringForSearch() ] }
        if (list.total > 10) {
            jsonList << [ id : "", name : "${list.total-10} not displayed" ]
        }
        def jsonResult = [ result : jsonList ]
        render jsonResult as JSON
    }
}
