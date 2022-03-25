package com.example.newsapp


import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest



class MainActivity : AppCompatActivity(), NewsItemClicked {

    private  lateinit var mAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val review=findViewById<RecyclerView>(R.id.recyclerView)
        review.layoutManager= LinearLayoutManager(this)
        fetchData()
        mAdapter=NewsAdapter(this)
        review.adapter=mAdapter


    }

    private fun fetchData(){

        val url = "https://gnews.io/api/v4/search?q=example&token=596639ab67cd0e67910475997ea9328c"

// Request a string response from the provided URL.
        val jsonObjectRequest=JsonObjectRequest(
            Request.Method.GET,url,null,
            {
                val newJSONArray=it.getJSONArray("articles")

                val newArray= ArrayList<News>()
                for (i in 0 until newJSONArray.length()){
                    val newJsonObject=newJSONArray.getJSONObject(i)

                    val news=News(
                        newJsonObject.getString("title"),
                        newJsonObject.getString("source"),
                        newJsonObject.getString("url"),
                        newJsonObject.getString("image")
                    )
                    newArray.add(news)
                }
                mAdapter.updateNews(newArray)
            },
            {

            }

        )
// Add the request to the RequestQueue.
     MySingleton.MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    override fun onItemClicked(item: News) {

        val builder= CustomTabsIntent.Builder();
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));

    }
}