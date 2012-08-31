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
