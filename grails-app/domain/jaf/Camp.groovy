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

class Camp {

    String location     // name of the house
    String country      // TODO use grails.plugins.countries.Country
    // Address addressHouse TODO store an address that doesn't belong to a Person
    Contact contactHouse
    Person personHouse  // TODO several persons with different roles and a history
    static hasMany = [years : CampYear]

    static constraints = {
        String location
        country(nullable: true)
        //AddressHouse(nullable: true)    No signature of method: jaf.Camp.AddressHouse() is applicable for argument types: (java.util.LinkedHashMap) values: [[nullable:true]]
        contactHouse(nullable: true)
        personHouse(nullable: true)
    }
    
    static searchable = {
        except = 'years'
    }

    def String toString() {
        return location
    }
}
