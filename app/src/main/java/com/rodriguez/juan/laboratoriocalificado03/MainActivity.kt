package com.rodriguez.juan.laboratoriocalificado03

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodriguez.juan.laboratoriocalificado03.databinding.ActivityMainBinding
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private val adapter by lazy { TeacherAdapter() }

    private lateinit var binding : ActivityMainBinding

    private val viewModel by lazy { MainViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTeachers.adapter = adapter
        binding.rvTeachers.layoutManager = LinearLayoutManager(this)

        observeValues()
    }

    private fun observeValues() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        viewModel.teacherList.observe(this) { teacherList ->
            if (teacherList != null) {
                adapter.updateList(teacherList)
            } else {
                adapter.updateList(emptyList())
            }
        }

        viewModel.errorApi.observe(this) { errorMessage ->
            showMessage(errorMessage)
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}