package jaf


/**
 *  Class used to register involvement of a Person in a Camp
 * 
 */
class Staff {

    static belongsTo = [ person : Person, camp : Camp ]
    
    String comment
    Date startDate
    Date endDate
    
    static constraints = {
        comment(nullable: true)
        startDate(nullable: true)
        endDate(nullable: true)
    }
    
    // TODO remove person selector (similar to address)
    // TODO provide a similar string to display in Camp
    String toString() {
        String result = camp.toString();
        if (startDate) {
            Calendar cal = new GregorianCalendar()
            cal.setTime(startDate)
            int startYear = cal.get(Calendar.YEAR)
            if (endDate) {
                cal.setTime(endDate)
                int endYear = cal.get(Calendar.YEAR)
                result += " (${startYear}-${endYear})"
            } else {
                result += " (since ${startYear})"
            }
        }
        if (comment) {
            result += " ${comment}"
        }
        return result;
    }
}
