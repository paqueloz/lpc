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

import org.hibernate.*

class ReportController {

    // export CSV,XLS...
    def exportService
    def grailsApplication
    
    SessionFactory sessionFactory

    def index() {
        [:]
    }
    
    /**
     * List applications for next summer (younger)
     */
    def younger() {

        // flush Hibernate before accessing the DB
        assert sessionFactory != null
        def hibSession = sessionFactory.getCurrentSession()
        assert hibSession != null
        hibSession.flush()

        def sql = Sql.newInstance(grailsApplication.config.dataSource.url,
                grailsApplication.config.dataSource.username,
                grailsApplication.config.dataSource.password,
                grailsApplication.config.dataSource.driverClassName)

        def recList = []

        java.math.MathContext mc = new java.math.MathContext(3)
        
        sql.eachRow("""SELECT p.id,
                (
                select group_concat(r.short_key) from
                nationality nat
                left join region r on r.id = nat.country_id
                where person_id=p.id
                ) as nationality,
                p.gender, p.first_name, p.last_name, p.new_to_lpc, p.preferences, p.Status,
                c.location, cy.year, a.status,
                DATEDIFF(curdate(), p.birth_day)/365.25 as Age
                FROM person p
                left join attendance a on a.person_id = p.id
                left join camp_year cy on a.camp_id = cy.id
                left join camp c on c.id = cy.camp_id
                where applied_for_next_year = 1
                HAVING (Age < 13.5)
                ORDER BY nationality, p.gender, p.first_name, cy.year DESC, Age;""") {
                    def rec = [ id: it.id,
                        nationality : it.nationality,
                        gender : "",
                        firstName : it.first_name,
                        lastName : it.last_name,
                        newToLpc : it.new_to_lpc,
                        preferences : it.preferences,
                        status : it.Status,
                        location : it.location,
                        year : it.year,
                        statusA : it.status,
                        age : it.Age.round(mc) ]
                    switch (it.gender) {
                        case "MALE": rec.gender = "M"; break;
                        case "FEMALE": rec.gender = "F";
                    }
                    recList << rec
                }

        [result:recList]
    }
    
    /**
     * List applications for next summer (14th)
     */
    def fourteen() {

        // flush Hibernate before accessing the DB
        assert sessionFactory != null
        def hibSession = sessionFactory.getCurrentSession()
        assert hibSession != null
        hibSession.flush()

        def sql = Sql.newInstance(grailsApplication.config.dataSource.url,
                grailsApplication.config.dataSource.username,
                grailsApplication.config.dataSource.password,
                grailsApplication.config.dataSource.driverClassName)

        def recList = []
        
        java.math.MathContext mc = new java.math.MathContext(3)
        
        sql.eachRow("""SELECT  p.id,
                (
                select group_concat(r.short_key) from
                nationality nat
                left join region r on r.id = nat.country_id
                where person_id=p.id
                ) as nationality,
                p.gender, p.first_name, p.last_name, p.new_to_lpc, p.preferences, p.Status,
                c.location, cy.year, a.status,
                DATEDIFF(curdate(), p.birth_day)/365.25 as Age
                FROM person p
                left join attendance a on a.person_id = p.id
                left join camp_year cy on a.camp_id = cy.id
                left join camp c on c.id = cy.camp_id
                where applied_for_next_year = 1
                HAVING (Age >= 13.5 and Age <= 14.5)
                ORDER BY nationality, p.gender, p.first_name, cy.year DESC, Age;""") {
                    def rec = [ id: it.id,
                        nationality : it.nationality,
                        gender : "",
                        firstName : it.first_name,
                        lastName : it.last_name,
                        newToLpc : it.new_to_lpc,
                        preferences : it.preferences,
                        status : it.Status,
                        location : it.location,
                        year : it.year,
                        statusA : it.status,
                        age : it.Age.round(mc) ]
                    switch (it.gender) {
                        case "MALE": rec.gender = "M"; break;
                        case "FEMALE": rec.gender = "F";
                    }
                    recList << rec
                }

        [result:recList]
    }
    /**
     * List applications for next summer (older)
     */
    def older() {

        // flush Hibernate before accessing the DB
        assert sessionFactory != null
        def hibSession = sessionFactory.getCurrentSession()
        assert hibSession != null
        hibSession.flush()

        def sql = Sql.newInstance(grailsApplication.config.dataSource.url,
                grailsApplication.config.dataSource.username,
                grailsApplication.config.dataSource.password,
                grailsApplication.config.dataSource.driverClassName)

        def recList = []
        
        java.math.MathContext mc = new java.math.MathContext(3)
        
        sql.eachRow("""SELECT p.id,
                (
                select group_concat(r.short_key) from
                nationality nat
                left join region r on r.id = nat.country_id
                where person_id=p.id
                ) as nationality,
                p.gender, p.first_name, p.last_name, p.new_to_lpc, p.preferences, p.Status,
                c.location, cy.year, a.status,
                DATEDIFF(curdate(), p.birth_day)/365.25 as Age
                FROM person p
                left join attendance a on a.person_id = p.id
                left join camp_year cy on a.camp_id = cy.id
                left join camp c on c.id = cy.camp_id
                where applied_for_next_year = 1
                and p.status = 'CAmper' HAVING (Age > 14.5)
                ORDER BY nationality, p.gender, p.first_name, cy.year DESC, Age;""") {
                    def rec = [ id: it.id,
                        nationality : it.nationality,
                        gender : "",
                        firstName : it.first_name,
                        lastName : it.last_name,
                        newToLpc : it.new_to_lpc,
                        preferences : it.preferences,
                        status : it.Status,
                        location : it.location,
                        year : it.year,
                        statusA : it.status,
                        age : it.Age.round(mc) ]
                    switch (it.gender) {
                        case "MALE": rec.gender = "M"; break;
                        case "FEMALE": rec.gender = "F";
                    }
                    recList << rec
                }

        [result:recList]
    }
    /**
     * List applications for next summer (counselor)
     */
    def counselor() {

        // flush Hibernate before accessing the DB
        assert sessionFactory != null
        def hibSession = sessionFactory.getCurrentSession()
        assert hibSession != null
        hibSession.flush()

        def sql = Sql.newInstance(grailsApplication.config.dataSource.url,
                grailsApplication.config.dataSource.username,
                grailsApplication.config.dataSource.password,
                grailsApplication.config.dataSource.driverClassName)

        def recList = []
        
        // FIXME experience?
        java.math.MathContext mc = new java.math.MathContext(3)
        sql.eachRow("""SELECT
                        (
                        select group_concat(r.short_key) from nationality nat
                        left join region r on r.id = nat.country_id
                        where person_id = p.id
                        ) as nationality,
                        p.gender, p.first_name, p.last_name, p.new_to_lpc, p.preferences, p.Status,
                        (
                        select group_concat(l.language) from language_level l
                        where l.person_id = p.id
                        ) as languages,
                        DATEDIFF(curdate(), p.birth_day)/365.25 as Age,
                        p.id
                        FROM person p
                        where applied_for_next_year = 1
                        and p.Status not like 'CAmper'
                        HAVING (Age > 14.5)
                        ORDER BY gender, Age, nationality;""") {
                    def rec = [ nationality : it.nationality,
                        gender : "",
                        firstName : it.first_name,
                        lastName : it.last_name,
                        newToLpc : it.new_to_lpc,
                        preferences : it.preferences,
                        status : it.Status,
                        languages : it.languages,
                        age : it.Age.round(mc) ]
                    switch (it.gender) {
                        case "MALE": rec.gender = "M"; break;
                        case "FEMALE": rec.gender = "F";
                    }
                    recList << rec
                }

        [result:recList]
    }
    
    def appliedForNextYear() 
    {
        if (params?.format && params.format != "html") {
            response.contentType = grailsApplication.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=addresses.${params.ext}")
            def l = Person.findAllByAppliedForNextYear(false)
            exportService.export(params.format, response.outputStream, l, [:], [:])
        }
    }

    def notSelectedAtCC() 
    {
        if (params?.format && params.format != "html") {
            response.contentType = grailsApplication.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=addresses.${params.ext}")
            def l = Person.findAllByAppliedForNextYear(false)
            exportService.export(params.format, response.outputStream, l, [:], [:])
            // FIXME NotSelected-atCC
            // FIXME il faut gérer l'année
            // FIXME le lien pourrait se trouver dans le camp "notselected at cc"
            // ici on mettrait juste un lien
            // l'utilisateur cherche l'année
            // va chercher les personnes et les ramène
        }
    }

}
