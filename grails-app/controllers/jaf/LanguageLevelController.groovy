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

class LanguageLevelController {

    static allowedMethods = [save: "POST", update: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [languageLevelInstanceList: LanguageLevel.list(params), languageLevelInstanceTotal: LanguageLevel.count()]
    }

    def create() {
        [languageLevelInstance: new LanguageLevel(params)]
    }

    def save() {
        def languageLevelInstance = new LanguageLevel(params)
        if (!languageLevelInstance.save(flush: true)) {
            render(view: "create", model: [languageLevelInstance: languageLevelInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'languageLevel.label', default: 'LanguageLevel'), languageLevelInstance.id])
        redirect(action: "show", id: languageLevelInstance.id)
    }

    def show() {
        def languageLevelInstance = LanguageLevel.get(params.id)
        if (!languageLevelInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'languageLevel.label', default: 'LanguageLevel'), params.id])
            redirect(action: "list")
            return
        }

        [languageLevelInstance: languageLevelInstance]
    }

    def edit() {
        def languageLevelInstance = LanguageLevel.get(params.id)
        if (!languageLevelInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'languageLevel.label', default: 'LanguageLevel'), params.id])
            redirect(action: "list")
            return
        }

        [languageLevelInstance: languageLevelInstance]
    }

    def update() {
        def languageLevelInstance = LanguageLevel.get(params.id)
        if (!languageLevelInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'languageLevel.label', default: 'LanguageLevel'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (languageLevelInstance.version > version) {
                languageLevelInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'languageLevel.label', default: 'LanguageLevel')] as Object[],
                          "Another user has updated this LanguageLevel while you were editing")
                render(view: "edit", model: [languageLevelInstance: languageLevelInstance])
                return
            }
        }

        languageLevelInstance.properties = params

        if (!languageLevelInstance.save(flush: true)) {
            render(view: "edit", model: [languageLevelInstance: languageLevelInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'languageLevel.label', default: 'LanguageLevel'), languageLevelInstance.id])
        redirect(action: "show", id: languageLevelInstance.id)
    }

    def delete() {
        def languageLevelInstance = LanguageLevel.get(params.id)
        if (!languageLevelInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'languageLevel.label', default: 'LanguageLevel'), params.id])
            redirect(action: "list")
            return
        }

        try {
            languageLevelInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'languageLevel.label', default: 'LanguageLevel'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'languageLevel.label', default: 'LanguageLevel'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
