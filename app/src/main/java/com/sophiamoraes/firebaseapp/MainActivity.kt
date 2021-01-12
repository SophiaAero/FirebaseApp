package com.sophiamoraes.firebaseapp

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.common.io.Resources.getResource
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.HashMap
//private lateinit var database: DatabaseReference
data class Tomadas(
        var Codigo : Int? = null,
        var potencia : Int? = null,
        var preço : Int? = null,
        var tensao : Int? = null,
        var vc1 : Int? = null,
        var vc2 : Int? = null,
        var vc3 : Int? = null

)
var codigo : Int? = null
var list: ArrayList<Tomadas> = ArrayList()
var found : Tomadas? = null

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val databaseInstance = FirebaseDatabase.getInstance("https://agoravai-ac6f8-default-rtdb.firebaseio.com/")

        val myRef = databaseInstance.getReference("Tomadas")
        // Read from the database
        val entraCodigo : EditText = findViewById(R.id.codigoinput)
        val botao : Button = findViewById(R.id.button)
        val resposta : TextView = findViewById(R.id.resultado)


        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                dataSnapshot.children.forEach { child ->
                    // Extract Message object from the DataSnapshot
                    val tomada: Tomadas? = child.getValue<Tomadas>()
                    if (tomada != null) {
                        list?.add(tomada)
                    }
                }

            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(null, "Failed to read value.", error.toException())
            }
        })
        botao.setOnClickListener(){
            codigo= entraCodigo.text.toString().toInt()
            if (list != null) {
                for(v in list!!){
                    if(codigo == v.Codigo){
                        found = v
                    }
                }
            }
            if(found!= null){
                resposta.setText(found!!.potencia.toString())
            }else{
                resposta.setText(list?.size.toString())
            }

        }

    }

}




