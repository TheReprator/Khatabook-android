package com.reprator.khatabook_android.ui.login

import android.app.Activity
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.text.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreviewLogin() {
    val state = rememberScaffoldState()
    Scaffold(
        scaffoldState = state,
        bodyContent = {
            LoginPage(state)
        },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginPage(state: ScaffoldState) {
    val textValue = remember { mutableStateOf(TextFieldValue()) }

    val scope = rememberCoroutineScope()
    val submit: () -> Unit = {
        scope.launch {
            val error = when (textValue.value.text.length) {
                10 -> ""
                0 -> "Please enter phone number"
                in 0..9 -> "Phone number can't be less than 10"
                else -> "Phone number can't be greater than 10"
            }
            if (error.isNotEmpty()) {
                Log.d("AAAA", "Error!")
                when (state.snackbarHostState.showSnackbar(error, actionLabel = "Ok")) {
                    SnackbarResult.Dismissed -> Log.d("AAAA", "Dismissed")
                    SnackbarResult.ActionPerformed -> Log.d("AAAA", "Action!")
                }
            } else {
                Log.d("AAAA", "Success!")
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(15.dp),
        verticalArrangement = Arrangement.Center
    ) {
        MaterialTextInputComponent(textValue, submit)
        Spacer(modifier = Modifier.preferredHeight(16.dp))
        MaterialButtonComponent(submit)
    }
}

@Composable
fun MaterialTextInputComponent(textValue: MutableState<TextFieldValue>, buttonClick: () -> Unit) {
    OutlinedTextField(
        value = textValue.value,
        onValueChange = { textFieldValue -> textValue.value = textFieldValue },
        keyboardType = KeyboardType.Phone,
        imeAction = ImeAction.Done,
        label = { Text("Enter Your Phone Number") },
        placeholder = { Text(text = "9041866055") },
        onImeActionPerformed = { imeAction: ImeAction,
                                 softwareKeyboardController: SoftwareKeyboardController? ->
            if (imeAction == ImeAction.Done) {
                softwareKeyboardController?.hideSoftwareKeyboard()
                buttonClick()
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun MaterialButtonComponent(buttonClick: () -> Unit) {
    val context = ContextAmbient.current
    Button(
        onClick = {
            val imm: InputMethodManager =
                (context as Activity).getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            buttonClick()
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = 5.dp
    ) {
        Text(text = "Submit", modifier = Modifier.padding(6.dp))
    }
}