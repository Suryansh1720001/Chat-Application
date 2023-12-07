package com.google.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class chatActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox : EditText
    private lateinit var sendButton : ImageButton
    private lateinit var messageAdapter: messageAdapter
    private lateinit var messagelist : ArrayList<message>
    private lateinit var mDbRef : DatabaseReference

     var receiverRoom: String ?=null
     var  senderRoom: String ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        mDbRef = FirebaseDatabase.getInstance().getReference()

        val name= intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom = receiverUid+ senderUid
        receiverRoom = senderUid+receiverUid



        supportActionBar?.title = name
        chatRecyclerView = findViewById(R.id.chatRecycleView)
        messageBox = findViewById(R.id.edtmessBox)
        sendButton = findViewById(R.id.sendButton)

        messagelist = ArrayList()
        messageAdapter = messageAdapter(this,messagelist)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter= messageAdapter

        // logic for adding data to recycler view
        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messagelist.clear()

                    for(postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(message::class.java)
                        messagelist.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        // adding the message to the database firebase
        sendButton.setOnClickListener{

            val message = messageBox.text.toString()
            val messageObject = message(message,senderUid)

            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }

            messageBox.setText("")

        }





    }
}