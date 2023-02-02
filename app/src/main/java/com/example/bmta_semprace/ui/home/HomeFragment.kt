package com.example.bmta_semprace.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bmta_semprace.databinding.FragmentHomeBinding
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
        smokingTimerViewModel =
            ViewModelProvider(this).get(SmokingTimerViewModel::class.java)

        smokingTimerViewModel.getElapsedTime().observe(viewLifecycleOwner) {
            val elapsedDays = TimeUnit.MILLISECONDS.toDays(it)
            val elapsedHours =
                TimeUnit.MILLISECONDS.toHours(it) - TimeUnit.DAYS.toHours(elapsedDays)
            val elapsedMinutes =
                TimeUnit.MILLISECONDS.toMinutes(it)-
                TimeUnit.HOURS.toMinutes(elapsedHours) -
                TimeUnit.DAYS.toMinutes(elapsedDays)

            binding.txtDays.text = elapsedDays.toString()
            binding.txtHours.text = elapsedHours.toString()
            binding.txtMinutes.text = elapsedMinutes.toString()
        }

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnStop.setOnClickListener {
            showConfirmationDialog()
        }
//        var smoker = (activity as MainActivity).controller.smoker
//        var smoker = smokingTimerViewModel.getSmoker()
//        binding.txtDays.text = smoker.name
        return root
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
}