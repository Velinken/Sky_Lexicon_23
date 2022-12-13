package com.dinadurykina.skylexicon.ui.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import com.dinadurykina.skylexicon.databinding.FragmentSkyAboutBinding
import com.dinadurykina.skylexicon.launcher.SkyApplication
import com.dinadurykina.skylexicon.ui.observeEvent


class SkyAboutFragment : Fragment() {
    private lateinit var binding: FragmentSkyAboutBinding
   // private lateinit var viewModel: SkyAboutViewModel
    private val viewModel by viewModels<SkyAboutViewModel>()
    // viewModel = ViewModelProvider(this).get(SkyAboutViewModel::class.java)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSkyAboutBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.myName = viewModel.myName
        binding.history = (requireContext().applicationContext as SkyApplication).skyHistory

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // событие кнопку Done
       /* viewModel.keyBoard.observe(viewLifecycleOwner , EventObserver { show ->
            show?.let {show ->
                if (show) showKeyboard()
                else  hideKeyboard()
            }
        })*/
        viewModel.starClicked.observeEvent(viewLifecycleOwner) {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        }
        viewModel.keyBoard.observeEvent(viewLifecycleOwner) {
            it?.let {
                if (it) showKeyboard()
                else hideKeyboard()
            }
        }


       /* viewModel.starClicked.observe(viewLifecycleOwner, EventObserver{
            Toast.makeText(activity,it,Toast.LENGTH_LONG).show()
        })*/

    }


    private fun hideKeyboard() {
        binding.apply {
            invalidateAll()   // обновить экран
            val imm = getSystemService(nicknameEdit.context, InputMethodManager::class.java)
            imm!!.hideSoftInputFromWindow(nicknameEdit.windowToken, 0)
        }
    }
    private fun showKeyboard () {
        binding.apply {
            val imm = getSystemService(nicknameEdit.context, InputMethodManager::class.java)
            imm!!.showSoftInput(nicknameEdit, 0)

        }
    }
}