@file:OptIn(ExperimentalMaterial3Api::class)

package at.thomasgorke.android.composeexample.ui.basic

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import at.thomasgorke.android.composeexample.R
import at.thomasgorke.android.composeexample.ui.base.BackTopAppBar
import at.thomasgorke.android.composeexample.ui.base.TopAppBarContainer
import at.thomasgorke.android.composeexample.ui.destinations.BasicStateHoistingScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun BasicTextFieldScreen(
    navigator: DestinationsNavigator
) {
    TopAppBarContainer(
        topAppBar = { BasicTextFieldScreenTopAppBar(navigator) }
    ) {
        TextFields()

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { navigator.navigate(BasicStateHoistingScreenDestination) }) {
            Text(text = "Go to state hoisting")
        }
    }
}

@Composable
fun TextFields() {
    val input1 = remember {
        mutableStateOf("")
    }

    var input2 by remember {
        mutableStateOf("")
    }

    Column {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            value = input1.value,
            onValueChange = { newValue ->
                input1.value = newValue
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            value = input2,
            onValueChange = { newValue ->
                // INFO: If you want to prevent entering specific letters (e.g. regex) you have to filter the new input and set the value accordingly
                input2 = newValue
            },
            placeholder = {
                Text(text = "Placeholder")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = null)
            }
        )


    }
}

@Composable
fun BasicTextFieldScreenTopAppBar(navigator: DestinationsNavigator) {
    BackTopAppBar(title = stringResource(id = R.string.tab_label_basic_text_fields)) {
        navigator.navigateUp()
    }
}