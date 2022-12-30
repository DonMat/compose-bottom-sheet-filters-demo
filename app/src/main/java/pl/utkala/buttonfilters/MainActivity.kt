package pl.utkala.buttonfilters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import pl.utkala.buttonfilters.data.FilterablePerson
import pl.utkala.buttonfilters.model.Job
import pl.utkala.buttonfilters.model.Person
import pl.utkala.buttonfilters.ui.CheckboxFilterDialog
import pl.utkala.buttonfilters.ui.FilterButton
import pl.utkala.buttonfilters.ui.TextFilterDialog
import pl.utkala.buttonfilters.ui.theme.ButtonFiltersTheme
import pl.utkala.buttonfilters.viewmodel.PersonFilterViewModel

@OptIn(ExperimentalMaterialApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ButtonFiltersTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val vm = viewModel(PersonFilterViewModel::class.java)

                    val peopleList by vm.list.collectAsState()
                    val filterState by vm.filterState.collectAsState()

                    val coroutineScope = rememberCoroutineScope()
                    val bottomSheetState = rememberModalBottomSheetState(
                        initialValue = ModalBottomSheetValue.Hidden,
                        confirmStateChange = {
                            if (it == ModalBottomSheetValue.Hidden) {
                                vm.closeFilter()
                            }
                            it != ModalBottomSheetValue.HalfExpanded
                        }
                    )

                    val lazyListState = rememberLazyListState()
                    val scrollState = rememberScrollState()
                    var unemployedFilterState by rememberSaveable { mutableStateOf(false) }
                    var jobFilterState by rememberSaveable { mutableStateOf(false) }
                    var nameState by rememberSaveable { mutableStateOf(false) }
                    var surnameState by rememberSaveable { mutableStateOf(false) }

                    ModalBottomSheetLayout(
                        sheetState = bottomSheetState,
                        sheetContent = {
                            Column(modifier = Modifier.fillMaxSize()) {
                                when (filterState) {
                                    is FilterablePerson.PersonJobsFilter -> {
                                        val jobFilter = (filterState as FilterablePerson.PersonJobsFilter)
                                        CheckboxFilterDialog(
                                            title = getString(R.string.filter_jobs),
                                            selectedItems = jobFilter.condition ?: listOf(),
                                            items = Job.values().toList(),
                                            onConfirm = { jobList ->
                                                jobFilter.condition = jobList
                                                vm.applyFilter(jobFilter)
                                                jobFilterState = jobFilter.isEnabled
                                                coroutineScope.launch { bottomSheetState.hide() }
                                            },
                                            onDismiss = { coroutineScope.launch { bottomSheetState.hide() } }
                                        )
                                    }
                                    is FilterablePerson.PersonNameFilter -> {
                                        val nameFilter = filterState as FilterablePerson.PersonNameFilter
                                        TextFilterDialog(
                                            title = getString(R.string.filter_names),
                                            initialValue = nameFilter.condition,
                                            onConfirm = { filterString ->
                                                nameFilter.condition = filterString
                                                vm.applyFilter(nameFilter)
                                                nameState = nameFilter.isEnabled
                                                coroutineScope.launch { bottomSheetState.hide() }
                                            },
                                            onDismiss = {
                                                coroutineScope.launch { bottomSheetState.hide() }
                                            }
                                        )
                                    }
                                    is FilterablePerson.PersonSurnameFilter -> {
                                        val surnameFilter = filterState as FilterablePerson.PersonSurnameFilter

                                        TextFilterDialog(
                                            title = getString(R.string.filter_surnames),
                                            initialValue = surnameFilter.condition,
                                            onConfirm = { filterString ->
                                                surnameFilter.condition = filterString
                                                vm.applyFilter(surnameFilter)
                                                surnameState = surnameFilter.isEnabled
                                                coroutineScope.launch { bottomSheetState.hide() }
                                            },
                                            onDismiss = {
                                                coroutineScope.launch { bottomSheetState.hide() }
                                            }
                                        )
                                    }
                                    is FilterablePerson.UnemployedPersonFilter -> {
                                        val unemployedFilter = filterState as FilterablePerson.UnemployedPersonFilter
                                        unemployedFilter.isEnabled = !unemployedFilterState
                                        vm.applyFilter(unemployedFilter)

                                        unemployedFilterState = unemployedFilter.isEnabled

                                    }
                                    null -> {} // nothing to do
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.horizontalScroll(state = scrollState),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                FilterButton(selected = jobFilterState, onClick = {
                                    vm.openFilter(FilterablePerson.PersonJobsFilter(listOf()))
                                    coroutineScope.launch { bottomSheetState.show() }
                                }, modifier = Modifier.padding(8.dp)) {
                                    Text(text = getString(R.string.filter_jobs))
                                }

                                FilterButton(selected = unemployedFilterState, onClick = {
                                    vm.openFilter(FilterablePerson.UnemployedPersonFilter(unemployedFilterState))
                                }, modifier = Modifier.padding(8.dp)) {
                                    Text(text = getString(R.string.filter_unemployed))
                                }

                                FilterButton(selected = nameState, onClick = {
                                    vm.openFilter(FilterablePerson.PersonNameFilter(""))
                                    coroutineScope.launch { bottomSheetState.show() }
                                }, modifier = Modifier.padding(8.dp)) {
                                    Text(text = getString(R.string.filter_names))
                                }

                                FilterButton(selected = surnameState, onClick = {
                                    vm.openFilter(FilterablePerson.PersonSurnameFilter(""))
                                    coroutineScope.launch { bottomSheetState.show() }
                                }, modifier = Modifier.padding(8.dp)) {
                                    Text(text = getString(R.string.filter_surnames))
                                }
                            }

                            Divider(modifier = Modifier.height(1.dp))

                            LazyColumn(state = lazyListState) {
                                items(key = { it.id }, items = peopleList) { person ->
                                    PersonRow(person = person)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PersonRow(person: Person, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "${person.name} ${person.surname}")
        Spacer(modifier = Modifier.weight(1f))
        Text(text = person.job?.name ?: "")
    }

}

