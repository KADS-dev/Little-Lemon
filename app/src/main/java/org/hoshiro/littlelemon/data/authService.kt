package org.hoshiro.littlelemon.data

import android.app.Activity
import android.content.Context
import android.net.Credentials
import android.util.Log
import androidx.compose.material3.TimePicker
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.credentials.GetCredentialRequest
import com.google.android.gms.tasks.Task

import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.auth

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthService @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    suspend fun login(email:String, password:String): FirebaseUser? {
        return firebaseAuth.signInWithEmailAndPassword(email, password).await().user
    }

//
//    fun loginWithPhone(
//        phoneNumber: String,
//        activity: Activity,
//        callback: OnVerificationStateChangedCallbacks
//    ) {
//
//        val options = PhoneAuthOptions
//            .newBuilder(firebaseAuth)
//            .setPhoneNumber(phoneNumber)
//            .setTimeout(60L, TimeUnit.SECONDS)
//            .setActivity(activity)
//            .setCallbacks(callback)
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }

//    suspend fun verifyCode(verificationCode: String, phoneCode: String): FirebaseUser? {
//        val credentials = PhoneAuthProvider.getCredential(verificationCode, phoneCode)
//        Log.i("KADSLOG", "CREDENTIALS CREATED")
//
//        return completeRegisterWithCredential(credentials)
//    }
//
//    private suspend fun completeRegisterWithCredential(
//        credential: AuthCredential,
//    ): FirebaseUser?{
//        Log.i("KADSLOG", "completeRegisterWithPhone")
//
//        return suspendCancellableCoroutine {cancellableContinuation ->
//            Log.i("KADSLOG", "suspendCancellableCoroutine")
//
//            firebaseAuth.signInWithCredential(credential)
//                .addOnSuccessListener{
//                    Log.i("KADSLOG", "cancellableContinuation.resume(it.user)")
//                    cancellableContinuation.resume(it.user)
//
//                }
//                .addOnFailureListener{
//
//                    Log.i("KADSLOG", "cancellableContinuation.resumeWithException(it)")
//                    Log.i("KADSLOG", "CREDENTIALS: "+ credential)
//
//                    cancellableContinuation.resumeWithException(it)
//                }
//        }
//    }
//
//    suspend fun completeRegisterWithPhoneVerification(credentials: PhoneAuthCredential) =
//        completeRegisterWithCredential(credentials)


    suspend fun register(emailText: String, passwordText: String): FirebaseUser?{
        return suspendCancellableCoroutine {  cancellableContinuation ->
            firebaseAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnSuccessListener {
                    val user: FirebaseUser? = it.user
                    cancellableContinuation.resume(user)
                }
                .addOnFailureListener{
                    cancellableContinuation.resumeWithException(it)
                }
        }

    }

    fun isUserLogged(): Boolean {
        return getCurrentUser() != null
    }
    fun logOut() {
        firebaseAuth.signOut()
    }

    private fun getCurrentUser() = firebaseAuth.currentUser
    /*** GOOGLE ***/

    suspend fun signInWithGoogle(idToken: String) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.signInWithCredential(firebaseCredential).await()
    }

    /*** GitHub ***/
//
//    suspend fun loginWithGitHub(activity: Activity): FirebaseUser? {
//        val provider = OAuthProvider.newBuilder("github.com").apply{
//            scopes = listOf("user:email")
//        }
//
//        return suspendCancellableCoroutine<FirebaseUser?> { cancellableContinuation ->
//            firebaseAuth.pendingAuthResult?.addOnSuccessListener {
//                cancellableContinuation.resume(it.user)
//            }?.addOnFailureListener{ e ->
//                cancellableContinuation.resumeWithException(e)
//            } ?: completeRegisterWithProvider(activity, provider.build(), cancellableContinuation)
//        }
//    }
//
//    private fun completeRegisterWithProvider(
//        activity: Activity,
//        provider: OAuthProvider,
//        cancellableContinuation: CancellableContinuation<FirebaseUser?>
//    ) {
//        firebaseAuth.startActivityForSignInWithProvider(activity, provider)
//            .addOnSuccessListener {
//                cancellableContinuation.resume(it.user)
//            }
//            .addOnFailureListener{
//                cancellableContinuation.resumeWithException(it)
//            }
//
//    }
//
//    /*** Anonymous ***/
//
//    suspend fun loginAnonymously(): FirebaseUser? {
//        return firebaseAuth.signInAnonymously().await().user
//    }
//

}