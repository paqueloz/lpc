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

import java.text.SimpleDateFormat
import grails.plugins.countries.*

class TestDataController {

    def index() {
        populateDb()
        redirect(controller: "person", action: "list")     
    }
    
    public static void populateDb() {
        if (!Country.findByShortKey("CH")) {
            def cont = new Continent( key : "EU" ).save(failOnError: true);
            new Country( shortKey : "CH",
                key : "CHE",
                capital : "Bern",
                iso3166Number: 756,
                continent: cont
                ).save(failOnError: true)
        }
        
        Person p = new Person(  firstName   : "Pierre-Antoine",
                lastName    : "Queloz",
                birthDay    : new SimpleDateFormat("yyyy-MM-dd").parse("1971-03-30"), // "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
                gender      : Gender.MALE,
                status      : PersonStatus.UNdefined,
                parentName  : "Michel Queloz",
                )
        p.save(failOnError: true)

        Person sop = new Person(  firstName   : "Soline",
                lastName    : "Queloz",
                birthDay    : new SimpleDateFormat("yyyy-MM-dd").parse("1998-09-11"), // "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
                gender      : Gender.FEMALE,
                status      : PersonStatus.UNdefined,
                )
        sop.save(failOnError: true)

        PersonRelation pr = new PersonRelation(person : p,
                relationship    : Relationship.parentOf,
                other           : sop,
                comment         : "maxi king").save(failOnError: true)
        pr = new PersonRelation(person : sop,
                    relationship    : Relationship.childOf,
                    other           : p,
                    comment         : "so nice").save(failOnError: true)
        pr = new PersonRelation(person : sop,
                relationship    : Relationship.livesWith,
                other           : p,
                comment         : "good").save(failOnError: true)

       Address a = new Address (   person  : p,
                street1 : "11 ch du Lac",
                zipCode : "1290",
                city    : "Versoix",
                country : Country.findByShortKey("CH"),
                active  : true,
                )
        a.save(failOnError: true)

        PersonContact c = new PersonContact (   person  : p,
                type    : ContactType.PHONE,
                mode    : ContactMode.HOME,
                value   : "+41 22 755 15 80",
                active  : true,
                )
        c.save(failOnError: true)
        c = new PersonContact (           person  : p,
                type    : ContactType.EMAIL,
                mode    : ContactMode.HOME,
                value   : "paqueloz@gmail.com",
                active  : true,
                )
        c.save(failOnError: true)
        c = new PersonContact (           person  : p,
                type    : ContactType.MOBILE,
                mode    : ContactMode.HOME,
                value   : "+41 79 768 30 17",
                active  : true,
                )
        c.save(failOnError: true)
        c = new PersonContact (           person  : p,
                type    : ContactType.EMAIL,
                mode    : ContactMode.WORK,
                value   : "queloz@rembo.com",
                active  : false,
                )
        c.save(failOnError: true)

        LanguageLevel l = new LanguageLevel (
                person      : p,
                language    : new Locale("fr","CH"),
                level       : Level.MOTHERTONGUE,
                )
        l.save(failOnError: true)
        l = new LanguageLevel (     person      : p,
                language    : new Locale("de","DE"),
                level       : Level.GOOD,
                )
        l.save(failOnError: true)
        l = new LanguageLevel (     person      : p,
                language    : new Locale("en"),
                level       : Level.FLUENT,
                )
        l.save(failOnError: true)

        Nationality n = new Nationality ( person: p, country: Country.findByShortKey("CH") )
        n.save(failOnError: true)

        Person pelet = new Person(  firstName   : "Big",
                lastName    : "Boss",
                birthDay    : new SimpleDateFormat("yyyy-MM-dd").parse("1969-01-02"),
                gender      : Gender.MALE,
                status      : PersonStatus.Director).save(failOnError: true)
        Camp k = new Camp(          location    : "Evolène",
                country     : Country.findByShortKey("CH"),
                personHouse : pelet).save(failOnError: true)

        CampYear ky = new CampYear( camp : k, year : 1986 ).save(failOnError: true)

        ky = new CampYear( camp : k, year : 1987 ).save(failOnError: true)
        Attendance at = new Attendance(person: p, camp: ky, status: PersonStatus.CAmper).save(failOnError: true)
        at = new Attendance(person: pelet, camp: ky, status: PersonStatus.Director).save(failOnError: true)

        ky = new CampYear( camp : k, year : 1988 ).save(failOnError: true)
        at = new Attendance(person: p, camp: ky, status: PersonStatus.CAmper).save(failOnError: true)
        at = new Attendance(person: pelet, camp: ky, status: PersonStatus.Director).save(failOnError: true)

        ky = new CampYear( camp : k, year : 1989 ).save(failOnError: true)

        Person mario = new Person(  firstName    : "Super",
                lastName    : "Mario",
                birthDay    : new SimpleDateFormat("yyyy-MM-dd").parse("1980-08-04"),
                gender      : Gender.MALE,
                status      : PersonStatus.UNdefined).save(failOnError: true)
        Staff smario = new Staff(   person      : mario,
                camp        : k,
                comment     : "plombier",
                startDate   : new SimpleDateFormat("yyyy-MM-dd").parse("2000-08-04")).save(failOnError:true)
        new Person(  firstName   : "Dj",
            lastName    : "Bobo",
            birthDay    : new SimpleDateFormat("yyyy-MM-dd").parse("1998-09-08"), // "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
            gender      : Gender.MALE,
            status      : PersonStatus.UNdefined,
            ).save(failOnError:true)
        new Person(  firstName   : "Dijé",
            lastName    : "Bobo",
            birthDay    : new SimpleDateFormat("yyyy-MM-dd").parse("1998-09-08"), // "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
            gender      : Gender.MALE,
            status      : PersonStatus.UNdefined,
            ).save(failOnError:true)
        new Person(  firstName   : "Dj",
            lastName    : "Bobo",
            birthDay    : new SimpleDateFormat("yyyy-MM-dd").parse("1997-08-09"), // "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
            gender      : Gender.MALE,
            status      : PersonStatus.UNdefined,
            ).save(failOnError:true)
    
    }
}
