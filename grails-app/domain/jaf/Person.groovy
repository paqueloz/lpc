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

    String preferences
    Gender gender

    boolean rejected
    PersonStatus status     // last (highest?) attendance status
                            // TODO search feature (by status)
                            // TODO easy way to list attendances with role

    boolean newClient       // TODO ASK Jacques for the purpose

    Date dateCreated
    Date lastUpdated

    static hasMany = [contacts: Contact, attendances: Attendance,
    	languages: LanguageLevel, addresses: Address, 
    	nationalities: Nationality]
	// Test de synchonisation gIT (Jacques Flumet lignes de commentaires)
	// .. rappel .... Il faudra  ne pointer que sur une seule adresse


    static constraints = {
        firstName(blank: false)
        lastName(blank: false)
        birthDay()
        deathDay(nullable: true)
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

// TODO prevent value Invited in Person
enum PersonStatus {
    Friend, CoDirector, COuncellor, CAmper, DITraining, Invited, Director, UNdefined
}