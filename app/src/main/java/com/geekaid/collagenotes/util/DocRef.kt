package com.geekaid.collagenotes.util

import com.geekaid.collagenotes.model.FileUploadModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference

fun noteRef(note: FileUploadModel, firestore: FirebaseFirestore): DocumentReference {

    return firestore.collection("courses").document(note.course)
        .collection(note.branch).document(note.subject)
        .collection("notes").document(note.fileInfo.fileUploadPath)

}

fun noteStorageRef(note: FileUploadModel, storageRef: StorageReference): StorageReference {

    return storageRef.child("courses").child(note.course).child(note.branch)
        .child(note.subject).child("notes").child(note.fileInfo.fileUploadPath)
}

fun noteFavRef(
    note: FileUploadModel,
    firestore: FirebaseFirestore,
    currentUser: FirebaseUser
): DocumentReference {

    return firestore.collection("Users").document(currentUser.email.toString())
        .collection("Favourite").document(note.fileInfo.fileUploadPath)
}

fun noteReportRef(note: FileUploadModel, firestore: FirebaseFirestore): DocumentReference {

    return firestore.collection("reportedCourses").document(note.course)
        .collection(note.branch).document(note.subject)
        .collection("notes").document(note.fileInfo.fileUploadPath)
}

fun noteReportReviewRef(note: FileUploadModel, firestore: FirebaseFirestore): DocumentReference {

    return firestore.collection("reviewReported").document(note.course)
        .collection(note.branch).document(note.fileInfo.fileUploadPath + note.date)
}