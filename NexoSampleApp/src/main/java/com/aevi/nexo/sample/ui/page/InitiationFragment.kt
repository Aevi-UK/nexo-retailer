package com.aevi.nexo.sample.ui.page

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.aevi.android.rxmessenger.ChannelClient
import com.aevi.android.rxmessenger.Channels
import com.aevi.nexo.sample.R
import com.aevi.nexo.sample.data.ScenarioController
import com.aevi.nexo.sample.databinding.FragmentInitBinding
import com.aevi.nexo.sample.ui.PagerViewModel
import com.aevi.ui.library.DropDownHelper
import kotlinx.android.synthetic.main.fragment_init.*
import kotlinx.android.synthetic.main.fragment_init.view.*

class InitiationFragment : Fragment() {

    private lateinit var viewModel: PagerViewModel
    private lateinit var messengerClient: ChannelClient

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
        messengerClient = Channels.pipe(view.context, SERVICE)
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
            messengerClient
                    .sendMessage("Magic!")
                    .subscribe({ response ->
                        Log.i(TAG, "Response received $response")
                    }, {
                        Log.w(TAG, "Failed to send message", it)
                    })
        }
    }

    companion object {
        private val TAG = InitiationFragment::class.simpleName
        private val SERVICE = ComponentName("com.aevi.nexo", "com.aevi.nexo.service.NexlatorInitiatorService")
    }
}
