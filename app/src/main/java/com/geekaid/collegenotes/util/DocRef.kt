package com.geekaid.collegenotes.util

import com.geekaid.collegenotes.model.FileUploadModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference

fun noteRef(note: FileUploadModel, firestore: FirebaseFirestore): DocumentReference {

    return firestore.collection("courses").document(note.course)
        .collection(note.branch).document(note.subject)
        .collection(note.noteType).document(note.fileInfo.fileUploadPath)

}

fun noteRefPath(note: FileUploadModel): String {

    return "courses/${note.course}/${note.branch}/${note.subject}/${note.noteType}/${note.fileInfo.fileUploadPath}"
}

fun noteStorageRef(note: FileUploadModel, storageRef: StorageReference): StorageReference {

    return storageRef.child("courses").child(note.course).child(note.branch)
        .child(note.subject).child(note.noteType).child(note.fileInfo.fileUploadPath)
}

fun noteFavRef(
    note: FileUploadModel,
    favSpaceName: String = "fav1",
    firestore: FirebaseFirestore,
    currentUser: FirebaseUser
): DocumentReference {

    return firestore.collection("users").document(currentUser.email.toString())
        .collection(favSpaceName).document(note.noteType)
}

fun noteReportRef(note: FileUploadModel, firestore: FirebaseFirestore): DocumentReference {

    return firestore.collection("reportedCourses").document(note.course)
        .collection(note.branch).document(note.subject)
        .collection(note.noteType).document(note.fileInfo.fileUploadPath)
}

fun noteReportReviewRef(note: FileUploadModel, firestore: FirebaseFirestore): DocumentReference {

    return firestore.collection("reviewReported").document(note.course)
        .collection(note.branch).document(note.fileInfo.fileUploadPath + note.date)
}

fun userDetailRef(firestore: FirebaseFirestore, currentUser: FirebaseUser): DocumentReference {
    return firestore.collection("users").document(currentUser.email.toString())
        .collection("userData").document("userInfo")
}

fun userDetailRef(firestore: FirebaseFirestore, currentUser: String): DocumentReference {
    return firestore.collection("users").document(currentUser)
        .collection("userData").document("userInfo")
}

fun userUploadRef(
    note: FileUploadModel,
    firestore: FirebaseFirestore,
    email: String
): DocumentReference {

     return firestore.collection("users").document(email)
        .collection("userData").document("uploads")
        .collection(note.noteType).document(note.fileInfo.fileUploadPath)

}

fun userProfileRef(storageRef: StorageReference, currentUser: FirebaseUser): StorageReference {
    return storageRef.child("userProfile").child(currentUser.email.toString())
}

fun filterDataRef(currentUser: FirebaseUser, firestore: FirebaseFirestore): DocumentReference {

    return firestore.collection("users").document(currentUser.email.toString())
        .collection("userData").document("filterData")
}

