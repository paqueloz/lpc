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
 *  Class used to register involvement of a Person in a Camp
 * 
 */
class Staff {

    static belongsTo = [ person : Person, camp : Camp ]
    
    String comment
    Date startDate
    Date endDate
    
    static constraints = {
        person()
        camp()
        comment(nullable: true)
        startDate(nullable: true)
        endDate(nullable: true)
    }

    String toString() {
        return camp.toString() + otherFields()
    }

    String toStringForCamp() {
        return person.toString() + otherFields()
    }

    String otherFields() {
        String result
        if (startDate) {
            Calendar cal = new GregorianCalendar()
            cal.setTime(startDate)
            int startYear = cal.get(Calendar.YEAR)
            if (endDate) {
                cal.setTime(endDate)
                int endYear = cal.get(Calendar.YEAR)
                result += " (${startYear}-${endYear})"
            } else {
                result += " (since ${startYear})"
            }
        }
        if (comment) {
            result += " ${comment}"
        }
        return result
    }
    
}
