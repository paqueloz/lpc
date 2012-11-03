/*
 * This program is intended to help the Luethi-Peterson Camps association
 *     to help them store and manage their users
 *
 *     Copyright (C) 2012 Jacques Fontignie
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
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

    def String toString() {
        return person.toString() + " in " + camp.toString()
    }
    
    int compareTo(obj) {
        int compareYear = camp.compareTo(obj.camp)
        if (compareYear != 0) {
            return compareYear
        }
        person.lastName.compareTo(obj.person.lastName)
        // FIXME status 1) director > co-dir > dir in training 2) counselor 3) campers 4) invited
        // FIXME start of CC vient après 
    }
}

