package com.ironclad.notesapp.data.firebase.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.ironclad.notesapp.data.firebase.source.FirebaseSource
import javax.inject.Inject

class FirebaseRepository @Inject constructor(private val firebareSource: FirebaseSource) {
    fun signInWithGoogle(account: GoogleSignInAccount) = firebareSource.signInWithGoogle(account)
}