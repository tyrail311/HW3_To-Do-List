package com.example.hw3_to_do_list

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_add_items.*

class AddItems : AppCompatActivity() {
    val REQUEST_CODE = 414
    val todoList = arrayListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_items)
    }

    fun goBackWithoutSaving(view : View)
    {
        val listEntryIntent = Intent(this, MainActivity::class.java)
        startActivityForResult(listEntryIntent, REQUEST_CODE)
        setResult(Activity.RESULT_CANCELED, listEntryIntent)
        finish()
    }

    fun saveAndAdd(view : View)
    {
        addToList()

    }

    fun goBackAndSave(view : View)
    {
        val listEntryIntent = Intent(this, MainActivity::class.java)
        addToList()
        listEntryIntent.putExtra("tasks", todoList)
        setResult(Activity.RESULT_OK, listEntryIntent)
        finish()
    }

    fun addToList()
    {
        todoList.add(write_a_task_text.text.toString())
    }
}