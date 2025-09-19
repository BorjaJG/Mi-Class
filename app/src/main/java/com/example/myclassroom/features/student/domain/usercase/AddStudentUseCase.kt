package com.example.myclassroom.features.student.domain.usercase

import com.example.myclassroom.features.student.domain.model.Student
import com.example.myclassroom.features.student.domain.repository.StudentRepository


class AddStudentUseCase(private val repository: StudentRepository) {
    suspend operator fun invoke(student: Student): Student {
        return repository.addStudent(student)
    }
}