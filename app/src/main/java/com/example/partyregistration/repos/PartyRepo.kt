package com.example.partyregistration.repos

import com.example.partyregistration.data.Party
import com.example.partyregistration.data.PartyDao
import javax.inject.Inject

class PartyRepo @Inject constructor(private val dao: PartyDao) {

    fun getAllParties():List<Party>{
        return dao.getAllParties()
    }

    suspend fun deleteParty(party: Party){
        dao.deleteParty(party)
    }

   suspend fun addParty(party: Party){
        dao.createParty(party)
    }

    suspend fun updateParty(party: Party){
        dao.updateParty(party)
    }

    fun checkDuplicate(name: String, email: String, phone: String): Int {
        return dao.checkDuplicate(name, email, phone)
    }

    fun checkNameExist(name: String): Int{
        return dao.checkNameExist(name)
    }

    fun checkEmailExist(email: String): Int{
        return dao.checkEmailExist(email)
    }

    fun checkPhoneExist(phone: String): Int{
        return dao.checkPhoneExist(phone)
    }

}