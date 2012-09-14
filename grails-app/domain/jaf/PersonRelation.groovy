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
        "${relationship} of ${other}"
    }
    
    static Relationship opposite(Relationship r, Person p) {
        switch(r) {
            case "mother":
            case "father": 
                return (p.getGender() == Gender.MALE) ? Relationship.son : Relationship.daughter 
            case "son":
            case "daughter":
                return (p.getGender() == Gender.MALE) ? Relationship.father : Relationship.mother
        }
    }
}

// e.g person is *mother* of other

enum Relationship {
    mother, father, son, daughter
}

