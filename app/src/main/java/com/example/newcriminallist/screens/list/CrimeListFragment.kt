package com.example.newcriminallist.screens.list

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newcriminallist.R
import com.example.newcriminallist.data.Crime
import com.example.newcriminallist.databinding.FragmentCrimeListBinding
import com.example.newcriminallist.screens.adapter.CrimeListAdapter
import kotlinx.coroutines.launch
import java.util.*

class CrimeListFragment : Fragment() {

    private lateinit var binding: FragmentCrimeListBinding
    private val crimeListViewModel: CrimeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCrimeListBinding.inflate(layoutInflater, container, false)

        binding.crimeRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_crime_list, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.new_crime -> {
                        showNewCrime()
                        true
                    }
                    else -> false
                }
            }

            private fun showNewCrime() {
                viewLifecycleOwner.lifecycleScope.launch {
                    val newCrime = Crime(
                        id = UUID.randomUUID(),
                        title = "",
                        date = Date(),
                        isSolved = false
                    )
                    crimeListViewModel.addCrime(newCrime)
                    val action = CrimeListFragmentDirections
                        .actionCrimeListFragmentToCrimeDetailFragment(newCrime.id)
                    findNavController().navigate(action)
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                crimeListViewModel.crimes.collect { crimes ->
                    binding.crimeRecyclerView.adapter = CrimeListAdapter(crimes,
                        onItemClicked = { crimeId ->
                            val action =
                                CrimeListFragmentDirections.actionCrimeListFragmentToCrimeDetailFragment(
                                    crimeId
                                )
                            findNavController().navigate(action)
                        },
                        onDeleteItem = { crimeId ->
                            val crime = Crime(crimeId, "", Date(), false)
                            viewLifecycleOwner.lifecycleScope.launch {
                                crimeListViewModel.deleteCrime(crime)
                            }
                        })
                }
            }
        }
    }
}