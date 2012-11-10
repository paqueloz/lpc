package jaf

import org.springframework.dao.DataIntegrityViolationException

class StaffController {

    static allowedMethods = [save: "POST", update: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [staffInstanceList: Staff.list(params), staffInstanceTotal: Staff.count()]
    }

    def create() {
        [staffInstance: new Staff(params)]
    }

    def save() {
        // BEGIN MANUAL EDIT
        Camp c
        if (params.camp.id) {
            c = Camp.findById(params.camp.id)
        }
        Person p
        if (params.person_id) {
            p = Person.findById(params.person_id)
        }
        def staffInstance = new Staff(camp:c,person:p,comment:params.comment,startDate:params.startDate,endDate:params.endDate)
        // END MANUAL EDIT
        if (!staffInstance.save(flush: true)) {
            render(view: "create", model: [staffInstance: staffInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'staff.label', default: 'Staff'), staffInstance.id])
        redirect(action: "show", id: staffInstance.id)
    }

    def show() {
        def staffInstance = Staff.get(params.id)
        if (!staffInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'staff.label', default: 'Staff'), params.id])
            redirect(action: "list")
            return
        }

        [staffInstance: staffInstance]
    }

    def edit() {
        def staffInstance = Staff.get(params.id)
        if (!staffInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'staff.label', default: 'Staff'), params.id])
            redirect(action: "list")
            return
        }

        [staffInstance: staffInstance]
    }

    def update() {
        def staffInstance = Staff.get(params.id)
        if (!staffInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'staff.label', default: 'Staff'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (staffInstance.version > version) {
                staffInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'staff.label', default: 'Staff')] as Object[],
                          "Another user has updated this Staff while you were editing")
                render(view: "edit", model: [staffInstance: staffInstance])
                return
            }
        }


        // BEGIN MANUAL EDIT
        // the search field is causing trouble to the next line
        // staffInstance.properties = params
        if (!params.person_id) {
            params.person_id = params.person_id_old
        }
        staffInstance.person = Person.findById(params.person_id)
        staffInstance.comment = params.comment
        staffInstance.startDate = params.startDate
        staffInstance.endDate = params.endDate
        // END MANUAL EDIT
        
        if (!staffInstance.save(flush: true)) {
            render(view: "edit", model: [staffInstance: staffInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'staff.label', default: 'Staff'), staffInstance.id])
        redirect(action: "show", id: staffInstance.id)
    }

    def delete() {
        def staffInstance = Staff.get(params.id)
        if (!staffInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'staff.label', default: 'Staff'), params.id])
            redirect(action: "list")
            return
        }

        try {
            staffInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'staff.label', default: 'Staff'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'staff.label', default: 'Staff'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
