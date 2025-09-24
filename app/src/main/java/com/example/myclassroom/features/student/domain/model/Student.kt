package com.example.myclassroom.features.student.domain.model

data class Student(
    val id: String,
    val dni: String,
    val name: String,
    val age: Int,
    val course: String,
    val semester: Int,
    val absences: List<Absence>,
    val subjects: List<Subject>
)

data class Absence(
    val subject: String,
    val date: String,
    val justified: Boolean
)

data class Subject(
    val code: String,
    val name: String,
    val teacher: String,
    val grade: Double? = null
)

data class StudentListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<StudentListItem>
)

data class StudentListItem(
    val name: String,
    val id: String
)
