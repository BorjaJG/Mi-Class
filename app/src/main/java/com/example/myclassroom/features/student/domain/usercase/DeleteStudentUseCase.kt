package com.example.myclassroom.features.student.domain.usercase

import com.example.myclassroom.features.student.domain.repository.StudentRepository

class DeleteStudentUseCase(private val studentRepository: StudentRepository) {
    suspend operator fun invoke(id: String): Boolean {
        return studentRepository.deleteStudent(id)
    }
}