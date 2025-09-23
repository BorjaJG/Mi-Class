package com.example.myclassroom.features.student.domain.repository

import com.example.myclassroom.features.student.domain.model.Student

interface StudentRepository {
    suspend fun getAllStudents(): List<Student>
    fun getStudent(): Student?
    fun modifyStudent(student: Student)
    fun addStudent(student: Student): Student
    fun deleteStudent(id: String): Boolean


}