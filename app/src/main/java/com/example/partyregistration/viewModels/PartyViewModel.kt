package com.example.partyregistration.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partyregistration.data.Party
import com.example.partyregistration.repos.PartyRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartyViewModel @Inject constructor(var repo: PartyRepo)  :  ViewModel(){

    private var _allParties: MutableLiveData<List<Party>> = MutableLiveData<List<Party>>()
    val allParties: LiveData<List<Party>> get() = _allParties

    fun addData(party: Party){
        viewModelScope.launch {
            repo.addParty(party)
        }

    }

    fun deleteData(party: Party){
            viewModelScope.launch {
                repo.deleteParty(party)
                getAllParties()
            }
    }

    fun updateData(party: Party){
            viewModelScope.launch{
                repo.updateParty(party)
                getAllParties()
            }
    }

    fun checkDuplicate(name: String ,email: String, phone: String): Int{
        return repo.checkDuplicate(name, email, phone)
    }

    fun checkNameExist(name: String): Int{
        return repo.checkNameExist(name)
    }
    fun checkEmailExist(email: String): Int{
        return repo.checkEmailExist(email)
    }

    fun checkPhoneExist(phone: String): Int{
        return repo.checkPhoneExist(phone)
    }


    fun getAllParties(){
        _allParties.postValue(repo.getAllParties())
    }


}