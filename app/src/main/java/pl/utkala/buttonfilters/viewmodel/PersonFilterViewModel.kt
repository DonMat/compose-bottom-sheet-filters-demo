package pl.utkala.buttonfilters.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import pl.utkala.buttonfilters.data.FilterablePerson
import pl.utkala.buttonfilters.model.Job
import pl.utkala.buttonfilters.model.Person

class PersonFilterViewModel : ViewModel() {

    private val people: List<Person> = listOf<Person>(
        Person("E0001", "John", "Doe", Job.PROGRAMMER),
        Person("E0002", "Ellen", "Cunningham", Job.PROGRAMMER),
        Person("E0003", "Carmen", "Walker", Job.TESTER),
        Person("E0004", "Mike", "Walker", Job.PROGRAMMER),
        Person("E0005", "Edgar", "Bourn", Job.WEB_DEVELOPER),
        Person("E0006", "Richard", "Robson", Job.WEB_DEVELOPER),
        Person("E0007", "Ralph", "Poe", Job.WEB_DEVELOPER),
        Person("E0008", "Max", "Smith", null)
    )

    private val _filteredList = MutableStateFlow<List<Person>>(people)
    val list: StateFlow<List<Person>> = _filteredList

    private var appliedFilters = mutableListOf<FilterablePerson>()

    private val _filterState = MutableStateFlow<FilterablePerson?>(null)
    val filterState = _filterState

    fun openFilter(filter: FilterablePerson) {
        _filterState.update {
            appliedFilters.find { it.javaClass == filter.javaClass } ?: filter
        }
    }

    fun closeFilter() {
        _filterState.update { null }
    }

    fun applyFilter(filter: FilterablePerson) {
        appliedFilters.removeIf { it.javaClass == filter.javaClass }
        appliedFilters.add(filter)
        closeFilter()

        _filteredList.update {
            appliedFilters.fold(people) { list, filter ->
                filter.filter(list)
            }
        }
    }
}