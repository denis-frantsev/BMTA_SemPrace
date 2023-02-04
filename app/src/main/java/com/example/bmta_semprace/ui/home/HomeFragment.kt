package com.example.bmta_semprace.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bmta_semprace.databinding.FragmentHomeBinding
import com.example.bmta_semprace.models.Smoker
import com.example.bmta_semprace.viewModels.SmokingTimerViewModel
import com.example.bmta_semprace.views.MainActivity
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.util.concurrent.TimeUnit

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var smokingTimerViewModel: SmokingTimerViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var smoker = (activity as MainActivity).controller.smoker

        smokingTimerViewModel =
            ViewModelProvider(this).get(SmokingTimerViewModel::class.java)

        smokingTimerViewModel.getElapsedTime().observe(viewLifecycleOwner) {
            val elapsedDays = TimeUnit.MILLISECONDS.toDays(it)
            val elapsedHours =
                TimeUnit.MILLISECONDS.toHours(it) - TimeUnit.DAYS.toHours(elapsedDays)
            val elapsedMinutes =
                TimeUnit.MILLISECONDS.toMinutes(it) -
                        TimeUnit.HOURS.toMinutes(elapsedHours) -
                        TimeUnit.DAYS.toMinutes(elapsedDays)

            binding.txtDays.text = elapsedDays.toString()
            binding.txtHours.text = elapsedHours.toString()
            binding.txtMinutes.text = elapsedMinutes.toString()

            smoker.updateElapsedTime(it)
            binding.txtMoneySaved.text = "${smoker.moneySaved} Kč"
            binding.txtCigarettes.text = smoker.cigsNotSmoked.toString()
            binding.txtPacks.text = smoker.packs.toString()
        }

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnStop.setOnClickListener {
            showConfirmationDialog()
        }

        binding.btnMotivation.setOnClickListener {
            showSettingMotivationDialog()
        }

        if (!smoker.motivation.isNullOrEmpty()) {
            binding.txtMotivation.text = smoker.motivation
        }

        return root
    }

    private fun showSettingMotivationDialog() {
        val input = EditText(this.context)
        AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setTitle("Motivation")
            .setMessage("Set your motivation")
            .setView(input)
            .setNegativeButton("Cancel") { _, _ ->
            }
            .setPositiveButton("Set") { _, _ ->
                val motivationText = input.text.toString()
                var smoker = (activity as MainActivity).controller.smoker
                smoker.motivation = motivationText
                writeSmokerDataJson(smoker, this.requireContext())
                binding.txtMotivation.text = motivationText
            }
            .show()
    }

    private fun showConfirmationDialog() {
        val input = EditText(this.context)
        AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setTitle("Relapse confirmation")
            .setMessage("You really smoked again? If so, enter the reason and click 'Yes'.")
            .setView(input)
            .setNegativeButton("No, miss clicked") { _, _ ->
            }
            .setPositiveButton("Yes") { _, _ ->
                val reasonText = input.text.toString()
                smokingTimerViewModel.stopTimer(reasonText)
                smokingTimerViewModel.startTimer()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun writeSmokerDataJson(smoker: Smoker, context: Context) {
        var jsonObject = JSONObject()
        jsonObject.put("name", smoker.name)
        jsonObject.put("packPrice", smoker.packPrice)
        jsonObject.put("cigsPerDay", smoker.cigsPerDay)
        jsonObject.put("motivation", smoker.motivation)

        val userString: String = jsonObject.toString()
        val file = File(context.filesDir, "data.json")
        val fileWriter = FileWriter(file)
        val bufferedWriter = BufferedWriter(fileWriter)
        bufferedWriter.write(userString)
        bufferedWriter.close()
    }
}