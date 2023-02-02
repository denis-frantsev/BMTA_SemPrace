package com.example.bmta_semprace.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bmta_semprace.databinding.ActivityUserDataBinding

class UserDataActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUserDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            val name = binding.editName.text.toString()
            val cigsPerDay = binding.editCigsPerDay.text.toString()
            val packPrice = binding.editPrice.text.toString()
            if(name.isEmpty() || name == "Name"){
                Toast.makeText(this, "Fill in the name field, please.", Toast.LENGTH_LONG).show()
            } else if (cigsPerDay == "0" || cigsPerDay.isEmpty()){
                Toast.makeText(this, "Fill in the amount of cigarettes you smoke per day, please.", Toast.LENGTH_LONG).show()
            } else if (packPrice == "0" || cigsPerDay.isEmpty()) {
                Toast.makeText(this, "Fill in the price field, please.", Toast.LENGTH_LONG).show()
            } else {
                var newAct = Intent(this, MainActivity::class.java)
                newAct.putExtra("name", name)
                newAct.putExtra("cigsPerDay",cigsPerDay)
                newAct.putExtra("packPrice",packPrice)
                startActivity(newAct)
            }
        }
    }
}