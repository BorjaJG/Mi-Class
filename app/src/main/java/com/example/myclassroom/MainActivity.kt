package com.example.myclassroom

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myclassroom.features.student.domain.model.Student
import com.example.myclassroom.features.student.presentation.StudentAdapter
import edu.example.dam2024.features.student.data.remote.StudentMockRemoteDataSource

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val recycler: RecyclerView = findViewById(R.id.recyclerStudents)
        recycler.layoutManager = LinearLayoutManager(this)

        val students: List<Student> = StudentMockRemoteDataSource().getStudents()
        val adapter = StudentAdapter(students) { }
        recycler.adapter = adapter
    }
}
