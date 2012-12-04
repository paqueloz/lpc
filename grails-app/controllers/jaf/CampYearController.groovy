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

import groovy.sql.Sql

import org.hibernate.SessionFactory;
import org.springframework.dao.DataIntegrityViolationException

class CampYearController {
    
    // export CSV,XLS...
    def exportService
    def grailsApplication
    SessionFactory sessionFactory
    
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

    def getPerson(def sql, def personId) {
        sql.firstRow("""select 
                p1.id, 
                p1.gender, 
                if (p1.new_to_lpc>0,'yes','no') as NewToLPC,
                date_format(p1.birth_day,'%d-%m-%Y') as BirthDay,
                p1.first_name, 
                p1.last_name, 
                p1.parent_name,
                p1.preferences,
                (
                select group_concat(concat(r6.short_key)) from nationality n6
                left
                join region r6 on r6.id=n6.country_id
                where  n6.person_id = p1.id
                group by n6.person_id
                ) as nationalities,
                (
                select group_concat(concat(l.language)) from language_level l
                where l.person_id = p1.id
                ) as languages,        
                if (IFNULL(a.person_id,7)=7,concat(r.relationship,'(',p2.first_name, ' ',p2.last_name,')'),'Own address') AS TypAdr,
                if (IFNULL(a.person_id,7)=7,a2.street1,a.street1) as Street1,
                if (IFNULL(a.person_id,7)=7,a2.street2,a.street2) as Street2,
                if (IFNULL(a.person_id,7)=7,a2.zip_code,a.zip_code) as Zip_code,
                if (IFNULL(a.person_id,7)=7,a2.city,a.city) as City,
                if (IFNULL(a.person_id,7)=7,reg2.short_key,reg.short_key) as Country,
                (
                select group_concat(concat(relationship,'(',p4.first_name, ' ',p4.last_name,') ')) from person_relation r4
                left join person p4 on r4.other_id = p4.id
                where r4.person_id = p1.id
                group by r4.person_id
                ) as Relatives
                
                from person p1
                left join person_relation r on p1.id=r.person_id and r.relationship='livesWith'
                left join person p2 on p2.id=r.other_id
                left join address a on p1.id = a.person_id
                left join address a2 on r.other_id = a2.person_id
                left join region reg on reg.id = a.country_id
                left join region reg2 on reg2.id = a2.country_id
                where p1.id = ${personId};""")
    }
        
    def export() {
        def campYearInstance = CampYear.get(params.id)
        if (params?.format && params.format != "html") {
            response.contentType = grailsApplication.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=addresses.${params.ext}")

            // flush Hibernate before accessing the DB
            assert sessionFactory != null
            def hibSession = sessionFactory.getCurrentSession()
            assert hibSession != null
            hibSession.flush()
    
            def sql = Sql.newInstance(grailsApplication.config.dataSource.url,
                    grailsApplication.config.dataSource.username,
                    grailsApplication.config.dataSource.password,
                    grailsApplication.config.dataSource.driverClassName)

            def l = campYearInstance.attendances.collect() { Attendance it ->
                getPerson(sql, it.person.id) 
            }
            exportService.export(params.format, response.outputStream, l, [:], [:])
        }
    }

}
