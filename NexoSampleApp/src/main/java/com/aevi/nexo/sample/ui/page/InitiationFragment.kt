package com.aevi.nexo.sample.ui.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.aevi.nexo.sample.data.ScenarioController
import com.aevi.nexo.sample.R
import com.aevi.nexo.sample.databinding.FragmentInitBinding
import com.aevi.nexo.sample.ui.PagerViewModel
import com.aevi.ui.library.DropDownHelper
import kotlinx.android.synthetic.main.fragment_init.*
import kotlinx.android.synthetic.main.fragment_init.view.*

class InitiationFragment : Fragment() {

    private lateinit var viewModel: PagerViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentInitBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_init, container, false)
        viewModel = ScenarioController.model
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindScenarioSelection(view)
        bindInitiationAction(view)
    }

    private fun bindScenarioSelection(view: View) {
        val dropDownHelper = DropDownHelper(activity)
        dropDownHelper.setupDropDown(scenarioSpinner, viewModel.scenarios, false)
        view.scenarioSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                viewModel.selectedScenario = -1
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.selectedScenario = position
            }
        }
    }

    private fun bindInitiationAction(view: View) {
        view.initiateButton.setOnClickListener {
            val message = ScenarioController.start()
            Toast.makeText(view.context, message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private val TAG = InitiationFragment::class.simpleName
    }
}
