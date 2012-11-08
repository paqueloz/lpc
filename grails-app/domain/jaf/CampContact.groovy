package jaf

class CampContact extends Contact {

    static belongsTo = [ camp : Camp ]
    
    static constraints = {
    }
}
