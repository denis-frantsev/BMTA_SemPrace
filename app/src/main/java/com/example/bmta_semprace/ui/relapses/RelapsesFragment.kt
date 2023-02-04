package com.example.bmta_semprace.ui.relapses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bmta_semprace.databinding.FragmentRelapsesBinding
import com.example.bmta_semprace.viewModels.RelapsesViewModel
import com.example.bmta_semprace.views.RelapseAdapter

class RelapsesFragment : Fragment() {

    private lateinit var relapseRecyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RelapseAdapter
    private lateinit var relapseViewModel: RelapsesViewModel
    private var _binding: FragmentRelapsesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRelapsesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize ViewModel
        relapseViewModel =
            ViewModelProvider(this).get(RelapsesViewModel::class.java)
        // Initialize RecyclerView
        viewManager = LinearLayoutManager(this.context)
        viewAdapter = RelapseAdapter(relapseViewModel.relapseList.value!!)
        binding.txtRelapse.text = "You relapsed ${viewAdapter.itemCount} times"

        relapseRecyclerView = binding.relapseRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        // Observe data changes in the ViewModel
        relapseViewModel.relapseList.observe(viewLifecycleOwner, Observer { relapses ->
            relapses?.let {
                viewAdapter.updateRelapseList(it)
                binding.txtRelapse.text = "You relapsed ${viewAdapter.itemCount} times"
            }
        })

        binding.btnRefresh.setOnClickListener{
            relapseViewModel.updateRelapseList()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}