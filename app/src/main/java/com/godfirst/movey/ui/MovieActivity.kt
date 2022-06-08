package com.godfirst.movey.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.godfirst.movey.adapter.MoveyRecyclerAdapter
import com.godfirst.movey.databinding.ActivityMovieBinding
import com.godfirst.movey.models.Movey
import com.godfirst.movey.models.MoveyList
import com.godfirst.movey.services.MoveyService
import com.godfirst.movey.services.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding
    private var requestList: List<Movey> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.titleText.text = intent.getStringExtra("title") ?: "Movey"

        binding.backImage.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.moveyRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MovieActivity)
        }

        makeNetworkRequest()

        val connectivityManager: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val builder: NetworkRequest.Builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(
                builder.build(),
                object : ConnectivityManager.NetworkCallback() {

                    override fun onAvailable(network: Network) {
                        lifecycleScope.launch {
                            connectionAvailable(true)
                        }
                    }

                    override fun onLost(network: Network) {
                        lifecycleScope.launch {
                            connectionAvailable(false)
                        }
                    }
                }
            )
        }
    }

    private fun makeNetworkRequest() {
        binding.progressBar.visibility = View.VISIBLE
        val moveyService: MoveyService = RetrofitBuilder().buildService(MoveyService::class.java)
        val moveyRequest: Call<MoveyList> =
            if (intent.getStringExtra("title").equals("Trending Movies")) {
                moveyService.getTopRatedMovies()
            } else {
                moveyService.getTrendingMovies()
            }
        moveyRequest.enqueue(object : Callback<MoveyList> {
            override fun onResponse(call: Call<MoveyList>, response: Response<MoveyList>) {
                requestList = response.body()!!.results
                if (response.isSuccessful) {
                    binding.moveyRecycler.adapter = MoveyRecyclerAdapter(requestList)
                }
                binding.progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<MoveyList>, t: Throwable) {
                Toast.makeText(this@MovieActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                binding.infoText.text = t.message
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    private suspend fun connectionAvailable(isConnected: Boolean) {
        withContext(Dispatchers.Main) {
            if (isConnected) {
                if (requestList.isEmpty()) {
                    makeNetworkRequest()
                }
            }
        }
    }
}