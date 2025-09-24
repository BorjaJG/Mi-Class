package com.example.myclassroom.features.student.domain.usercase

import com.example.myclassroom.features.student.domain.model.Student
import com.example.myclassroom.features.student.domain.repository.StudentRepository

class GetAllStudentsUseCase(private val repository: StudentRepository) {
    suspend operator fun invoke(): List<Student> {
        return repository.getAllStudents()
    }
}