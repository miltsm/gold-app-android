package com.syamil.izzat.goldappsample.view

import android.os.Bundle
import android.text.Editable
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import com.syamil.izzat.goldappsample.R
import com.syamil.izzat.goldappsample.databinding.ActivityHomeBinding
import com.syamil.izzat.goldappsample.domain.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.math.BigDecimal

class HomeActivity : BaseActivity(), NavController.OnDestinationChangedListener {

    protected lateinit var binding : ActivityHomeBinding

    private val homeVM by viewModels<HomeViewModel>()

    private val navigator by lazy {
        Navigation.findNavController(this, R.id.home_host)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityHomeBinding.inflate(layoutInflater).apply {
            binding = this
            setContentView(root)
        }

        //navigator.addOnDestinationChangedListener(this)
        homeVM.loadRates()

        binding.conversionCard.apply {
            topupBtn.apply {
                text = getString(R.string.topup)
                setOnClickListener {
                    navigator.apply {
                        try {
                            navigate(R.id.action_homeFragment_to_topUpFragment)
                        } catch (e: Exception) {
                            if (currentDestination?.id != R.id.topUpFragment)
                                navigate(R.id.topUpFragment)
                        }
                    }
                }
            }

            gramEt.addTextChangedListener {
                homeVM.mutableGram.value = try {
                    it.toString().toDoubleOrNull() ?: 0.0
                } catch (e: Exception) {
                    1.0
                }
            }

            lifecycleScope.launch {
                homeVM.mutableGram.collectLatest {
                    basePriceEt.text = Editable.Factory.getInstance().newEditable(
                        homeVM.formatPrice(
                            //homeVM.response.value.data?.getPriceByGram(it) ?: BigDecimal.ZERO
                            it.times(homeVM.response.value.data?.goldPricePerGrams ?: 62.29).toBigDecimal()
                        )
                    )

                    topupBtn.isEnabled = it > 0.0
                }
            }
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
//        binding.conversionCard.topupBtn.apply {
//            //val isTopUpFragment = controller.currentDestination?.id == R.id.topUpFragment
//            text = getString(R.string.topup)
//            setOnClickListener {
//                navigator.apply {
//                    try {
//                        navigate(R.id.action_homeFragment_to_topUpFragment)
//                    } catch (e: Exception) {
//                        if (currentDestination?.id != R.id.topUpFragment)
//                            navigate(R.id.topUpFragment)
//                    }
//                }
//            }
//        }
    }
}