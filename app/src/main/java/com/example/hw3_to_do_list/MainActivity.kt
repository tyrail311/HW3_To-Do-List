package com.example.hw3_to_do_list

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE = 414
    private val FILE_NAME = "MyToDoList"
    private val newTodoArray = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onStart()
    {
        loadJson()
        super.onStart()
        val todoListAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, newTodoArray)
        list_view.adapter = todoListAdapter
        list_view.setOnItemLongClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "Completed: $selectedItem", Toast.LENGTH_SHORT).show()
            newTodoArray.removeAt(position)
            if (newTodoArray.size == 0)
                Toast.makeText(this, "All tasks are completed!", Toast.LENGTH_SHORT).show()
            todoListAdapter.notifyDataSetChanged()
            return@setOnItemLongClickListener true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        saveJson()
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
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
        }
    }
    fun saveJson() {

        if (newTodoArray.size == 0) { // Make sure the list has some items
            Toast.makeText(this, "Nothing to save", Toast.LENGTH_SHORT).show()
            return
        }
        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val gson = Gson()
        val newToDoArrayJson = gson.toJson(newTodoArray)
        editor.putString("tasks", newToDoArrayJson)
        editor.apply()

        val toast = Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()


    }


    fun loadJson() {

        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        val tasks = sharedPreferences.getString("tasks", "") ?: ""

        if (tasks.isNotEmpty()){

            val gson = Gson()
            val sType = object : TypeToken<List<String>>() { }.type
            val savedVehicleList = gson.fromJson<List<String>>(tasks, sType)

            newTodoArray.clear()

            for (vehicle in savedVehicleList) {
                newTodoArray.add(vehicle)
            }
        }
    }
}