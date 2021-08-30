package com.example.tugas_submission2_bfaa.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tugas_submission2_bfaa.Adapter.FollowingAdapter
import com.example.tugas_submission2_bfaa.Datamodel.UserFollowingDatamodel
import com.example.tugas_submission2_bfaa.Retrofit.RetrofitUser
import com.example.tugas_submission2_bfaa.databinding.FragmentFollowingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding
    private lateinit var name: String

    companion object {
        val TAG: String = FollowingFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)

        val intent = requireActivity().intent
        name = intent.getStringExtra("name").toString()
        getAllFollowing(requireActivity())
        return binding!!.root

    }

    private fun getAllFollowing(context: Context) {

        RetrofitUser.instance.getUserFollowing(name)
            .enqueue(object : Callback<ArrayList<UserFollowingDatamodel>> {

                override fun onResponse(
                    call: Call<ArrayList<UserFollowingDatamodel>>,
                    response: Response<ArrayList<UserFollowingDatamodel>>
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
                    val list = response.body()

                    val userFollowing = list?.let {
                        FollowingAdapter(context, it)
                    }
                    binding!!.rvFollowing.adapter = userFollowing
                }

                override fun onFailure(
                    call: Call<ArrayList<UserFollowingDatamodel>>,
                    t: Throwable
                ) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }


            })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}