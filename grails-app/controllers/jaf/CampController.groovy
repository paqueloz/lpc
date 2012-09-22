package jaf

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class CampController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [campInstanceList: Camp.list(params), campInstanceTotal: Camp.count()]
    }

    def create() {
        [campInstance: new Camp(params)]
    }

    def save() {
        def campInstance = new Camp(params)
        if (!campInstance.save(flush: true)) {
            render(view: "create", model: [campInstance: campInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'camp.label', default: 'Camp'), campInstance.id])
        redirect(action: "show", id: campInstance.id)
    }

    def show() {
        def id = params.id
        if (params.camp_id) { // auto_complete
            id = params.camp_id
        }
        def campInstance = Camp.get(id)
        if (!campInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'camp.label', default: 'Camp'), params.id])
            redirect(action: "list")
            return
        }

        [campInstance: campInstance]
    }

    def edit() {
        def campInstance = Camp.get(params.id)
        if (!campInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'camp.label', default: 'Camp'), params.id])
            redirect(action: "list")
            return
        }

        [campInstance: campInstance]
    }

    def update() {
        def campInstance = Camp.get(params.id)
        if (!campInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'camp.label', default: 'Camp'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (campInstance.version > version) {
                campInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'camp.label', default: 'Camp')] as Object[],
                          "Another user has updated this Camp while you were editing")
                render(view: "edit", model: [campInstance: campInstance])
                return
            }
        }

        campInstance.properties = params

        if (!campInstance.save(flush: true)) {
            render(view: "edit", model: [campInstance: campInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'camp.label', default: 'Camp'), campInstance.id])
        redirect(action: "show", id: campInstance.id)
    }

    def delete() {
        def campInstance = Camp.get(params.id)
        if (!campInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'camp.label', default: 'Camp'), params.id])
            redirect(action: "list")
            return
        }

        try {
            campInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'camp.label', default: 'Camp'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'camp.label', default: 'Camp'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
    
    /**
     * Return a list of camps for the auto-complete search box
     */
    def autoCompleteJSON = {
        if (!params.query) {
            return;
        }
        def list = Camp.search("${params.query}*", [ reload : true ])     // default limit 10 results
                                                                            // reload to concatenate birthDay and address
        def jsonList = list.results.collect { Camp c -> [ id : c.id, name : c.toStringForSearch() ] }
        if (list.total > 10) {
            jsonList << [ id : "", name : "${list.total-10} not displayed" ]
        }
        def jsonResult = [ result : jsonList ]
        render jsonResult as JSON
    }
}
