package com.example.bmta_semprace.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bmta_semprace.R
import com.example.bmta_semprace.viewModels.AppViewModel
import com.example.bmta_semprace.databinding.ActivityMainBinding
import com.example.bmta_semprace.models.Smoker
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONObject
import java.io.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var controller: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = readDataJson(this)
        controller = AppViewModel(data)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_relapses, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        ifUserAlreadyExists()
    }

    private fun ifUserAlreadyExists() {
        if (controller.smoker.name.isNullOrEmpty()) {
            startActivity(Intent(this, UserDataActivity::class.java))
            var data: Bundle? = intent.extras;
            if (data != null) {
                val smoker = controller.smoker
                smoker.name = data.getString("name")!!
                smoker.cigsPerDay = Integer.parseInt(data.getString("cigsPerDay")!!)
                smoker.packPrice = Integer.parseInt(data.getString("packPrice"))
                writeSmokerDataJson(smoker, this)
            }
        }
    }

    private fun readDataJson(context: Context): String {
        var string: String? = ""
        val stringBuilder = StringBuilder()
        val file = File(context.filesDir, "data.json")
        val fileReader = FileReader(file)
        val reader = BufferedReader(fileReader)
        while (true) {
            try {
                if (reader.readLine().also { string = it } == null) break
            } catch (e: IOException) {
                e.printStackTrace()
            }
            stringBuilder.append(string).append("\n")

        }
        reader.close()
        return stringBuilder.toString()
    }

    private fun writeSmokerDataJson(smoker: Smoker, context: Context) {
        var jsonObject =  JSONObject();
        jsonObject.put("name", smoker.name);
        jsonObject.put("packPrice", smoker.packPrice);
        jsonObject.put("cigsPerDay", smoker.cigsPerDay);

        val userString: String = jsonObject.toString()
        val file = File(context.filesDir, "data.json")
        val fileWriter = FileWriter(file)
        val bufferedWriter = BufferedWriter(fileWriter)
        bufferedWriter.write(userString)
        bufferedWriter.close()
    }
}