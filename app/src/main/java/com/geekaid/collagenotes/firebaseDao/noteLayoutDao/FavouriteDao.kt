package com.geekaid.collagenotes.firebaseDao.noteLayoutDao

import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.viewmodel.FavouriteViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber

fun favouriteDao(course: FileUploadModel) {

    val db = Firebase.firestore
    val currentUser = Firebase.auth.currentUser!!

    val noteRef = db.collection("courses").document(course.course)
        .collection(course.branch).document(course.subject)
        .collection("notes").document(course.fileUploadPath)

    val favouriteRef = db.collection("Users").document(currentUser.email.toString())
        .collection("Favourite").document(course.fileUploadPath)

    favouriteRef.get()
        .addOnSuccessListener { document ->

            if (document.exists()) {
                favouriteRef.delete()

                noteRef.update("favourite", FieldValue.arrayRemove(currentUser.email))

                Timber.i("delete")

            } else {
                noteRef.update("favourite", FieldValue.arrayUnion(currentUser.email))

//                course.fav = !course.fav
                course.favourite.add(currentUser.email.toString())
                favouriteRef.set(course)
                Timber.i("Added")
            }
        }

}

fun favouriteDaoFetch(favViewModel: FavouriteViewModel) {

    val db = Firebase.firestore
    val auth = Firebase.auth
    val favouriteList: ArrayList<FileUploadModel> = arrayListOf()

    db.collection("Users").document(auth.currentUser?.email.toString())
        .collection("Favourite").addSnapshotListener { value, error ->
            if (error != null)
                Timber.e(error.message)


            for (dc: DocumentChange in value?.documentChanges!!) {
                if (dc.type == DocumentChange.Type.ADDED) {
                    favouriteList.add(dc.document.toObject(FileUploadModel::class.java))
                }
            }
            favViewModel.favouriteList.value = favouriteList

        }
}