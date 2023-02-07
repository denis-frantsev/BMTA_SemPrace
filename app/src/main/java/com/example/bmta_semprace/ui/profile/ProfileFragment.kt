package com.example.bmta_semprace.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bmta_semprace.databinding.FragmentProfileBinding
import com.example.bmta_semprace.viewModels.ProfileViewModel
import com.example.bmta_semprace.views.UserDataActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this)[ProfileViewModel::class.java]
        resultLauncher = activityResultLauncherInitializer()

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.txtName
        profileViewModel.smoker.observe(viewLifecycleOwner) {
            textView.text = it.name
            binding.txtCigs.text = it.cigsPerDay.toString()
            binding.txtPriceProfile.text = it.packPrice.toString()
        }

        binding.btnEdit.setOnClickListener {
            val intent = Intent(requireContext(), UserDataActivity::class.java)
            resultLauncher.launch(intent)

        }

        return root
    }

    private fun activityResultLauncherInitializer() =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Bundle? = result.data?.extras
                if (data != null) {
                    profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
                    val smoker = profileViewModel.smoker.value
                    if (smoker != null) {
                        smoker.name = data.getString("name")!!
                        smoker.cigsPerDay = Integer.parseInt(data.getString("cigsPerDay")!!)
                        smoker.packPrice = Integer.parseInt(data.getString("packPrice")!!)
                        profileViewModel.updateSmokerDataJson(smoker, requireContext())
                    }
                }
            }
        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}