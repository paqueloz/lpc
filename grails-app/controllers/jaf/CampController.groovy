package jaf

import org.springframework.dao.DataIntegrityViolationException

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
        def campInstance = Camp.get(params.id)
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
}
