package com.akbar.suitmediatestaplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.akbar.suitmediatestaplication.adapter.UserAdapter
import com.akbar.suitmediatestaplication.api.RetrofitClient
import com.akbar.suitmediatestaplication.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ThirdScreenActivity : AppCompatActivity() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private val userList: MutableList<User> = mutableListOf()
    private var currentPage = 1
    private val perPage = 5
    private val REQUEST_CODE_THIRD_SCREEN = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_screen)

        // Set up the toolbar
        val toolbarFormData: Toolbar = findViewById(R.id.tb_thirdScreen)
        setSupportActionBar(toolbarFormData)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.recyclerView)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        // Initialize userAdapter before setting it as the adapter for recyclerView
        userAdapter = UserAdapter(userList) { user ->
            // Handle item click here
            Toast.makeText(this@ThirdScreenActivity, "Clicked on ${user.first_name} ${user.last_name}", Toast.LENGTH_SHORT).show()

            val intent = Intent(this@ThirdScreenActivity, SecondScreenActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter

        // Set up SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            refreshData()
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!swipeRefreshLayout.isRefreshing) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 5) {
                        // Load the next page when scrolling to the bottom (adjust the threshold as needed)
                        loadNextPage()
                    }
                }
            }
        })

        // Load initial data
        fetchData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun refreshData() {
        currentPage = 1
        userList.clear()
        fetchData()
    }

    private fun loadNextPage() {
        // Increment the current page for pagination
        currentPage++
        fetchData()
    }

    private fun fetchData() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                // Make the API call with updated page and perPage
                val response = RetrofitClient.apiService.getUsers(currentPage, perPage)
                if (response.isSuccessful) {
                    // Update UI on the main thread
                    MainScope().launch {
                        val userResponse = response.body()
                        if (userResponse != null) {
                            // Add the new users to the existing list
                            userList.addAll(userResponse.data)

                            // Notify the adapter that the data set has changed
                            userAdapter.notifyDataSetChanged()

                            // If using SwipeRefreshLayout, indicate that the refresh is complete
                            swipeRefreshLayout.isRefreshing = false
                        }
                    }
                } else {
                    // Handle error
                    MainScope().launch {
                        swipeRefreshLayout.isRefreshing = false
                        // Show an error message or handle as needed
                    }
                }
            } catch (e: Exception) {
                // Handle exception
                MainScope().launch {
                    swipeRefreshLayout.isRefreshing = false
                    // Show an error message or handle as needed
                }
            }
        }
    }


}
