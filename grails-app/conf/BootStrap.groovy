import grails.util.Environment
import jaf.SecRole
import jaf.SecUser
import jaf.SecUserSecRole

// import org.codehaus.groovy.grails.commons.ConfigurationHolder

class BootStrap {
    
    def grailsApplication

    def init = { servletContext ->

        def currentEnv = Environment.current
        
        /*
         * Make sure the default roles exist
         */
        def readerRole = SecRole.findByAuthority('ROLE_READER') ?: new SecRole(authority: 'ROLE_READER').save(failOnError: true)
        def userRole = SecRole.findByAuthority('ROLE_USER') ?: new SecRole(authority: 'ROLE_USER').save(failOnError: true)
        def adminRole = SecRole.findByAuthority('ROLE_ADMIN') ?: new SecRole(authority: 'ROLE_ADMIN').save(failOnError: true)
        
        /*
         * If there is no "admin", create it
         */
        def adminUser = SecUser.findByUsername('admin') ?: new SecUser(
                username: 'admin',
                password: currentEnv == Environment.PRODUCTION ? grailsApplication.config.sec.adm.pass : "abcd",
                enabled: true).save(failOnError: true)

        /*
         * Make sure that "admin" has the admin role
         */
        if (!adminUser.authorities.contains(adminRole)) {
            SecUserSecRole.create adminUser, readerRole
            SecUserSecRole.create adminUser, userRole
            SecUserSecRole.create adminUser, adminRole
        }
        
        if (currentEnv == Environment.DEVELOPMENT) {
            // Using a populated DB now. Call controller if needed...
            // TestDataController.populateDb()
        }
        
    }
    def destroy = {
    }
}

