/*
 *   This program manages the database and supports the work processes
 *   of the Luethi-Peterson Camps association (http://luethipetersoncamps.org/).
 *
 *   Copyright (C) 2012 Jacques Fontignie, Pierre-Antoine Queloz
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

// Comparable to sort in the Person view
class Attendance implements Comparable {

    static belongsTo = [camp: CampYear, person: Person]
    PersonStatus status

    static constraints = {
        person()
        camp()
        status()
    }

    String toString() {
        return person.toString() + " in " + camp.toString()
    }
    
    String nameAndStatus() {
        return "${person} - ${status}"
    }
    
    String campAndStatus() {
        return "${camp} - ${status}"
    }
    
    int compareTo(obj) {
        
        int compareYear = camp.compareTo(obj.camp)
        if (compareYear != 0) {
            return compareYear
        }
        int cmpStat = compareStatus(obj)
        if (cmpStat != 0) {
            return cmpStat
        }
        return person.lastName.compareTo(obj.person.lastName) ?:
            ( person.firstName.compareTo(obj.person.firstName) ?:
                (person.birthDay <=> obj.person.birthDay)
            )
        
    }
    
    int compareStatus(obj) {
        return rank(status) - rank(obj.status)
    }
    
    int rank(PersonStatus s) {
        switch (s) {
            case PersonStatus.Director : return 1
            case PersonStatus.CoDirector : return 2
            case PersonStatus.DITraining : return 3
            case PersonStatus.COuncellor : return 4
            case PersonStatus.CAmper : return 5
        }
        return 6
    }
}

