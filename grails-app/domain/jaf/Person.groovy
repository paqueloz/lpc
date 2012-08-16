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

class Person {

    String firstName
    String lastName

    Date birthDay
    Date deathDay

    //Nationality nationality //TODO convert it into an enumeration

    String preferences
    Gender gender

    boolean rejected
    LastStatus status

    boolean newClient //TODO ASK jacques for the purpose

    Date dateCreated
    Date lastUpdated

    //Person father
    //Person mother

    static hasMany = [contacts: Contact, attendances: Attendance,
    	languages: LanguageLevel, addresses: Address, 
    	nationalities: Nationality]


    static constraints = {
        firstName(blank: false)
        lastName(blank: false)
        // nationality()
        birthDay()
        deathDay(nullable: true)
        //father(nullable: true)
        //mother(nullable: true)
        preferences(nullable: true)
    }

    static searchable = {
        only = ['firstName','lastName','preferences']
    }

    def String toString() {
        return firstName + " " + lastName
    }
}


enum Gender {
    MALE, FEMALE, UNdefined
}

enum LastStatus {

    Friend, CoDirector, COuncellor, CAmper, DITraining, Invited, Director,  UNdefined

}