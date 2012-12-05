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

import java.sql.SQLClientInfoException;

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
            // flush Hibernate before accessing the DB
            assert sessionFactory != null
            def hibSession = sessionFactory.getCurrentSession()
            assert hibSession != null
            hibSession.flush()
    
            def sql = Sql.newInstance(grailsApplication.config.dataSource.url,
                    grailsApplication.config.dataSource.username,
                    grailsApplication.config.dataSource.password,
                    grailsApplication.config.dataSource.driverClassName)

            response.contentType = grailsApplication.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=addresses.${params.ext}")
            def l = []
            sql.eachRow("""select 
                p1.id,
                p1.first_name, 
                p1.last_name,
                if(IFNULL(a.person_id,7)=7,concat(r.relationship,'(',p2.first_name, ' ',p2.last_name,')'),'Own address') AS TypAdr,
                if(IFNULL(a.person_id,7)=7,a2.street1,a.street1) as Street1,
                if(IFNULL(a.person_id,7)=7,a2.street2,a.street2) as Street2,
                if(IFNULL(a.person_id,7)=7,a2.zip_code,a.zip_code) as Zip_code,
                if(IFNULL(a.person_id,7)=7,a2.city,a.city) as City,
                if(IFNULL(a.person_id,7)=7,reg2.short_key,reg.short_key) as Country,
                (
                select group_concat(concat(relationship,'(',p4.first_name, ' ',p4.last_name,') ')) from person_relation r4
                left join
                person p4 on r4.other_id = p4.id
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
                
                where p1.applied_for_next_year>0;
            """) {
                // without the intermediate rec, export will not work
                def rec = [
                        id : it.id,
                        first_name : it.first_name,
                        last_name : it.last_name,
                        TypAdr : it.TypAdr,
                        Street1 : it.Street1,
                        Street2 : it.Street2,
                        Zip_code : it.Zip_code,
                        City : it.City,
                        Country : it.Country,
                        Relatives : it.Relatives,
                    ]
                l << rec
            }
            log.info("Result:${l.size()}")
            List fields = ['id','first_name','last_name','TypAdr','Street1','Street2','Zip_code','City','Country','Relatives']
            exportService.export(params.format, response.outputStream, l, fields, [:], [:], [:])
        }
    }

    def notSelectedAtCC() 
    {
        if (params?.format && params.format != "html") {

            def sql = Sql.newInstance(grailsApplication.config.dataSource.url,
                grailsApplication.config.dataSource.username,
                grailsApplication.config.dataSource.password,
                grailsApplication.config.dataSource.driverClassName)
            
//            sql.execute("""
//                DROP PROCEDURE IF EXISTS  `lpc`.`AfterCC`;
//                DELIMITER \$\$
//                CREATE DEFINER=`root`@`localhost` PROCEDURE `lpc`.`AfterCC`(
//                  IN yearOfCamps INT
//                )
//                READS SQL DATA
//                -- The year given in parameter is the year for which people submitted the application form
//                -- in other words, the year of the end of the CC
//                proc: BEGIN
//                
//                  -- Declare '_val' variables to read in each record from the cursor
//                  DECLARE pID_val INT;
//                
//                  DECLARE debug SMALLINT DEFAULT 0;
//                
//                  -- Declare variables used just for cursor and loop control
//                  DECLARE no_more_rows BOOLEAN;
//                  DECLARE loop_cntr INT DEFAULT 0;
//                  DECLARE num_rows INT DEFAULT 0;
//                  DECLARE newInserted INT DEFAULT 0;
//                  DECLARE campIdNotSelected INT;
//                  
//                  -- Declare the cursor
//                  DECLARE pList_cur CURSOR FOR
//                    select id from person where
//                    id in (
//                    select id from person where applied_for_next_year>0
//                    ) and id not in (
//                    -- person(s) who are in a camp (at least invited)
//                    select distinct a.person_id from attendance a
//                    left join camp_year cy on a.camp_id=cy.id and cy.year=yearOfCamps
//                    left join camp c on cy.camp_id=c.id
//                    where a.camp_id is not null AND cy.id is not null
//                     and location<> 'NotSelected-atCC'
//                    ) ;
//                
//                  -- Declare 'handlers' for exceptions
//                  DECLARE CONTINUE HANDLER FOR NOT FOUND
//                    SET no_more_rows = TRUE;
//                
//                  -- Creation of the "camp - NotSelected"
//                  SET campIdNotSelected = (select id from camp where location like 'Not%' ) ;
//                  SET @q1=concat('insert into camp_year (version,camp_id,year) VALUES (0,',campIdNotSelected,', ',yearOfCamps,')');
//                  PREPARE query FROM @q1;
//                  EXECUTE query;
//                  DEALLOCATE PREPARE query;
//                
//                  SET campIdNotSelected = (select id from camp_year where camp_id=campIdNotSelected and year=yearOfCamps);
//                  IF debug=1 THEN select cy.id 'ID of newCamp', c.location,cy.year from camp c
//                      left join camp_year cy on cy.camp_id=c.id
//                      where location like 'Not%'  and cy.year=2013 ;
//                  END IF;
//                  
//                  -- 'open' the cursor and capture the number of rows returned
//                  -- set the number of camps
//                  OPEN pList_cur;
//                  select FOUND_ROWS() into num_rows;
//                  IF debug=1 THEN select 'Nb persons does not have a camp invitation', num_rows;  END IF;
//                               
//                  -- Initialise queries for insertion
//                  SET @insertAttendance = concat('INSERT INTO `lpc`.`attendance` (version, camp_id, person_id,status) VALUES (0, ',campIdNotSelected, ' , ');
//                
//                  mainLoop: LOOP
//                
//                    FETCH  pList_cur INTO   pID_val;
//                
//                      IF debug=1 THEN select 'loop:', pID_val, loop_cntr, num_rows; END IF;
//                    
//                    -- break out of the loop if
//                    IF loop_cntr >=  num_rows THEN
//                        CLOSE pList_cur;
//                        LEAVE mainLoop;
//                    END IF;
//                    SET loop_cntr = loop_cntr + 1;
//                  
//                    set @q1=concat(@insertAttendance, pID_val, ',''Invited'');');
//                      IF debug=1 THEN select @q1; END IF;
//                    
//                      -- select @q1;
//                    PREPARE query FROM @q1;
//                    EXECUTE query;
//                    DEALLOCATE PREPARE query;
//                
//                  END LOOP mainLoop;
//                
//                  select loop_cntr, ' persons invited to the camp ;;  person without invitation', num_rows ;
//                  
//                -- Remove the application form
//                  set @q1='update person set applied_for_next_year=0 where applied_for_next_year<>0';
//                  IF debug<>88 THEN select @q1 as 'Removing the application form'; END IF;
//                    
//                  PREPARE query FROM @q1;
//                  EXECUTE query;
//                  DEALLOCATE PREPARE query;
//                
//                END
//                \$\$
//                
//                /*
//                -- Supression des camps sans "attendance" (aucune personne)
//                  select 'listOfCampWithoutPeople',group_concat(id) from camp_year cy
//                  where cy.id not in ( select distinct camp_id from attendance)
//                  group by 1
//                    
//                delete from camp_year where id in (
//                411,412,413
//                )
//                
//                
//                -- Supression de toutes les attendances pour NotSelected(2013)
//                delete from attendance where camp_id in (
//                select cy.id from camp c
//                left join camp_year cy on cy.camp_id=c.id
//                where location like 'Not%' and cy.year=2013
//                )
//                
//                */
//            """)    

            // assume that the query is run in December or January, take the year 3 months further
            GregorianCalendar cal = new GregorianCalendar()
            cal.add(Calendar.MONTH,3)
            int yearOfCamps = cal.get(Calendar.YEAR)
            log.info("Calling stored procedure for year ${yearOfCamps}")
            def result = sql.execute("call AfterCC(${yearOfCamps})")

            response.contentType = grailsApplication.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=addresses.${params.ext}")
            def l = Person.findAllByAppliedForNextYear(false)
            exportService.export(params.format, response.outputStream, result, [:], [:])

        }
    }

}


