import jaf.ContactMode;
import jaf.ContactType;
import jaf.Gender;
import jaf.LastStatus;
import jaf.Person;
import jaf.Address;
import jaf.Contact;
import jaf.LanguageLevel;
import jaf.Level;

import java.text.SimpleDateFormat;
import java.util.Date;


/*
 * This program is intended to help the Luethi-Peterson Camps association
 *     to help them store and manage their users
 *
 *     Copyright (C) 2012 Jacques Fontignie
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import grails.plugins.countries.Country;
import grails.util.Environment

import jaf.SecRole
import jaf.SecUser
import jaf.SecUserSecRole

// import org.codehaus.groovy.grails.commons.ConfigurationHolder

class BootStrap {
    
    def grailsApplication

    def init = { servletContext ->

        def currentEnv = Environment.current
        
        def userRole = SecRole.findByAuthority('ROLE_USER') ?: new SecRole(authority: 'ROLE_USER').save(failOnError: true)
        def adminRole = SecRole.findByAuthority('ROLE_ADMIN') ?: new SecRole(authority: 'ROLE_ADMIN').save(failOnError: true)


        def adminUser = SecUser.findByUsername('admin') ?: new SecUser(
                username: 'admin',
                password: currentEnv == Environment.PRODUCTION ? grailsApplication.config.sec.adm.pass : "abcd",
                enabled: true).save(failOnError: true)

        if (!adminUser.authorities.contains(adminRole)) {
            SecUserSecRole.create adminUser, adminRole
            SecUserSecRole.create adminUser, userRole
        }
        
        if (currentEnv == Environment.DEVELOPMENT) {
            Person p = new Person(  firstName   : "Pierre-Antoine", 
                                    lastName    : "Queloz",
                                    birthDay    : new SimpleDateFormat("yyyy-MM-dd").parse("1971-03-30"), // "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
                                    gender      : Gender.MALE,
                                    status      : LastStatus.UNdefined,
                                    )
            p.save(failOnError: true)
            
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
        }
        
    }
    def destroy = {
    }
}

/*
class Address {

    static belongsTo = [person:Person]

    String street1
    String street2
    String zipCode
    String city
    Country country
    boolean active

    Date dateCreated
    Date lastUpdated

    static constraints = {
        street1()
        street2(nullable: true)
        zipCode(nullable: true)
        city(nullable: true)
        country(nullable: true)
        active()
    }

    static searchable = {
        except = ['dateCreated','lastUpdated','country']
    }
    
    def String toString() {
        "${street1} ${street2?:''} ${zipCode}"
    }
}

*/