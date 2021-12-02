package no.usn.gruppe4.crmwebappandroid.models

import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class Tools {
    fun verifyCheck(element: TextInputEditText): Boolean{
        if (element.length() < 1){
            element.error = "Required!"
            return false
        }else{
            return true
        }
    }
    fun formatDate(date: Date): String{
        val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY)
        return fmt.format(date)
    }

    fun timeIndexFormat(timeindex: Int): String{
        var res = ""
        val clockM = timeindex % 60
        val clockH = (timeindex - clockM) / 60

        if (clockH < 10){
            res += "0$clockH:"
        }else{
            res += "$clockH:"
        }
        if (clockM < 10) {
            res += "0$clockM"
        }else{
            res += "$clockM"
        }
        return res
    }
}