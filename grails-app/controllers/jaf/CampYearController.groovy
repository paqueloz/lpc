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

class CampYearController {
    
    // export CSV,XLS...
    def exportService
    def grailsApplication

    static allowedMethods = [save: "POST", update: "POST"]

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
    
    def export() {
        def campYearInstance = CampYear.get(params.id)
        if (params?.format && params.format != "html") {
            response.contentType = grailsApplication.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=addresses.${params.ext}")
            def l = campYearInstance.attendances.collect() { Attendance it -> it.person }
            exportService.export(params.format, response.outputStream, l, [:], [:])
        }
    }

}
