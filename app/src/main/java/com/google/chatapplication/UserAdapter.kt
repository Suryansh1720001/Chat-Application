package com.google.chatapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class UserAdapter(val context: Context, val userList: ArrayList<User>):RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    class UserViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val txtName = itemView.findViewById<TextView>(R.id.text_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val CurrentUser = userList[position]
        holder.txtName.text = CurrentUser.name

     holder.itemView.setOnClickListener{
         val intent  = Intent(context,chatActivity::class.java)
         intent.putExtra("name",CurrentUser.name)
         intent.putExtra("uid",CurrentUser.uid)


         context.startActivities(arrayOf(intent))

     }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}