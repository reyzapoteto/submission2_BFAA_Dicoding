package com.example.tugas_submission2_bfaa.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tugas_submission2_bfaa.Adapter.FollowAdapter
import com.example.tugas_submission2_bfaa.Datamodel.UserFollowDatamodel
import com.example.tugas_submission2_bfaa.Retrofit.RetrofitUser
import com.example.tugas_submission2_bfaa.databinding.FragmentFollowersBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding
    private lateinit var name: String

    companion object {
        val TAG: String = FollowersFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val intent = requireActivity().intent
        name = intent.getStringExtra("name").toString()
        getUserFollowers(requireActivity(), name)
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    private fun getUserFollowers(context: Context, name: String) {

        RetrofitUser.instance.getUserFollowers(name)
            .enqueue(object : Callback<ArrayList<UserFollowDatamodel>> {

                override fun onResponse(
                    call: Call<ArrayList<UserFollowDatamodel>>,
                    response: Response<ArrayList<UserFollowDatamodel>>
                ) {
                    when (response.code()) {
                        401 -> Log.d(TAG, "${response.code()} : Bad Request")
                        403 -> Log.d(TAG, "${response.code()} : Forbidden")
                        404 -> Log.d(TAG, "${response.code()} : Not Found")
                        else -> Log.d(TAG, "${response.code()} : ${response.errorBody()}")
                    }

                    val list = response.body()
                    val userFollowers = FollowAdapter()
                    binding!!.rvFollowers.adapter = userFollowers
                    list?.let { userFollowers.subList(it) }

                }

                override fun onFailure(
                    call: Call<ArrayList<UserFollowDatamodel>>,
                    t: Throwable
                ) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }

            })

    }


}