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

import java.util.SortedSet;

class CampYear implements Comparable {

    Date beginDate, endDate
    
    static belongsTo = [ camp : Camp ]
    
    SortedSet attendances
    static hasMany = [ attendances : Attendance ]
    
    int year

    static constraints = {
        camp()
        year(min: 1900)
        beginDate(nullable: true)
        endDate(nullable: true)
        attendances()
    }

    def String toString() {
        return "$camp ($year)"
    }
    
    int compareTo(obj) {
        // some camps come after others
        int comparison = camp.compareTo(obj.camp)
        if (comparison) {
            return comparison
        }
        // use year if there is no order in camps
        year - obj.year
    }
}
