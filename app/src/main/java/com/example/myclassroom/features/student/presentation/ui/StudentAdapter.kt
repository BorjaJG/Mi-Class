package com.example.myclassroom.features.student.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myclassroom.R
import com.example.myclassroom.features.student.domain.model.Student
import java.util.Locale

class StudentAdapter(
    private val students: MutableList<Student>,
    private val onClick: (Student) -> Unit,
    private val onDelete: (Student) -> Unit // ✅ Nuevo callback para eliminar
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>(), Filterable {

    private var studentsFiltered: MutableList<Student> = students.toMutableList()

    inner class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.tvStudentName)
        private val course: TextView = view.findViewById(R.id.tvStudentCourse)
        private val btnEdit: Button = view.findViewById(R.id.btnEditStudent)
        private val btnDelete: Button =
            view.findViewById(R.id.btnDeleteStudent) // ✅ Corregido nombre

        fun bind(student: Student) {
            name.text = student.name
            course.text = "${student.course} - Semestre ${student.semester}"

            // Click sobre el ítem
            itemView.setOnClickListener { onClick(student) }

            // Click sobre el botón Editar
            btnEdit.setOnClickListener { onClick(student) }

            // Click sobre el botón Borrar
            btnDelete.setOnClickListener {
                onDelete(student) // ✅ Notificar al fragment/activity
                deleteStudent(student.id) // ✅ Eliminar del adapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        StudentViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_student, parent, false)
        )

    override fun getItemCount() = studentsFiltered.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(studentsFiltered[position])
    }

    // --- 🔎 Filtro de búsqueda ---
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase(Locale.getDefault()) ?: ""
                val filteredList = if (query.isEmpty()) {
                    students
                } else {
                    students.filter {
                        it.name.lowercase(Locale.getDefault()).contains(query) ||
                                it.course.lowercase(Locale.getDefault()).contains(query)
                    }
                }
                return FilterResults().apply { values = filteredList }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                studentsFiltered =
                    (results?.values as? List<Student>)?.toMutableList() ?: mutableListOf()
                notifyDataSetChanged()
            }
        }
    }

    // --- ✏️ Modificar un estudiante ---
    fun updateStudent(student: Student) {
        // Buscar en la lista original
        val indexOriginal = students.indexOfFirst { it.id == student.id }
        if (indexOriginal != -1) {
            students[indexOriginal] = student
        }

        // Buscar en la lista filtrada
        val indexFiltered = studentsFiltered.indexOfFirst { it.id == student.id }
        if (indexFiltered != -1) {
            studentsFiltered[indexFiltered] = student
            notifyItemChanged(indexFiltered)
        } else {
            notifyDataSetChanged()
        }
    }

    // --- ➕ Añadir un nuevo estudiante ---
    fun addStudent(student: Student) {
        students.add(student)
        studentsFiltered.add(student)
        notifyItemInserted(studentsFiltered.size - 1)
    }

    // --- 🗑️ Eliminar un estudiante ---
    fun deleteStudent(id: String) { // ✅ Corregido nombre de función
        // Buscar posición en la lista filtrada
        val filteredIndex = studentsFiltered.indexOfFirst { it.id == id }

        // Eliminar de ambas listas
        students.removeAll { it.id == id }

        val wasRemovedFromFiltered = studentsFiltered.removeAll { it.id == id }

        // Notificar al adapter
        if (wasRemovedFromFiltered && filteredIndex != -1) {
            notifyItemRemoved(filteredIndex)
        } else {
            notifyDataSetChanged()
        }
    }
}