package jaf

class Nationality {

    static belongsTo = [person:Person]

    String country

    static constraints = {
    }

    def String toString() {
        return country
    }

}

