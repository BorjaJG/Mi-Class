package com.example.myclassroom

import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myclassroom.features.student.domain.model.Student
import com.example.myclassroom.features.student.presentation.StudentAdapter
import edu.example.dam2024.features.student.data.remote.StudentMockRemoteDataSource

class MainActivity : AppCompatActivity() {

    // ✅ Declarar el adapter como propiedad de la clase
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val recycler: RecyclerView = findViewById(R.id.recyclerStudents)
        recycler.layoutManager = LinearLayoutManager(this)

        val students: List<Student> = StudentMockRemoteDataSource().getStudents()

        // Inicializar el adapter
        adapter = StudentAdapter(students as MutableList<Student>) { student ->
            showEditDialog(student)
        }
        recycler.adapter = adapter

        // 🔎 Conectar el SearchView con el filtro
        val searchView: SearchView = findViewById(R.id.searchStudents)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })

        // ⚡️ Ejemplo: Modificar un estudiante automáticamente al iniciar
        val studentToUpdate = students.firstOrNull { it.id == "1" }
        studentToUpdate?.let {
            val updated = it.copy(course = "DAM actualizado", semester = 2)
            adapter.updateStudent(updated)
        }
    }

    // --- ✏️ Función para mostrar diálogo de edición ---
    private fun showEditDialog(student: Student) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_student, null)

        val etName: EditText = dialogView.findViewById(R.id.etStudentName)
        val etCourse: EditText = dialogView.findViewById(R.id.etStudentCourse)
        val etSemester: EditText = dialogView.findViewById(R.id.etStudentSemester)

        // Rellenar con los datos actuales
        etName.setText(student.name)
        etCourse.setText(student.course)
        etSemester.setText(student.semester.toString())

        AlertDialog.Builder(this)
            .setTitle("Editar estudiante")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val updated = student.copy(
                    name = etName.text.toString(),
                    course = etCourse.text.toString(),
                    semester = etSemester.text.toString().toIntOrNull() ?: student.semester
                )
                adapter.updateStudent(updated)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
