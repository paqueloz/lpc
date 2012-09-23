package jaf

import org.springframework.dao.DataIntegrityViolationException

class CampYearController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [campYearInstanceList: CampYear.list(params), campYearInstanceTotal: CampYear.count()]
    }

    def create() {
        params.year = new GregorianCalendar().get(Calendar.YEAR)+1
        [campYearInstance: new CampYear(params)]
    }

    // TODO, validation date début et date fin
    def save() {
        def campYearInstance = new CampYear(params)
        if (!campYearInstance.save(flush: true)) {
            render(view: "create", model: [campYearInstance: campYearInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'campYear.label', default: 'CampYear'), campYearInstance.id])
        redirect(action: "show", id: campYearInstance.id)
    }

    def show() {
        def campYearInstance = CampYear.get(params.id)
        if (!campYearInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'campYear.label', default: 'CampYear'), params.id])
            redirect(action: "list")
            return
        }

        [campYearInstance: campYearInstance]
    }

    def edit() {
        def campYearInstance = CampYear.get(params.id)
        if (!campYearInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'campYear.label', default: 'CampYear'), params.id])
            redirect(action: "list")
            return
        }

        [campYearInstance: campYearInstance]
    }

    def update() {
        def campYearInstance = CampYear.get(params.id)
        if (!campYearInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'campYear.label', default: 'CampYear'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (campYearInstance.version > version) {
                campYearInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'campYear.label', default: 'CampYear')] as Object[],
                          "Another user has updated this CampYear while you were editing")
                render(view: "edit", model: [campYearInstance: campYearInstance])
                return
            }
        }

        campYearInstance.properties = params

        if (!campYearInstance.save(flush: true)) {
            render(view: "edit", model: [campYearInstance: campYearInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'campYear.label', default: 'CampYear'), campYearInstance.id])
        redirect(action: "show", id: campYearInstance.id)
    }

    def delete() {
        def campYearInstance = CampYear.get(params.id)
        if (!campYearInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'campYear.label', default: 'CampYear'), params.id])
            redirect(action: "list")
            return
        }

        try {
            campYearInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'campYear.label', default: 'CampYear'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'campYear.label', default: 'CampYear'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
