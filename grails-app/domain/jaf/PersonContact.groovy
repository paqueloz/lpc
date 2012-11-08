package jaf

class PersonContact extends Contact {

    static belongsTo = [ person : Person ]
        
    static constraints = {
    }
}
