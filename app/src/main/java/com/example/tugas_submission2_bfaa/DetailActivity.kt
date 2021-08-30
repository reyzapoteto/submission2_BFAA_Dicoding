package com.example.tugas_submission2_bfaa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.tugas_submission2_bfaa.Adapter.TabBarAdapter
import com.example.tugas_submission2_bfaa.Datamodel.UserDetailDatamodel
import com.example.tugas_submission2_bfaa.Retrofit.RetrofitUser
import com.example.tugas_submission2_bfaa.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

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

        getDetailUser(name)
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

                    Glide
                        .with(this@DetailActivity)
                        .load(responsenya?.avatar_url)
                        .into(binding.imgUserAvatar)

                    val userFullName =
                        resources.getString(R.string.fullname) + responsenya?.fullname
                    val userCompany = resources.getString(R.string.company) + responsenya!!.company
                    val userLocation =
                        resources.getString(R.string.location) + responsenya.location
                    val userRepo =
                        resources.getString(R.string.repo) + responsenya.public_repos.toString()


                    binding.tvUSerFullname.text = userFullName
                    binding.tvUserCompany.text = userCompany
                    binding.tvUserLocation.text = userLocation
                    binding.tvUserRepo.text = userRepo
                    binding.tvUserUsername.text = responsenya.name

                }

                override fun onFailure(call: Call<UserDetailDatamodel>, t: Throwable) {
                    binding.progUserDetail.visibility = View.INVISIBLE
                    Toast.makeText(this@DetailActivity, t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }
}