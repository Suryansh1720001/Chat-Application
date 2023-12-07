package com.google.chatapplication

import android.content.IntentSender

class message {

    var message: String?=null
    var senderId:String?=null
    constructor(){}

    constructor(message: String? ,senderID:String?){
        this.message = message
        this.senderId = senderID
    }
}