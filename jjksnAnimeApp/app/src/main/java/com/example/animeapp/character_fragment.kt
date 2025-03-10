package com.example.animeapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class character_fragment:Fragment(R.layout.characters_fragment) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: character_adapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.charactersFragmentMainActivity)

        val characters = listOf(
            character_data("Gojo Saturo",R.drawable.gojo),
            character_data("Geto Suguru",R.drawable.geto),
            character_data("Yuji Itadori",R.drawable.yujiitadori),
            character_data("Nobara Kugisaki",R.drawable.nobarakugisaki),
            character_data("Maki Zenin",R.drawable.makizenin),
            character_data("Kento Nanami",R.drawable.kentonanami),
            character_data("Ryomen Sukuna", R.drawable.ryoumensukuna),
            character_data("Toge Inumaki",R.drawable.togeinumaki),
            character_data("Megumi Fushiguro",R.drawable.megumifushiguro),
            character_data("Toji Fushiguro",R.drawable.tojifushiguro),
            character_data("Mei Mei",R.drawable.meimei),
            character_data("Mahito",R.drawable.mahito),
        )
        adapter = character_adapter(characters)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}