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

import java.util.HashMap.Entry
import org.springframework.dao.DataIntegrityViolationException

class SecUserController {

    static allowedMethods = [save: "POST", update: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [secUserInstanceList: SecUser.list(params), secUserInstanceTotal: SecUser.count()]
    }

    def create() {
        [secUserInstance: new SecUser(params)]
    }

    def save() {
        def secUserInstance = new SecUser(params)
        if (!secUserInstance.save(flush: true)) {
            render(view: "create", model: [secUserInstance: secUserInstance])
            return
        }
        // SecUserSecRole.create(secUserInstance, SecRole.findByAuthority('ROLE_USER'))

		flash.message = message(code: 'default.created.message', args: [message(code: 'secUser.label', default: 'SecUser'), secUserInstance.id])
        redirect(action: "show", id: secUserInstance.id)
    }

    def show() {
        def secUserInstance = SecUser.get(params.id)
        if (!secUserInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'secUser.label', default: 'SecUser'), params.id])
            redirect(action: "list")
            return
        }

        def roleMap = [:] // map will contain all roles and a boolean value
        SecRole.findAll().each { SecRole s -> roleMap[(s.getAuthority())] = false }
        secUserInstance.getAuthorities().each { SecRole s -> roleMap[(s.getAuthority())] = true }

        [secUserInstance: secUserInstance, roles: roleMap]
    }

    def edit() {
        def secUserInstance = SecUser.get(params.id)
        if (!secUserInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'secUser.label', default: 'SecUser'), params.id])
            redirect(action: "list")
            return
        }

        def roleMap = [:] // map will contain all roles and a boolean value
        SecRole.findAll().each { SecRole s -> roleMap[(s.getAuthority())] = false }
        secUserInstance.getAuthorities().each { SecRole s -> roleMap[(s.getAuthority())] = true }

        [secUserInstance: secUserInstance, roles: roleMap]
    }

    def update() {
        def secUserInstance = SecUser.get(params.id)
        if (!secUserInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'secUser.label', default: 'SecUser'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (secUserInstance.version > version) {
                secUserInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'secUser.label', default: 'SecUser')] as Object[],
                          "Another user has updated this SecUser while you were editing")
                render(view: "edit", model: [secUserInstance: secUserInstance])
                return
            }
        }

        secUserInstance.properties = params

        // manage roles, find all params like _ROLE_ and apply the changes
        SecUserSecRole.removeAll(secUserInstance)
        params.entrySet().each { Entry e ->
            String current = (String)e.key 
            if (current.startsWith("ROLE_")) {
                SecRole role = SecRole.findByAuthority(current)
                if (e.value == "on") SecUserSecRole.create(secUserInstance,role,true)
            }
        }
        
        if (!secUserInstance.save(flush: true)) {
            render(view: "edit", model: [secUserInstance: secUserInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'secUser.label', default: 'SecUser'), secUserInstance.id])
        redirect(action: "show", id: secUserInstance.id)
    }

    def delete() {
        def secUserInstance = SecUser.get(params.id)
        if (!secUserInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'secUser.label', default: 'SecUser'), params.id])
            redirect(action: "list")
            return
        }

        try {
            secUserInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'secUser.label', default: 'SecUser'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'secUser.label', default: 'SecUser'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
