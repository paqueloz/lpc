import jaf.Attendance
import jaf.Camp
import jaf.CampYear
import jaf.ContactMode;
import jaf.ContactType;
import jaf.Gender;
import jaf.PersonStatus;
import jaf.Nationality
import jaf.Person;
import jaf.Address;
import jaf.Contact;
import jaf.LanguageLevel;
import jaf.Level;
import jaf.PersonStatus;
import jaf.Staff
import jaf.TestDataController;

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
            // Using a populated DB now. Call controller if needed...
            // TestDataController.populateDb()
        }
        
    }
    def destroy = {
    }
}

