package com.example.practica9_247253

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private val userRef = FirebaseDatabase.getInstance().getReference("users")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var btnSave = findViewById<Button>(R.id.save_button) as Button
        btnSave.setOnClickListener {
            sameMarkFromForm()
        }

        userRef.addChildEventListener(object: ChildEventListener {
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1:String?) {
                val value = dataSnapshot.getValue()

                if (value is String) {
                } else if (value is User) {
                    val usuario = value

                    if(usuario!=null){writeMark(usuario)}
                }
            }
        })
    }

    private fun sameMarkFromForm(){
        var firstName :EditText= findViewById(R.id.et_name) as EditText
        var lastName: EditText= findViewById(R.id.et_lastName) as EditText
        var age: EditText= findViewById(R.id.et_age) as EditText

        val usuario=User(firstName.text.toString(),lastName.text.toString(),age.text.toString())

        userRef.push().setValue(usuario)
    }

    private fun writeMark(mark: User){
        var listV: TextView =findViewById(R.id.tvLista) as TextView
        val text = listV.text.toString() + mark.toString() + "\n"
        listV.text = text
    }
}