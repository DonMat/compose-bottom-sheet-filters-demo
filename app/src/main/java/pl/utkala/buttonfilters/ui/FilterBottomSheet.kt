package pl.utkala.buttonfilters.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun BottomSheetTitle(title: String, onConfirm: () -> Unit, onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier.clickable {
                onDismiss()
            }
        )
        Text(
            text = title,
            style = MaterialTheme.typography.h6
        )
        Icon(
            imageVector = Icons.Default.Done,
            contentDescription = null,
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.clickable {
                onConfirm()
            }
        )
    }

    Divider(modifier = Modifier.height(1.dp))
}

@Composable
fun <T> CheckboxFilterDialog(title: String, selectedItems: List<T>, items: List<T>, onConfirm: (List<T>) -> Unit, onDismiss: () -> Unit) {
    val (selectedJobs, selectJob) = remember {
        mutableStateOf(selectedItems)
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        BottomSheetTitle(
            title,
            onDismiss = onDismiss,
            onConfirm = { onConfirm(selectedJobs) }
        )

        items.forEach { job ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedJobs.contains(job),
                    onCheckedChange = {
                        val list = selectedJobs.toMutableList()
                        if (selectedJobs.contains(job)) {
                            list.remove(job)
                        } else {
                            list.add(job)
                        }
                        selectJob(list)
                    })
                Text(
                    text = job.toString(),
                    modifier = Modifier.clickable {
                        val list = selectedJobs.toMutableList()
                        if (selectedJobs.contains(job)) {
                            list.remove(job)
                        } else {
                            list.add(job)
                        }
                        selectJob(list)
                    }
                )

            }

        }

    }
}

@Composable
fun TextFilterDialog(title: String, initialValue: String = "", onConfirm: (String) -> Unit, onDismiss: () -> Unit) {
    var filterValue by remember {
        mutableStateOf(initialValue)
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        BottomSheetTitle(
            title = title,
            onDismiss = onDismiss,
            onConfirm = { onConfirm(filterValue) }
        )

        OutlinedTextField(
            value = filterValue,
            onValueChange = { filterValue = it },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if (filterValue.isNotBlank()) {
                    Icon(Icons.Default.Clear,
                        contentDescription = "clear text",
                        modifier = Modifier
                            .clickable {
                                filterValue = ""
                            }
                    )
                }
            })
    }
}