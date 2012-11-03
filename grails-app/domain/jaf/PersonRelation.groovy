package jaf

/**
 * Uni-directional relationship between two persons
 * When a Person is deleted, the hasMany / belongsTo automatically deletes in one direction
 * A beforeDelete hook in Person deletes in the reverse direction
 * Bi-directional seems more difficult (e.g. for display)
 */
class PersonRelation {

    Person          person
    Relationship    relationship
    Person          other
    String          comment

    static belongsTo = [ person : Person, other : Person ]

    static constraints = {
        person()
        relationship(nullable: false)
        other()
        comment(nullable : true)
    }
    
    String toString() {
        "${relationship} ${other}"
    }
    
    static Relationship opposite(Relationship r, Person p) {
        switch(r) {
            case "parentOf":
                return Relationship.childOf 
            case "childOf":
                return Relationship.parentOf
            case "livesWith":
                return r
        }
    }
}

// e.g person is *parentOf* of other
// better if the person referenced by livesWith has an address

enum Relationship {
    parentOf, childOf, livesWith
}

