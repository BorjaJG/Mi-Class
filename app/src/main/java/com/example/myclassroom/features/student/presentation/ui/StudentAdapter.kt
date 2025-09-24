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
    private val onEdit: (Student) -> Unit, // ✅ Cambiado nombre para claridad
    private val onDelete: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>(), Filterable {

    private var studentsFiltered: MutableList<Student> = students.toMutableList()

    inner class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.tvStudentName)
        private val course: TextView = view.findViewById(R.id.tvStudentCourse)
        private val btnEdit: Button = view.findViewById(R.id.btnEditStudent)
        private val btnDelete: Button = view.findViewById(R.id.btnDeleteStudent)

        fun bind(student: Student) {
            name.text = student.name
            course.text = "${student.course} - Semestre ${student.semester}"

            // Click sobre el ítem
            itemView.setOnClickListener { onEdit(student) }

            // Click sobre el botón Editar
            btnEdit.setOnClickListener { onEdit(student) }

            // Click sobre el botón Borrar
            btnDelete.setOnClickListener {
                onDelete(student) // ✅ Notificar al fragment/activity
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_student, parent, false)
        )
    }

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
                    students.toList() // ✅ Usar copia de la lista original
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
                studentsFiltered.clear()
                studentsFiltered.addAll((results?.values as? List<Student>) ?: emptyList())
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
            // Si no está en la lista filtrada, actualizar toda la lista
            studentsFiltered.clear()
            studentsFiltered.addAll(students)
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
    fun deleteStudent(student: Student) { // ✅ Cambiado parámetro para mayor claridad
        // Eliminar de la lista original
        students.removeAll { it.id == student.id }

        // Eliminar de la lista filtrada
        val filteredIndex = studentsFiltered.indexOfFirst { it.id == student.id }
        if (filteredIndex != -1) {
            studentsFiltered.removeAt(filteredIndex)
            notifyItemRemoved(filteredIndex)
        } else {
            notifyDataSetChanged()
        }
    }
}