package com.example.rolemanager

data class SkillData(val name : String, val modifier : String, val bonus : Int) {

    companion object{
        val collectionName : String = "skills"
        val skillFieldName : String = "skill"
        val modifierFieldName : String = "modifier"
        val bonusFieldName : String = "bonus"
    }

}