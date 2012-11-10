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

class NationalityController {

    static allowedMethods = [save: "POST", update: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [nationalityInstanceList: Nationality.list(params), nationalityInstanceTotal: Nationality.count()]
    }

    def create() {
        [nationalityInstance: new Nationality(params)]
    }

    def save() {
        def nationalityInstance = new Nationality(params)
        if (!nationalityInstance.save(flush: true)) {
            render(view: "create", model: [nationalityInstance: nationalityInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'nationality.label', default: 'Nationality'), nationalityInstance.id])
        redirect(action: "show", id: nationalityInstance.id)
    }

    def show() {
        def nationalityInstance = Nationality.get(params.id)
        if (!nationalityInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'nationality.label', default: 'Nationality'), params.id])
            redirect(action: "list")
            return
        }

        [nationalityInstance: nationalityInstance]
    }

    def edit() {
        def nationalityInstance = Nationality.get(params.id)
        if (!nationalityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'nationality.label', default: 'Nationality'), params.id])
            redirect(action: "list")
            return
        }

        [nationalityInstance: nationalityInstance]
    }

    def update() {
        def nationalityInstance = Nationality.get(params.id)
        if (!nationalityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'nationality.label', default: 'Nationality'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (nationalityInstance.version > version) {
                nationalityInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'nationality.label', default: 'Nationality')] as Object[],
                          "Another user has updated this Nationality while you were editing")
                render(view: "edit", model: [nationalityInstance: nationalityInstance])
                return
            }
        }

        nationalityInstance.properties = params

        if (!nationalityInstance.save(flush: true)) {
            render(view: "edit", model: [nationalityInstance: nationalityInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'nationality.label', default: 'Nationality'), nationalityInstance.id])
        redirect(action: "show", id: nationalityInstance.id)
    }

    def delete() {
        def nationalityInstance = Nationality.get(params.id)
        if (!nationalityInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'nationality.label', default: 'Nationality'), params.id])
            redirect(action: "list")
            return
        }

        try {
            nationalityInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'nationality.label', default: 'Nationality'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'nationality.label', default: 'Nationality'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
