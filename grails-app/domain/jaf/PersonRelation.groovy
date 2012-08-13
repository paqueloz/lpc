package jaf

class PersonRelation {

    RelationShip relationShip

    Person pers1
    Person pers2


    static constraints = {
        pers1()
        relationShip(nullable: false)
        pers2()
    }
}

// FIXME comment on gère les réciproques?

enum RelationShip {
    mother, father,  // [from son, daughter]
    sister, brother

    //TODO fill the list of nationalities
}

