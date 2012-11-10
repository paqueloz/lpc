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

import com.grailsrocks.functionaltest.*

class PersonListFunctionalTests extends BrowserTestCase {
    
    void testNotLoggedIn() {
        
        get("/person/list")
        assertStatus 200
        assertTitle "Login"
        
        // Here call get(uri) or post(uri) to start the session
        // and then use the custom assertXXXX calls etc to check the response
        //
        // get('/something')
        // assertStatus 200
        // assertContentContains 'the expected text'
    }
    
    void testPersonList() {
        // http://localhost:8080/jaf/j_spring_security_check?j_password=abcd&j_username=admin
        post("j_spring_security_check") {
            j_username = "admin"
            j_password = "abcd"
        }
        
        assertTitle "Welcome to Grails"
    }
}
