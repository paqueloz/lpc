package jaf

class Nationalities {

	// FIXME est-ce qu'on veut effacer la nationalité quand on efface la person?
    static belongsTo = [person:Person]

	// FIXME étrange d'utiliser java.util.Locale...
    Locale  nationality

    static constraints = {
    }

    def String toString() {
        return  nationality
    }

}

