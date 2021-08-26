package com.example.stubhubchallenge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stubhubchallenge.PhotosRepository
import com.example.stubhubchallenge.R
import com.example.stubhubchallenge.model.PhotoResponse
import com.example.stubhubchallenge.ui.adapter.PhotosAdapter
import com.example.stubhubchallenge.viewmodel.PhotosViewModel
import com.example.stubhubchallenge.viewmodel.PhotosViewModelFactory

class MainActivity : AppCompatActivity() {
    private val viewmodelFactory by lazy {
        PhotosViewModelFactory(PhotosRepository())
    }
    private val viewmodel by lazy {
        ViewModelProvider(this, viewmodelFactory).get(PhotosViewModel::class.java)
    }

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: PhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = findViewById(R.id.photos_rv)
        mRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        viewmodel.photosLiveData?.observe(this,
            Observer<PhotoResponse> {
                Log.d("photo_response", it.hits[0].user)
                mAdapter = PhotosAdapter(it.hits)
                mRecyclerView.adapter = mAdapter
            }
        )

        viewmodel.getPhotos("tiger")
    }
}
