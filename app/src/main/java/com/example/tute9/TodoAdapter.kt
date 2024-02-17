package com.example.tute9

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.tute9.database.Todo
import com.example.tute9.database.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TodoAdapter(items:List<Todo>, repository: TodoRepository,viewModel: MainActivityData):Adapter<ToDoViewHolder>(){

    lateinit var context: Context

    val items = items
    val repository = repository
    val viewModel = viewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item,parent,false)
        context = parent.context

        return ToDoViewHolder((view))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.cbTodo.text = items.get(position).item
        holder.ivDelete.setOnClickListener{
            val isChecked = holder.cbTodo.isChecked

            if (isChecked){
                CoroutineScope(Dispatchers.IO).launch {
                    repository.delete(items.get(position))
                    val data = repository.getAllTodoItems()
                    withContext(Dispatchers.Main){
                        viewModel.setData(data)
                    }
                }
            }else{
                Toast.makeText(context,"Select the item to be deleted", Toast.LENGTH_LONG).show()
            }

        }
    }

}
