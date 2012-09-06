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

import grails.plugins.countries.Country

class Camp {

    String location     // name of the house
    String street1
    String street2
    String zipCode
    String city
    Country country
    Contact contact     // for phone number
    static hasMany = [ years : CampYear, staff : Staff ]
    
    // TODO add picture

    static constraints = {
        location()
        street1(nullable: true)
        street2(nullable: true)
        zipCode(nullable: true)
        city(nullable: true)
        country(nullable: true)
        contact(nullable: true)
    }
    
    static searchable = {
        except = 'years'
    }

    def String toString() {
        return location
    }
}
