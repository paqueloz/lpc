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

import grails.plugins.countries.Country

class Address {

    static belongsTo = [person:Person]

    String street1
    String street2
    String zipCode
    String city
    Country country
    boolean active

    Date dateCreated
    Date lastUpdated

    static constraints = {
        person(nullable: false)
        street1()
        street2(nullable: true)
        zipCode(nullable: true)
        city(nullable: true)
        country(nullable: true)
        active()
    }

    static searchable = {
        except = ['dateCreated','lastUpdated','country']
    }
    
    def String toString() {
        "${street1} ${street2?:''} ${zipCode}"
    }
}
