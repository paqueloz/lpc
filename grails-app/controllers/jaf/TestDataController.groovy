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
                relationship    : Relationship.fatherOf,
                other           : sop,
                comment         : "maxi king").save(failOnError: true)
        pr = new PersonRelation(person : sop,
                    relationship    : Relationship.daughterOf,
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

        Contact c = new Contact (   person  : p,
                type    : ContactType.PHONE,
                mode    : ContactMode.HOME,
                value   : "+41 22 755 15 80",
                active  : true,
                )
        c.save(failOnError: true)
        c = new Contact (           person  : p,
                type    : ContactType.EMAIL,
                mode    : ContactMode.HOME,
                value   : "paqueloz@gmail.com",
                active  : true,
                )
        c.save(failOnError: true)
        c = new Contact (           person  : p,
                type    : ContactType.MOBILE,
                mode    : ContactMode.HOME,
                value   : "+41 79 768 30 17",
                active  : true,
                )
        c.save(failOnError: true)
        c = new Contact (           person  : p,
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

    }
}
