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

        repo.addParty(party)
        getAllParties()
    }

    fun deleteData(party: Party){

            repo.deleteParty(party)
            getAllParties()

    }

    fun updateData(party: Party){

            repo.updateParty(party)
            getAllParties()

    }

    fun checkDuplicate(name: String ,email: String, phone: String): Int{
        return repo.checkDuplicate(name, email, phone)
    }


    fun getAllParties(){
        _allParties.postValue(repo.getAllParties())
    }


}