package jaf

class PersonRelation {

    RelationShip relationShip

    Person pers1
    Person pers2

    String comment

    static constraints = {
        pers1()
        relationShip(nullable: false)
        pers2()
        comment(nullable : true)
    }
}

// e.g pers1 is *mother* of pers2

enum RelationShip {
    mother, father
}

