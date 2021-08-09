package com.adit.poskologistikapp.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.adit.poskologistikapp.activities.DetailBencanaActivity
import com.adit.poskologistikapp.activities.PoskoMasyarakatActivity
import com.adit.poskologistikapp.activities.PoskoActivity
import com.adit.poskologistikapp.adapters.BencanaSliderAdapter
import com.adit.poskologistikapp.adapters.onClickBencanaSliderAdapter
import com.adit.poskologistikapp.contracts.HomeFragmentContract
import com.adit.poskologistikapp.databinding.FragmentHomeBinding
import com.adit.poskologistikapp.models.Bencana
import com.adit.poskologistikapp.presenters.HomeFragmentPresenter
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView

class HomeFragment : Fragment(), HomeFragmentContract.HomeFragmentView{
    private lateinit var _binding : FragmentHomeBinding
    private val binding get() = _binding
    private lateinit var adapterBencanaSliderAdapter: BencanaSliderAdapter
    private var presenter : HomeFragmentContract.HomeFragmentPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        presenter = HomeFragmentPresenter(this)
        buttonClick()
        return binding.root
    }

    override fun attachToSlider(bencana: List<Bencana>) {
        adapterBencanaSliderAdapter = BencanaSliderAdapter(bencana, object : onClickBencanaSliderAdapter{
            override fun detail(bencana: Bencana) {
                val intent = Intent(activity, DetailBencanaActivity::class.java).apply {
                    putExtra("BENCANA_DETAIL", bencana)
                }

                startActivity(intent)
            }

        })
        binding.imageSlider.apply {
            setSliderAdapter(adapterBencanaSliderAdapter)
            setIndicatorAnimation(IndicatorAnimationType.WORM)
            setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
            indicatorSelectedColor = Color.WHITE
            indicatorUnselectedColor = Color.GRAY
            scrollTimeInSec = 4 //set scroll delay in seconds :
            startAutoCycle()
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.loadingHomeFragment.isIndeterminate = true
    }

    override fun hideLoading() {
        binding.loadingHomeFragment.apply {
            isIndeterminate = false
            progress = 0
            visibility = View.GONE
        }
    }

    private fun infoBencana(){
        presenter?.infoBencana()
    }

    private fun buttonClick(){
        binding.posko.setOnClickListener {
            startActivity(Intent(requireActivity(), PoskoActivity::class.java))
        }

        binding.donatur.setOnClickListener {
            startActivity(Intent(requireActivity(), PoskoMasyarakatActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        infoBencana()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }




}