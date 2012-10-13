package jaf

import groovy.sql.Sql

import org.hibernate.*

class ReportController {

    SessionFactory sessionFactory
    def grailsApplication

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
        
        // FIXME change age range and filter on appliedForNextYear
        sql.eachRow("""SELECT
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
                HAVING (Age < 14)
                ORDER BY nationality, p.gender, p.first_name, cy.year DESC, Age;""") {
                    def rec = [ nationality : it.nationality,
                        gender : "",
                        firstName : it.first_name,
                        lastName : it.last_name,
                        newToLpc : it.new_to_lpc,
                        preferences : it.preferences,
                        status : it.Status,
                        location : it.location,
                        year : it.year,
                        statusA : it.status,
                        age : it.Age ]
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
        
        // FIXME change age range and filter on appliedForNextYear
        sql.eachRow("""SELECT
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
                HAVING (Age >= 14 and Age < 15)
                ORDER BY nationality, p.gender, p.first_name, cy.year DESC, Age;""") {
                    def rec = [ nationality : it.nationality,
                        gender : "",
                        firstName : it.first_name,
                        lastName : it.last_name,
                        newToLpc : it.new_to_lpc,
                        preferences : it.preferences,
                        status : it.Status,
                        location : it.location,
                        year : it.year,
                        statusA : it.status,
                        age : it.Age ]
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
        
        // FIXME change age range and filter on appliedForNextYear
        sql.eachRow("""SELECT
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
                HAVING (Age > 15 and Age <25)
                ORDER BY nationality, p.gender, p.first_name, cy.year DESC, Age;""") {
                    def rec = [ nationality : it.nationality,
                        gender : "",
                        firstName : it.first_name,
                        lastName : it.last_name,
                        newToLpc : it.new_to_lpc,
                        preferences : it.preferences,
                        status : it.Status,
                        location : it.location,
                        year : it.year,
                        statusA : it.status,
                        age : it.Age ]
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
        
        // FIXME change age range and filter on appliedForNextYear
        sql.eachRow("""SELECT
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
                where gender!='UNdefined'
                ORDER BY nationality, p.first_name, cy.year DESC, Age limit 20;""") {
                    def rec = [ nationality : it.nationality,
                        gender : "",
                        firstName : it.first_name,
                        lastName : it.last_name,
                        newToLpc : it.new_to_lpc,
                        preferences : it.preferences,
                        status : it.Status,
                        location : it.location,
                        year : it.year,
                        statusA : it.status,
                        age : it.Age ]
                    switch (it.gender) {
                        case "MALE": rec.gender = "M"; break;
                        case "FEMALE": rec.gender = "F";
                    }
                    recList << rec
                }

        [result:recList]
    }

}
