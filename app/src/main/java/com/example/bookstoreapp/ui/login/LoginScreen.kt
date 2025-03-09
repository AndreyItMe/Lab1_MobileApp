package com.example.bookstoreapp.ui.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookstoreapp.R
import com.google.firebase.appcheck.internal.util.Logger.TAG
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.sign

@Composable
fun LoginScreen() {
    //val auth = Firebase.auth

    val emailState = remember {
        mutableStateOf("")
    }

    val passwordState = remember {
        mutableStateOf("")
    }

    Image(
        painter = painterResource(
        id = R.drawable.book_store_bg),
        contentDescription = "BG",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    //Log.d("MyLog", "User email: ${auth.currentUser?.email}")
    Column(
        modifier = Modifier.fillMaxSize().padding(
            start = 40.dp, end = 40.dp
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.internet_explorer_logo_2005),
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "BookStore App",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            fontFamily = FontFamily.Serif
        )

        Spacer(modifier = Modifier.height(15.dp))

        RoundedCornerTextField(
            text = emailState.value,
            label = "Email"
        ){
            emailState.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = passwordState.value,
            label = "Password"
        ){
            passwordState.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        LoginButton(text = "Sign In"){

        }
        LoginButton(text = "Sign Up"){

        }
    }
}

private fun signUp(auth: FirebaseAuth, email: String, password: String){
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener{ task ->
            if (task.isSuccessful) {
            //if (it.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("MyLog", "createUserWithEmail:success")
                val user = auth.currentUser
                //updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Log.d("MyLog", "createUserWithEmail:failure", task.exception)
                /*
                Toast.makeText(
                    baseContext,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT,
                ).show()
                 */
                //updateUI(null)
            }
        }
}
private fun signIn(auth: FirebaseAuth, email: String, password: String){
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener{ task ->
            if (task.isSuccessful) {
                //if (it.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("MyLog", "Sign in:success")
                val user = auth.currentUser
                //updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Log.d("MyLog", "Sing in:failure", task.exception)
                /*
                Toast.makeText(
                    baseContext,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT,
                ).show()
                 */
                //updateUI(null)
            }
        }
}

private fun signOut(auth: FirebaseAuth){
    auth.signOut()
    Log.d("MyLog", "Sign Out: success")
}

private fun deleteAccount(auth: FirebaseAuth, email: String, password: String){
    val credential = EmailAuthProvider.getCredential(email, password)
    auth.currentUser?.reauthenticate(credential)?.addOnCompleteListener {
        if(it.isSuccessful){
            auth.currentUser?.delete()?.addOnCompleteListener {
                if(it.isSuccessful){
                    Log.d("MyLog", "delete Account: success")
                } else
                {
                    Log.d("MyLog", "delete Account: failure")
                }
            }
        }else{
            Log.d("MyLog", "reauthenticate: failure")
        }
    }


}