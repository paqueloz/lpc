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
            case "motherOf":
            case "fatherOf": 
                return (p.getGender() == Gender.MALE) ? Relationship.sonOf : Relationship.daughterOf 
            case "sonOf":
            case "daughterOf":
                return (p.getGender() == Gender.MALE) ? Relationship.fatherOf : Relationship.motherOf
            case "livesWith":
                return r
        }
    }
}

// e.g person is *motherOf* of other
// better if the person referenced by livesWith has an address

enum Relationship {
    motherOf, fatherOf, sonOf, daughterOf, livesWith
}

