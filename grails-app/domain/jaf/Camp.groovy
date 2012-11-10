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

import grails.plugins.countries.Country

class Camp implements Comparable {

    String location     // name of the house
    String street1
    String street2
    String zipCode
    String city
    Country country
    SortedSet years    
    static hasMany = [ years : CampYear, staff : Staff, contacts : CampContact ]
    
    // TODO add picture

    static constraints = {
        location()
        street1(nullable: true)
        street2(nullable: true)
        zipCode(nullable: true)
        city(nullable: true)
        country(nullable: true)
    }
    
    static searchable = {
        except = 'years'
    }

    String toString() {
        return location
    }
    
    String toStringForSearch() {
        return "${location} ${city?:''} ${country?country.key:''}"
    }
    
    int compareTo(obj) {
        if (location!="StartOfCC" && obj.location!="StartOfCC") {
            return 0
        } else if (location == obj.location) {
            return 0
        } else if (location == "StartOfCC") {
            return 1
        }
        return -1
    }
}
