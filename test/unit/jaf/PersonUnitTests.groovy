package jaf

import static org.junit.Assert.*
import java.text.SimpleDateFormat
import grails.plugins.countries.*


import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class PersonUnitTests {

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    void testToString() {
        // fail "Implement me"
        Person p = new Person(  firstName   : "Pierre-Antoine",
            lastName    : "Queloz",
            birthDay    : new SimpleDateFormat("yyyy-MM-dd").parse("1971-03-30"), // "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
            gender      : Gender.MALE,
            status      : PersonStatus.UNdefined,
            )
        assert p.lastName == "Queloz"
        assert p.toStringForSearch() == "Pierre-Antoine Queloz (1971-03-30)"
        Address a = new Address (   person  : p,
            street1 : "11 ch du Lac",
            zipCode : "1290",
            city    : "Versoix",
            // country : Country.findByShortKey("CH"),
            active  : true,
            )
        p.address = [ a ]
        def relist =  p.address.collect { Address it -> it }
        assert relist.size() == 1
        assert relist[0].city == "Versoix"
        assert p.toStringForSearch() == "Pierre-Antoine Queloz (1971-03-30) Versoix"
    }
}
