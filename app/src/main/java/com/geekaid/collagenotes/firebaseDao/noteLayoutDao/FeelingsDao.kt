//package com.geekaid.collagenotes.firebaseDao.noteLayoutDao
//
//import com.geekaid.collagenotes.model.FileUploadModel
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase
//
//fun feelingsLikeDao(course: FileUploadModel) {
//
//    val currentUser = Firebase.auth.currentUser!!
//    val db = Firebase.firestore
//
//    val noteRef = db.collection("courses").document(course.course)
//        .collection(course.branch).document(course.subject)
//        .collection("notes").document(course.fileUploadPath)
//
//    val favNoteRef = db.collection("Users").document(currentUser.email.toString())
//        .collection("Favourite").document(course.fileUploadPath)
//
//    if (course.likes.contains(currentUser.email) && course.likes.isNotEmpty()) {
//        course.likes.remove(currentUser.email)
//    } else {
//        course.likes.add(currentUser.email.toString())
//    }
//
//    if (course.dislikes.contains(currentUser.email)) {
//        course.dislikes.remove(currentUser.email)
//    }
//
//    db.runBatch { batch ->
//        batch.update(noteRef, "likes", course.likes, "dislikes", course.dislikes)
//        batch.update(favNoteRef, "likes", course.likes, "dislikes", course.dislikes)
//    }
//}
//
//fun feelingsDislikeDao(course: FileUploadModel) {
//
//    val currentUser = Firebase.auth.currentUser!!
//    val db = Firebase.firestore
//
//    val noteRef = db.collection("courses").document(course.course)
//        .collection(course.branch).document(course.subject)
//        .collection("notes").document(course.fileUploadPath)
//
//    val favNoteRef = db.collection("Users").document(currentUser.email.toString())
//        .collection("Favourite").document(course.fileUploadPath)
//
//    if (course.likes.contains(currentUser.email)) {
//        course.likes.remove(currentUser.email)
//    }
//
//    if (course.dislikes.contains(currentUser.email)) {
//        course.dislikes.remove(currentUser.email)
//    } else {
//        course.dislikes.add(currentUser.email.toString())
//    }
//
//    db.runBatch { batch ->
//        batch.update(noteRef, "likes", course.likes, "dislikes", course.dislikes)
//        batch.update(favNoteRef, "likes", course.likes, "dislikes", course.dislikes)
//    }
//}