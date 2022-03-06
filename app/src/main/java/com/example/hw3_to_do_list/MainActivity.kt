package com.example.hw3_to_do_list

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val REQUEST_CODE = 414
    val newTodoArray = arrayListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onStart()
    {
        super.onStart()
        val todoListAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1, newTodoArray
        )
        list_view.adapter = todoListAdapter
        list_view.setOnItemLongClickListener { parent, view, position, id ->

            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "This is a long press, Deleting $selectedItem", Toast.LENGTH_SHORT).show()

            newTodoArray.removeAt(position)

            todoListAdapter.notifyDataSetChanged()

            return@setOnItemLongClickListener true
        }
    }
    fun openSecondActivity(view : View)
    {
        val listEntryIntent = Intent(this, AddItems::class.java)
        startActivityForResult(listEntryIntent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val todoList = data?.getStringArrayListExtra("tasks")
        if(todoList != null)
        {
            for (task: String in todoList) {
                newTodoArray.add(task)
            }
        }
    }
}