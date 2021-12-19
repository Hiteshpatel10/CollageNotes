package com.geekaid.collagenotes.util

import com.geekaid.collagenotes.model.FileUploadModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference

fun noteRef(note: FileUploadModel, firestore: FirebaseFirestore): DocumentReference {

    return firestore.collection("courses").document(note.course)
        .collection(note.branch).document(note.subject)
        .collection(note.noteType).document(note.fileInfo.fileUploadPath)

}

fun noteStorageRef(note: FileUploadModel, storageRef: StorageReference): StorageReference {

    return storageRef.child("courses").child(note.course).child(note.branch)
        .child(note.subject).child(note.noteType).child(note.fileInfo.fileUploadPath)
}

fun noteFavRef(
    note: FileUploadModel,
    firestore: FirebaseFirestore,
    currentUser: FirebaseUser
): DocumentReference {

    return firestore.collection("Users").document(currentUser.email.toString())
        .collection("Favourite").document("fav1")
        .collection(note.noteType).document(note.fileInfo.fileUploadPath)
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
    return firestore.collection("Users").document(currentUser.email.toString())
        .collection("UserData").document("UserInfo")
}

fun userUploadRef(note: FileUploadModel, firestore: FirebaseFirestore, currentUser: FirebaseUser): DocumentReference {
    return firestore.collection("Users").document(currentUser.email.toString())
        .collection("uploads").document(note.fileInfo.fileUploadPath)
}

fun userProfileRef(storageRef: StorageReference, currentUser: FirebaseUser): StorageReference{
    return  storageRef.child("userProfile").child(currentUser.email.toString())
}

