/*
 *   This program manages the database and supports the work processes
 *   of the Luethi-Peterson Camps association (http://luethipetersoncamps.org/).
 *
 *   Copyright (C) 2012 Pierre-Antoine Queloz
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
    childOf, livesWith, parentOf
}

