package com.example.tugas_submission2_bfaa

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.tugas_submission2_bfaa.Adapter.UserMainAdapter
import com.example.tugas_submission2_bfaa.Datamodel.UserDatamodel
import com.example.tugas_submission2_bfaa.Datamodel.UserSearchDatamodel
import com.example.tugas_submission2_bfaa.MainViewModel.SettingViewModel
import com.example.tugas_submission2_bfaa.MainViewModel.ViewModelFactory
import com.example.tugas_submission2_bfaa.Preferences.SettingPreferences
import com.example.tugas_submission2_bfaa.Retrofit.RetrofitUser
import com.example.tugas_submission2_bfaa.SettingsActivity.Companion.value
import com.example.tugas_submission2_bfaa.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.switchmaterial.SwitchMaterial
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var value: Boolean = false
    val userAdapter = UserMainAdapter()

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadTheme()

        showUserAll()

        if (value) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.searchUser).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.usernameHint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!!.isEmpty()) {
                    showUserAll()
                } else {
                    showUserSelected(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
            R.id.favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
            else -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showUserAll() {
        binding.rvUser.setHasFixedSize(true)
        binding.progUSer.visibility = View.VISIBLE
        RetrofitUser.instance.getAllUsers().enqueue(object : Callback<ArrayList<UserDatamodel>> {

            override fun onResponse(
                call: Call<ArrayList<UserDatamodel>>,
                response: Response<ArrayList<UserDatamodel>>
            ) {
                when (response.code()) {
                    401 -> Log.d(TAG, "${response.code()} : Bad Request")
                    403 -> Log.d(TAG, "${response.code()} : Forbidden")
                    404 -> Log.d(TAG, "${response.code()} : Not Found")
                    else -> Log.d(
                        TAG,
                        "${response.code()} : ${response.errorBody()}"
                    )
                }
                binding.progUSer.visibility = View.INVISIBLE
                val list = response.body()
                list?.let { userAdapter.submitList(it) }
                binding.rvUser.adapter = userAdapter
            }

            override fun onFailure(call: Call<ArrayList<UserDatamodel>>, t: Throwable) {
                binding.progUSer.visibility = View.INVISIBLE
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun showUserSelected(query: String) {

        binding.rvUser.setHasFixedSize(true)
        binding.progUSer.visibility = View.VISIBLE

        RetrofitUser.instance.getUserSelected(query)
            .enqueue(object : Callback<UserSearchDatamodel> {

                override fun onResponse(
                    call: Call<UserSearchDatamodel>,
                    response: Response<UserSearchDatamodel>
                ) {
                    when (response.code()) {
                        401 -> Log.d(TAG, "${response.code()} : Bad Request")
                        403 -> Log.d(TAG, "${response.code()} : Forbidden")
                        404 -> Log.d(TAG, "${response.code()} : Not Found")
                        else -> Log.d(
                            TAG,
                            "${response.code()} : ${response.errorBody()}"
                        )
                    }

                    binding.progUSer.visibility = View.INVISIBLE

                    val list = response.body()

                    list?.let { result -> userAdapter.submitList(result.items) }
                    binding.rvUser.adapter = userAdapter

                }

                override fun onFailure(call: Call<UserSearchDatamodel>, t: Throwable) {
                    binding.progUSer.visibility = View.INVISIBLE
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun loadTheme() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        value = sharedPreferences.getBoolean("darkmode", false)
    }


}