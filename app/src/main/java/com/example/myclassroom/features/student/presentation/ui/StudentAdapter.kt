package com.example.myclassroom.features.student.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myclassroom.R
import com.example.myclassroom.features.student.domain.model.Student
import java.util.Locale

class StudentAdapter(
    private val students: List<Student>,
    private val onClick: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>(), Filterable {

    private var studentsFiltered: MutableList<Student> = students.toMutableList()

    inner class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.tvStudentName)
        private val course: TextView = view.findViewById(R.id.tvStudentCourse)

        fun bind(student: Student) {
            name.text = student.name
            course.text = "${student.course} - Semestre ${student.semester}"
            itemView.setOnClickListener { onClick(student) }
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

    // --- Filtro de búsqueda ---
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
}
