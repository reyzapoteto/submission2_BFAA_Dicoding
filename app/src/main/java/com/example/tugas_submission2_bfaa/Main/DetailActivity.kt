package com.example.tugas_submission2_bfaa.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.tugas_submission2_bfaa.Adapter.TabBarAdapter
import com.example.tugas_submission2_bfaa.Datamodel.UserDetailDatamodel
import com.example.tugas_submission2_bfaa.MainViewModel.MainViewModel
import com.example.tugas_submission2_bfaa.MainViewModel.UserViewModelFactory
import com.example.tugas_submission2_bfaa.R
import com.example.tugas_submission2_bfaa.Retrofit.RetrofitUser
import com.example.tugas_submission2_bfaa.database.User
import com.example.tugas_submission2_bfaa.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var user: User? = null
    private lateinit var userViewModel: MainViewModel

    private lateinit var userCompany: String
    private lateinit var userLocation: String
    private lateinit var userFullName: String
    private lateinit var userRepo: String
    private var userUsername: String? = null
    private lateinit var userAvatar: String

    companion object {

        val TAG: String = DetailActivity::class.java.simpleName

        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = obtainViewModel(this)
        user = User()

        binding.imgBtnKembali.setOnClickListener {
            super.onBackPressed()
        }

        val tabAdapter = TabBarAdapter(this)
        binding.viewPager.adapter = tabAdapter
        TabLayoutMediator(binding.tabUserFollow, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        val intent = intent
        val name = intent.getStringExtra("name")
        val status =  intent.getStringExtra("status")

        getDetailUser(name)

        if(status == "detail"){
            binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)

        }else{
            binding.fabFavorite.setImageResource(R.drawable.ic_baseline_delete_24)
        }

        binding.fabFavorite.setOnClickListener {
            if(status == "detail"){

                user.let {
                    it?.name = userUsername!!
                    it?.fullname = userFullName
                    it?.avatar_url = userAvatar
                    it?.company = userCompany
                    it?.location = userLocation
                    it?.public_repos = userRepo
                }

                userViewModel.insert(user as User)
                Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()

            }else{
                userViewModel.deleteUser(userUsername.toString())
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()

            }

        }

    }

    private fun getDetailUser(name: String?) {
        binding.progUserDetail.visibility = View.VISIBLE
        RetrofitUser.instance.getUsersDetail(name.toString())
            .enqueue(object : Callback<UserDetailDatamodel> {

                override fun onResponse(
                    call: Call<UserDetailDatamodel>,
                    response: Response<UserDetailDatamodel>
                ) {
                    binding.progUserDetail.visibility = View.INVISIBLE

                    when (response.code()) {
                        401 -> Log.d(TAG, "${response.code()} : Bad Request")
                        403 -> Log.d(TAG, "${response.code()} : Forbidden")
                        404 -> Log.d(TAG, "${response.code()} : Not Found")
                        else -> Log.d(
                            TAG,
                            "${response.code()} : ${response.errorBody()}"
                        )
                    }
                    val responsenya = response.body()

                    userFullName =
                        resources.getString(R.string.fullname) + responsenya?.fullname
                    userCompany = resources.getString(R.string.company) + responsenya!!.company
                    userLocation =
                        resources.getString(R.string.location) + responsenya.location
                    userRepo =
                        resources.getString(R.string.repo) + responsenya.public_repos.toString()

                    userUsername = responsenya.name
                    userAvatar = responsenya.avatar_url

                    Glide
                        .with(this@DetailActivity)
                        .load(userAvatar)
                        .into(binding.imgUserAvatar)

                    binding.tvUSerFullname.text = userFullName
                    binding.tvUserCompany.text = userCompany
                    binding.tvUserLocation.text = userLocation
                    binding.tvUserRepo.text = userRepo
                    binding.tvUserUsername.text = userUsername

                }

                override fun onFailure(call: Call<UserDetailDatamodel>, t: Throwable) {
                    binding.progUserDetail.visibility = View.INVISIBLE
                    Toast.makeText(this@DetailActivity, t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = UserViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }
}