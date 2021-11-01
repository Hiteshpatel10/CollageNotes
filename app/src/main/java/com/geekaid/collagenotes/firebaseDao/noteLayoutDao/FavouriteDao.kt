package com.geekaid.collagenotes.firebaseDao.noteLayoutDao

import com.geekaid.collagenotes.model.FileUploadModel
import com.geekaid.collagenotes.viewmodel.FavouriteViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber

fun favouriteDao(course: FileUploadModel) {

    val db = Firebase.firestore
    val auth = Firebase.auth

    db.collection("Users").document(auth.currentUser?.email.toString())
        .collection("Favourite").document(course.fileUploadPath).get()
        .addOnSuccessListener { document ->

            if (document.exists()) {
                db.collection("Users").document(auth.currentUser?.email.toString())
                    .collection("Favourite").document(course.fileUploadPath).delete()

                db.collection("courses").document(course.course)
                    .collection(course.branch).document(course.subject)
                    .collection("notes").document(course.fileUploadPath)
                    .update("fav", false)

                Timber.i("delete")

            } else {
                db.collection("courses").document(course.course)
                    .collection(course.branch).document(course.subject)
                    .collection("notes").document(course.fileUploadPath)
                    .update("fav", true)

                course.fav = !course.fav
                db.collection("Users").document(auth.currentUser?.email.toString())
                    .collection("Favourite").document(course.fileUploadPath).set(course)
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