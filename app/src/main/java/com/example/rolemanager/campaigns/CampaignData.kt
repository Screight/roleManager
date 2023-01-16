package com.example.rolemanager.campaigns

import com.google.firebase.firestore.FirebaseFirestore

data class CampaignData(val name : String, val description : String, val backgroundColorHex : String){

    companion object{
        val collectionName : String = "campaigns"
        val nameFieldName  : String = "name"
        val descriptionFieldName : String = "description"
        val backgroundColorFieldName : String = "backgroundColorHex"
    }

}
