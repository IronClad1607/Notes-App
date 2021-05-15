package com.ironclad.notesapp.data.firebase.source

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject

class FirebaseSource @Inject constructor(private val firebaseAuth: FirebaseAuth) {
    fun signInWithGoogle(account: GoogleSignInAccount) =
        firebaseAuth.signInWithCredential(GoogleAuthProvider.getCredential(account.idToken, null))
}