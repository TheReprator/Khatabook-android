package com.reprator.khatabook_android.ui.login

import android.app.Activity
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.text.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun DefaultPreviewLogin() {
    LoginPage()
}

@Composable
fun LoginPage() {
    val textValue = remember { mutableStateOf(TextFieldValue()) }

    val submit: () -> Unit = {
        Log.e("Hi", "Hi")
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
                buttonClick.invoke()
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

            buttonClick.invoke()
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = 5.dp
    ) {
        Text(text = "Submit", modifier = Modifier.padding(6.dp))
    }
}
