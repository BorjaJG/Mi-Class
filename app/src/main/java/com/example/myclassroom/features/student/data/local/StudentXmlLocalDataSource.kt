package com.example.myclassroom.features.student.data.local

import android.content.Context
import com.example.myclassroom.R
import com.example.myclassroom.features.student.domain.model.Student
import com.google.gson.Gson


class StudentXmlLocalDataSource(private val context: Context) {

    private val gson = Gson()

    private val sharedPreferences = context.getSharedPreferences(
        context.getString(R.string.student_file_xml), Context.MODE_PRIVATE
    )

    fun saveAll(students: List<Student>) {
        val editor = sharedPreferences.edit()
        students.forEach { student ->
            editor.putString(student.id, gson.toJson(student))
        }
        editor.apply()
    }

    fun getStudents(): List<Student> {
        val students = mutableListOf<Student>()
        val mapStudents = sharedPreferences.all
        mapStudents.values.forEach { jsonStudent ->
            val student = gson.fromJson(jsonStudent as String, Student::class.java)
            students.add(student)
        }
        return students
    }

    fun deleteAll() {
        sharedPreferences.edit().clear().apply()
    }
}
