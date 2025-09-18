package edu.example.dam2024.features.student.data.remote

import com.example.myclassroom.features.student.domain.model.Absence
import com.example.myclassroom.features.student.domain.model.Student
import com.example.myclassroom.features.student.domain.model.Subject

class StudentMockRemoteDataSource {

    private val students = listOf(
        Student(
            id = "1",
            dni = "12345678A",
            name = "Juan Pérez",
            age = 20,
            course = "DAM",
            semester = 1,
            absences = listOf(
                Absence(subject = "Programación", date = "2025-01-15", justified = false),
                Absence(subject = "Bases de Datos", date = "2025-01-20", justified = true)
            ),
            subjects = listOf(
                Subject(
                    code = "PRG",
                    name = "Programación",
                    teacher = "Profesor García",
                    grade = 8.5
                ),
                Subject(
                    code = "BBDD",
                    name = "Bases de Datos",
                    teacher = "Profesora López",
                    grade = 7.0
                )
            )
        ),
        Student(
            id = "2",
            dni = "87654321B",
            name = "María López",
            age = 21,
            course = "DAM",
            semester = 2,
            absences = listOf(
                Absence(subject = "Entornos", date = "2025-02-05", justified = false)
            ),
            subjects = listOf(
                Subject(
                    code = "ENT",
                    name = "Entornos de Desarrollo",
                    teacher = "Profesor Ruiz",
                    grade = 9.0
                ),
                Subject(
                    code = "FOL",
                    name = "Formación y Orientación Laboral",
                    teacher = "Profesora Martínez",
                    grade = 6.5
                )
            )
        ),
        Student(
            id = "3",
            dni = "45678912C",
            name = "Carlos Gómez",
            age = 19,
            course = "DAM",
            semester = 1,
            absences = emptyList(),
            subjects = listOf(
                Subject(
                    code = "LMSGI",
                    name = "Lenguajes de Marcas",
                    teacher = "Profesor Fernández",
                    grade = 7.5
                ),
                Subject(
                    code = "SIS",
                    name = "Sistemas Informáticos",
                    teacher = "Profesor Sánchez",
                    grade = 8.0
                )
            )
        )
    )

    fun getStudents(): List<Student> {
        return students
    }
}
