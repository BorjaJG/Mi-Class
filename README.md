# 📘 Proyecto CRUD de Alumnos

Este proyecto implementa un **CRUD (Create, Read, Update, Delete)** usando como ejemplo una clase de **Alumnos**.  
El objetivo es mostrar de manera sencilla cómo gestionar una lista de estudiantes con operaciones básicas de mantenimiento de datos.

---

## 🔧 Funcionalidades del CRUD

1. **Create (Crear):**  
   - Agregar un nuevo alumno a la lista.  
   - Datos de ejemplo: nombre, apellido, edad, matrícula.

2. **Read (Leer):**  
   - Visualizar todos los alumnos registrados.  
   - Listado dinámico que muestra la información en un `RecyclerView` o lista similar.

3. **Update (Actualizar):**  
   - Editar la información de un alumno existente.  
   - Ejemplo: cambiar nombre, edad o matrícula.

4. **Delete (Eliminar):**  
   - Eliminar un alumno de la lista.  
   - Incluye confirmación con `AlertDialog` para evitar eliminaciones accidentales.

---

## 🏗️ Tecnologías y herramientas usadas

- **Lenguaje:** Kotlin  
- **Patrón:** Arquitectura MVVM  
- **UI:** Android Jetpack Compose / XML + RecyclerView  
- **Gestión de estado:** LiveData y ViewModel  
- **Adapter:** Uso de `ListAdapter` y `DiffUtil` para manejo eficiente de listas  
- **AlertDialog (Material Design):** Confirmación en operaciones destructivas  
- **Notificaciones:** Toasts para feedback de acciones exitosas  

---

## 🧑‍🎓 Clase de ejemplo: `Alumno`

```kotlin
data class Alumno(
    val id: Int,
    var nombre: String,
    var apellido: String,
    var edad: Int,
    var matricula: String
)
