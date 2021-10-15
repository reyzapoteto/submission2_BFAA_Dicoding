package com.example.tugas_submission2_bfaa.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.example.tugas_submission2_bfaa.Adapter.UserFavoriteAdapter
import com.example.tugas_submission2_bfaa.MainViewModel.MainViewModel
import com.example.tugas_submission2_bfaa.MainViewModel.UserViewModelFactory
import com.example.tugas_submission2_bfaa.MainViewModel.ViewModelFactory
import com.example.tugas_submission2_bfaa.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserFavoriteAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserFavoriteAdapter(this)

        binding.rvFavorite.adapter = adapter
        binding.rvFavorite.setHasFixedSize(true)


        supportActionBar?.title = "Favorite User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        mainViewModel = obtainViewModel(this)
        mainViewModel.getAllUsers().observe(this, { userList ->
            if (userList != null) {
                adapter.setListUser(userList)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item);
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = UserViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }
}