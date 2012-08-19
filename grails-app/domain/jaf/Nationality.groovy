package jaf

import grails.plugins.countries.Country

class Nationality {

    static belongsTo = [person:Person]

    Country country

    static constraints = {
    }

    String toString() {
        country.key
    }
}

