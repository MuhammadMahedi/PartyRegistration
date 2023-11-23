package com.example.partyregistration.repos

import com.example.partyregistration.data.Party
import com.example.partyregistration.data.PartyDao
import javax.inject.Inject

class PartyRepo @Inject constructor(private val dao: PartyDao) {

    fun getAllParties():List<Party>{
        return dao.getAllParties()
    }

    fun deleteParty(party: Party){
        dao.deleteParty(party)
    }

    fun addParty(party: Party){
        dao.createParty(party)
    }

    fun updateParty(party: Party){
        dao.updateParty(party)
    }

    fun checkDuplicate(name: String, email: String, phone: String): Int {
        return dao.checkDuplicate(name, email, phone)
    }

}