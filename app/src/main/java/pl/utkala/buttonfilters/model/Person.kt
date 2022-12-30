package pl.utkala.buttonfilters.model

enum class Job {
    PROGRAMMER, TESTER, WEB_DEVELOPER
}

data class Person(val id: String, val name: String, val surname: String, val job: Job?)


val people = listOf<Person>(
    Person("E0001", "John", "Doe", Job.PROGRAMMER),
    Person("E0002", "Ellen", "Cunningham", Job.PROGRAMMER),
    Person("E0003", "Carmen", "Walker", Job.TESTER),
    Person("E0004", "Mike", "Walker", Job.PROGRAMMER),
    Person("E0005", "Edgar", "Bourn", Job.WEB_DEVELOPER),
    Person("E0006", "Richard", "Robson", Job.WEB_DEVELOPER),
    Person("E0007", "Ralph", "Poe", Job.WEB_DEVELOPER),
    Person("E0008", "Max", "Smith", null),


//    Person("E0009", "John", "Doe", Job.PROGRAMMER),
//    Person("E0010", "Ellen", "Cunningham", Job.PROGRAMMER),
//    Person("E0011", "Carmen", "Walker", Job.TESTER),
//    Person("E0012", "Mike", "Walker", Job.PROGRAMMER),
//    Person("E0013", "Edgar", "Bourn", Job.WEB_DEVELOPER),
//    Person("E0014", "Richard", "Robson", Job.WEB_DEVELOPER),
//    Person("E0015", "Ralph", "Poe", Job.WEB_DEVELOPER),
//    Person("E0016", "Max", "Smith", null),
//    Person("E0017", "John", "Doe", Job.PROGRAMMER),
//    Person("E0018", "Ellen", "Cunningham", Job.PROGRAMMER),
//    Person("E0019", "Carmen", "Walker", Job.TESTER),
//    Person("E0020", "Mike", "Walker", Job.PROGRAMMER),
//    Person("E0021", "Edgar", "Bourn", Job.WEB_DEVELOPER),
//    Person("E0022", "Richard", "Robson", Job.WEB_DEVELOPER),
//    Person("E0023", "Ralph", "Poe", Job.WEB_DEVELOPER),
//    Person("E0024", "Max", "Smith", null),
//    Person("E0025", "John", "Doe", Job.PROGRAMMER),
//    Person("E0026", "Ellen", "Cunningham", Job.PROGRAMMER),
//    Person("E0027", "Carmen", "Walker", Job.TESTER),
//    Person("E0028", "Mike", "Walker", Job.PROGRAMMER),
//    Person("E0029", "Edgar", "Bourn", Job.WEB_DEVELOPER),
//    Person("E0030", "Richard", "Robson", Job.WEB_DEVELOPER),
//    Person("E0031", "Ralph", "Poe", Job.WEB_DEVELOPER),
//    Person("E0032", "Max", "Smith", null),
)