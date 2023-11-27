package com.example.partyregistration.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.partyregistration.R
import com.example.partyregistration.adapters.PartyAdapter
import com.example.partyregistration.data.Party
import com.example.partyregistration.databinding.ActivityPartyListBinding
import com.example.partyregistration.viewModels.PartyViewModel
import hilt_aggregated_deps._com_example_partyregistration_di_DBProvider

class PartyListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPartyListBinding
    private val viewModel : PartyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPartyListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()

        val list = intent.getParcelableArrayListExtra<Party>("PartyList")
        if(list != null){
           val adapter = PartyAdapter(this,list)

        binding.rvPartyList.adapter = adapter

        adapter.setOnClickListener(object :PartyAdapter.OnClickListener{
            override fun onClick(position: Int, model: Party) {
                val intent = Intent(this@PartyListActivity, PartyDetailsActivity::class.java)
                intent.putExtra("PartyDetails",model)
                startActivity(intent)
            }

            override fun onDeleteClick(model: Party) {
                TODO("Not yet implemented")
            }

        })
        }

        binding.fabAdd.setOnClickListener{
            finish()
        }
    }

    private fun setToolbar(){
        val toolbar = binding.detailsToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Registered Parties" // Clear default title
            setDisplayHomeAsUpEnabled(true) // Show back button
        }
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}