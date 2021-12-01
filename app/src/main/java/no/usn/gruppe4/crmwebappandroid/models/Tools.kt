package no.usn.gruppe4.crmwebappandroid.models

import com.google.android.material.textfield.TextInputEditText

class Tools {
    fun verifyCheck(element: TextInputEditText): Boolean{
        if (element.length() < 1){
            element.error = "Required!"
            return false
        }else{
            return true
        }
    }
}