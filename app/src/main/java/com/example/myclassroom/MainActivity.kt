package com.example.myclassroom

import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myclassroom.features.student.domain.model.Student
import com.example.myclassroom.features.student.presentation.StudentAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.example.dam2024.features.student.data.remote.StudentMockRemoteDataSource

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: StudentAdapter
    private val students: MutableList<Student> = StudentMockRemoteDataSource().getStudents().toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val recycler: RecyclerView = findViewById(R.id.recyclerStudents)
        recycler.layoutManager = LinearLayoutManager(this)

        // ✅ Inicializar el adapter con ambos callbacks
        adapter = StudentAdapter(
            students = students,
            onEdit = { student -> showEditDialog(student) },
            onDelete = { student -> deleteStudent(student) }
        )
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

        // ➕ Botón para añadir nuevo estudiante
        val fabAdd: FloatingActionButton = findViewById(R.id.fabAddStudent)
        fabAdd.setOnClickListener {
            showAddStudentDialog()
        }

        // ⚡️ Modificar un estudiante automáticamente al iniciar (opcional)
        val studentToUpdate = students.firstOrNull { it.id == "1" }
        studentToUpdate?.let {
            val updated = it.copy(course = "DAM actualizado", semester = 2)
            adapter.updateStudent(updated)
        }
    }

    // --- ➕ Función para mostrar diálogo de AÑADIR estudiante ---
    private fun showAddStudentDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_student, null)

        val etName: EditText = dialogView.findViewById(R.id.etStudentName)
        val etCourse: EditText = dialogView.findViewById(R.id.etStudentCourse)
        val etSemester: EditText = dialogView.findViewById(R.id.etStudentSemester)

        // Limpiar campos para nuevo estudiante
        etName.setText("")
        etCourse.setText("")
        etSemester.setText("")

        AlertDialog.Builder(this)
            .setTitle("Añadir nuevo estudiante")
            .setView(dialogView)
            .setPositiveButton("Crear") { _, _ ->
                val name = etName.text.toString()
                val course = etCourse.text.toString()
                val semester = etSemester.text.toString().toIntOrNull() ?: 1

                if (name.isBlank()) {
                    Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val newStudent = Student(
                    id = (System.currentTimeMillis()).toString(), // ✅ ID único
                    name = name,
                    course = course,
                    semester = semester,
                    dni = "",
                    age = 0,
                    subjects = emptyList()
                )

                // Añadir a la lista y actualizar adapter
                adapter.addStudent(newStudent)
                Toast.makeText(this, "Estudiante añadido", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    // --- ✏️ Función para mostrar diálogo de EDITAR estudiante ---
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
                val name = etName.text.toString()
                val course = etCourse.text.toString()
                val semester = etSemester.text.toString().toIntOrNull() ?: student.semester

                if (name.isBlank()) {
                    Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val updated = student.copy(
                    name = name,
                    course = course,
                    semester = semester
                )
                adapter.updateStudent(updated)
                Toast.makeText(this, "Estudiante actualizado", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    // --- 🗑️ Función para eliminar estudiante ---
    private fun deleteStudent(student: Student) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar estudiante")
            .setMessage("¿Estás seguro de que quieres eliminar a ${student.name}?")
            .setPositiveButton("Eliminar") { _, _ ->
                adapter.deleteStudent(student)
                Toast.makeText(this, "Estudiante eliminado", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}