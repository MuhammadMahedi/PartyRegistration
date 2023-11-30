package com.example.partyregistration.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.partyregistration.R
import com.example.partyregistration.adapters.PartyAdapter
import com.example.partyregistration.data.Party
import com.example.partyregistration.databinding.ActivityPartyListBinding
import com.example.partyregistration.viewModels.PartyViewModel
import dagger.hilt.android.AndroidEntryPoint
import hilt_aggregated_deps._com_example_partyregistration_di_DBProvider

@AndroidEntryPoint
class PartyListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPartyListBinding
    private val viewModel: PartyViewModel by viewModels()
    private lateinit var partyAdapter: PartyAdapter
    var list: List<Party>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPartyListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()

        //list = viewModel.allParties.value
        viewModel.getAllParties()
        setTheScreen()

        val list = intent.getParcelableArrayListExtra<Party>("PartyList")
        if (list != null) {
            val adapter = PartyAdapter(this, list)

            //showDetailsOrDelete(adapter)

            //binding.rvPartyList.adapter = adapter


        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setToolbar() {
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

    fun setTheScreen() {
        viewModel.allParties.observe(this@PartyListActivity) {
            list = it
            if (list != null) {
                partyAdapter = PartyAdapter(this, list!!)
                binding.rvPartyList.adapter = partyAdapter
                showDetailsOrDelete(partyAdapter)
            }


        }
    }

    private fun showDetailsOrDelete(adapter: PartyAdapter) {
        adapter.setOnClickListener(object : PartyAdapter.OnClickListener {
            override fun onClick(position: Int, model: Party) {
                val intent = Intent(this@PartyListActivity, PartyDetailsActivity::class.java)
                intent.putExtra("PartyDetails", model)
                startActivity(intent)
                finish()
            }

            override fun onDeleteClick(model: Party) {
                //Toast.makeText(requireContext(), "ddddd", Toast.LENGTH_SHORT).show()
                AlertDialog.Builder(this@PartyListActivity)
                    .setMessage("Are You Sure you Want to delete this note?")
                    .setPositiveButton("Yes") { _, _ ->
                        viewModel.deleteData(model)
                        //viewModel.getAllNotes()
                        //      setTheScreen()
                        Toast.makeText(
                            this@PartyListActivity,
                            "deleted Successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                    }.setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            }

        })
    }
}
