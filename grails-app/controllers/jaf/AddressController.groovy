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

class AddressController {

    static allowedMethods = [save: "POST", update: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [addressInstanceList: Address.list(params), addressInstanceTotal: Address.count()]
    }

    def create() {
        /* BEGIN MANUAL EDIT */
        Address addressInstance = new Address(params)
        addressInstance.active = true
        [addressInstance: addressInstance]
    }

    def save() {
        def addressInstance = new Address(params)
        if (!addressInstance.save(flush: true)) {
            render(view: "create", model: [addressInstance: addressInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'address.label', default: 'Address'), addressInstance.id])
        redirect(action: "show", id: addressInstance.id)
    }

    def show() {
        def addressInstance = Address.get(params.id)
        if (!addressInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'address.label', default: 'Address'), params.id])
            redirect(action: "list")
            return
        }

        [addressInstance: addressInstance]
    }

    def edit() {
        def addressInstance = Address.get(params.id)
        if (!addressInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'address.label', default: 'Address'), params.id])
            redirect(action: "list")
            return
        }

        [addressInstance: addressInstance]
    }

    def update() {
        def addressInstance = Address.get(params.id)
        if (!addressInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'address.label', default: 'Address'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (addressInstance.version > version) {
                addressInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'address.label', default: 'Address')] as Object[],
                          "Another user has updated this Address while you were editing")
                render(view: "edit", model: [addressInstance: addressInstance])
                return
            }
        }

        addressInstance.properties = params

        if (!addressInstance.save(flush: true)) {
            render(view: "edit", model: [addressInstance: addressInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'address.label', default: 'Address'), addressInstance.id])
        redirect(action: "show", id: addressInstance.id)
    }

    def delete() {
        def addressInstance = Address.get(params.id)
        if (!addressInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'address.label', default: 'Address'), params.id])
            redirect(action: "list")
            return
        }

        try {
            addressInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'address.label', default: 'Address'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'address.label', default: 'Address'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
