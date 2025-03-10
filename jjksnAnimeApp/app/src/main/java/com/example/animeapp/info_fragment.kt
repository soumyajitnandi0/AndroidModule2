package com.example.animeapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class info_fragment:Fragment(R.layout.info_fragment) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: info_adapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.infoFragmentMainActivity)

        val info = listOf(
            info_data("Gojo Satoru", R.drawable.gojo,
                "The most powerful sorcerer, possessing the Six Eyes and Limitless Cursed Technique. " +
                        "He is charismatic, confident, and serves as a mentor to Yuji and his friends."
            ),
            info_data("Geto Suguru", R.drawable.geto,
                "Once Gojo’s best friend, he turned into a villain believing that only sorcerers should exist. " +
                        "He is highly skilled and manipulates cursed spirits to fight."
            ),
            info_data("Yuji Itadori", R.drawable.yujiitadori,
                "A kind-hearted and physically strong high schooler who becomes the vessel for Ryomen Sukuna. " +
                        "Despite his burden, he strives to save people and uphold justice."
            ),
            info_data("Nobara Kugisaki", R.drawable.nobarakugisaki,
                "A determined and fearless sorcerer with a unique fighting style using a hammer and nails. " +
                        "She values her own individuality and refuses to be underestimated."
            ),
            info_data("Maki Zenin", R.drawable.makizenin,
                "Born into the prestigious Zenin Clan but lacking cursed energy, she makes up for it with " +
                        "incredible physical strength and weapon mastery, striving to prove her worth."
            ),
            info_data("Kento Nanami", R.drawable.kentonanami,
                "A serious and disciplined sorcerer who left a corporate job to fight curses. " +
                        "He uses the Ratio Technique to deliver precise and deadly attacks."
            ),
            info_data("Ryomen Sukuna", R.drawable.ryoumensukuna,
                "An ancient and extremely powerful curse, known as the King of Curses. " +
                        "He is ruthless, manipulative, and enjoys causing destruction."
            ),
            info_data("Toge Inumaki", R.drawable.togeinumaki,
                "A sorcerer from the Inumaki Clan who uses Cursed Speech, allowing him to command opponents " +
                        "with his words. He communicates with safe phrases to avoid unintended effects."
            ),
            info_data("Megumi Fushiguro", R.drawable.megumifushiguro,
                "A quiet and strategic sorcerer who uses the Ten Shadows Technique to summon powerful shikigami. " +
                        "He aims to surpass his limits and protect those he cares about."
            ),
            info_data("Toji Fushiguro", R.drawable.tojifushiguro,
                "A legendary assassin with no cursed energy but enhanced physical abilities due to Heavenly Restriction. " +
                        "He is feared for his combat skills and deadly precision."
            ),
            info_data("Mei Mei", R.drawable.meimei,
                "A professional sorcerer and mercenary, known for her strategic mind and combat skills. " +
                        "She uses crows imbued with cursed energy to fight."
            ),
            info_data("Mahito", R.drawable.mahito,
                "A cruel and sadistic curse who can manipulate souls, altering bodies at will. " +
                        "He enjoys tormenting humans and views them as mere experiments for his power."
            )
        )

        adapter = info_adapter(info)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}