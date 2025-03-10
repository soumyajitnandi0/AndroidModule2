package com.example.animeapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class clips_fragment:Fragment(R.layout.clips_fragment) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: clips_adapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.clipsFragmentMainActivity)

        val reels = listOf(
            clips_data(R.raw.video1),
            clips_data(R.raw.video2),
            clips_data(R.raw.video3),
            clips_data(R.raw.video4),
            clips_data(R.raw.video5),
            clips_data(R.raw.video6),
            clips_data(R.raw.video7),
            clips_data(R.raw.video8),
            clips_data(R.raw.video9),
            clips_data(R.raw.video10),
            clips_data(R.raw.video11),
            clips_data(R.raw.video12)
        )

        adapter = clips_adapter(reels)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}