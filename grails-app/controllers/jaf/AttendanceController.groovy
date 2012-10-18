package jaf

import org.springframework.dao.DataIntegrityViolationException

class AttendanceController {

    static allowedMethods = [save: "POST", update: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [attendanceInstanceList: Attendance.list(params), attendanceInstanceTotal: Attendance.count()]
    }

    def create() {
        [attendanceInstance: new Attendance(params)]
    }

    def save() {
        def attendanceInstance = new Attendance(person : Person.findById(params.person_id),
            camp : CampYear.findById(params.camp?.id),
            status : params.status)
        if (!attendanceInstance.save(flush: true)) {
            render(view: "create", model: [attendanceInstance: attendanceInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'attendance.label', default: 'Attendance'), attendanceInstance.id])
        redirect(controller: "campYear", action: "show", id: attendanceInstance.camp.id)
    }

    def show() {
        def attendanceInstance = Attendance.get(params.id)
        if (!attendanceInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'attendance.label', default: 'Attendance'), params.id])
            redirect(action: "list")
            return
        }

        [attendanceInstance: attendanceInstance]
    }

    def edit() {
        def attendanceInstance = Attendance.get(params.id)
        if (!attendanceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'attendance.label', default: 'Attendance'), params.id])
            redirect(action: "list")
            return
        }

        [attendanceInstance: attendanceInstance]
    }

    def update() {
        def attendanceInstance = Attendance.get(params.id)
        if (!attendanceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'attendance.label', default: 'Attendance'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (attendanceInstance.version > version) {
                attendanceInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'attendance.label', default: 'Attendance')] as Object[],
                          "Another user has updated this Attendance while you were editing")
                render(view: "edit", model: [attendanceInstance: attendanceInstance])
                return
            }
        }

        attendanceInstance.properties = params

        if (!attendanceInstance.save(flush: true)) {
            render(view: "edit", model: [attendanceInstance: attendanceInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'attendance.label', default: 'Attendance'), attendanceInstance.id])
        redirect(action: "show", id: attendanceInstance.id)
    }

    def delete() {
        def attendanceInstance = Attendance.get(params.id)
        if (!attendanceInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'attendance.label', default: 'Attendance'), params.id])
            redirect(action: "list")
            return
        }

        try {
            attendanceInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'attendance.label', default: 'Attendance'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'attendance.label', default: 'Attendance'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
