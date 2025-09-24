package com.example.myclassroom.features.student.domain.usercase

import com.example.myclassroom.features.student.domain.model.Student
import com.example.myclassroom.features.student.domain.repository.StudentRepository

class GetStudentUseCase(private val studentRepository: StudentRepository) {

    suspend operator fun invoke(id: String): Student? {
        return studentRepository.getStudent()
    }
}