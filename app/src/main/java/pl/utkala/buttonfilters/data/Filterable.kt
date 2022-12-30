package pl.utkala.buttonfilters.data

import pl.utkala.buttonfilters.model.Job
import pl.utkala.buttonfilters.model.Person

interface Filterable<T> {
    fun filter(list: List<T>): List<T>
}

sealed class FilterablePerson() : Filterable<Person> {
    var isEnabled: Boolean = true

    class PersonNameFilter(condition: String) : FilterablePerson() {
        var condition = condition
            set(value) {
                isEnabled = value.isNotBlank()
                field = value
            }

        override fun filter(list: List<Person>): List<Person> {
            return if (isEnabled)
                list.filter { it.name.contains(condition) }
            else
                list
        }
    }

    class PersonSurnameFilter(condition: String) : FilterablePerson() {
        var condition = condition
            set(value) {
                isEnabled = value.isNotBlank()
                field = value
            }


        override fun filter(list: List<Person>): List<Person> {
            return if (isEnabled)
                list.filter { it.surname.contains(condition) }
            else
                list
        }
    }

    class PersonJobsFilter(condition: List<Job>?) : FilterablePerson() {
        var condition = condition
            set(value) {
                isEnabled = value?.isEmpty() == false
                field = value
            }

        override fun filter(list: List<Person>): List<Person> {
            return if (isEnabled) {
                list.filter { condition!!.contains(it.job) }
            } else {
                list
            }
        }
    }

    class UnemployedPersonFilter(state: Boolean) : FilterablePerson() {
        init {
            isEnabled = state
        }

        override fun filter(list: List<Person>): List<Person> {
            return if (isEnabled) {
                list.filter { it.job == null }
            } else {
                list
            }
        }
    }
}




