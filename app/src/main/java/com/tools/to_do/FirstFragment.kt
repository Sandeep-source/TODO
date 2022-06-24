package com.tools.to_do

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tools.to_do.databinding.FragmentFirstBinding
import com.tools.to_do.db.To_Do

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var  viewModel:ViewModel;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        viewModel=ViewModelProvider(this).get(ViewModel::class.java)
        binding.fab.setOnClickListener({
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        })

        viewModel.setBinding(binding)
        viewModel.dao.liveToDos.observe(viewLifecycleOwner,{list->
            viewModel.setBinding(binding)
        })
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}