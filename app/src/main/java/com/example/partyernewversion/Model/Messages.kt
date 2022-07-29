package com.example.partyernewversion.Model

import com.google.firebase.database.Exclude
import com.google.firebase.database.ServerValue

class Messages(
    val loginUserOwner:String? = null,
    val textMessage:String? = null,
    val timeSend: Any? = null) {

    var createdTimestamp:Any? = timeSend
        set(value) {
            field = ServerValue.TIMESTAMP
        }
        @Exclude
        get() {
            if(timeSend == null){
                return 0
            } else {
                return timeSend as Long
            }
        }
}