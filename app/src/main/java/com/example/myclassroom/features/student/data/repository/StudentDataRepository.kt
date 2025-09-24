package com.example.myclassroom.features.student.data.repository

import com.example.myclassroom.features.student.data.local.StudentXmlLocalDataSource
import com.example.myclassroom.features.student.domain.model.Student
import com.example.myclassroom.features.student.domain.repository.StudentRepository
import edu.example.dam2024.features.student.data.remote.StudentMockRemoteDataSource

class StudentDataRepository(
    private val studentXmlLocalDataSource: StudentXmlLocalDataSource,
    private val studentApiRemoteDataSource: StudentMockRemoteDataSource
) : StudentRepository {

    override suspend fun getAllStudents(): List<Student> {
        val studentsFromLocal = studentXmlLocalDataSource.getStudents()
        return studentsFromLocal.ifEmpty {
            val studentsFromRemote = studentApiRemoteDataSource.getStudents()
            studentXmlLocalDataSource.saveAll(studentsFromRemote)
            studentsFromRemote
        }
    }


}