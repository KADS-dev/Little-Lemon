package org.hoshiro.littlelemon.data.online

import android.util.Log

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume

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

//
//    fun registerUser(email: String, password: String) {
//        viewModelScope.launch {
//
//        }
//    }


    suspend fun register(emailText: String, passwordText: String): FirebaseUser?{
        return suspendCancellableCoroutine {  cancellableContinuation ->

                firebaseAuth.createUserWithEmailAndPassword(emailText, passwordText)
                    .addOnSuccessListener {
                        val user: FirebaseUser? = it.user
                        cancellableContinuation.resume(user)
                    }
                    .addOnFailureListener{
                        Log.i("ERROR", "firebaseAuth.register: " + it.message.toString() )
                        //cancellableContinuation.resumeWithException(it)
                        //cancellableContinuation.resume (user)
                        cancellableContinuation.resume (null)
                    }

        }

    }

    fun isUserLogged(): Boolean {
        return getCurrentUser() != null
    }


    fun logOut() {
        firebaseAuth.signOut()
    }

    fun getCurrentUser() = firebaseAuth.currentUser?.email
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