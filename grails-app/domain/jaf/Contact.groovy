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

import org.apache.commons.validator.EmailValidator

class Contact {

    Date dateCreated
    Date lastUpdated

    ContactType type
    ContactMode mode
    String value
    boolean active

    static constraints = {
        value(blank: false, validator: {val,obj ->
            if (obj.type == ContactType.EMAIL) return EmailValidator.instance.isValid(val)
            true
        })
    }

    String toString() {
        return "[" + mode + "] " + value +  " (" + type + ")"
    }

}

enum ContactType {
    PHONE, FAX, EMAIL, MOBILE, UNdefined
}

enum ContactMode {
    HOME, WORK, UNdefined
}


