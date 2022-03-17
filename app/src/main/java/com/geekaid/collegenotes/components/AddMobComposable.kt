package com.geekaid.collegenotes.components

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.geekaid.collegenotes.R
import com.geekaid.collegenotes.viewmodel.DashboardViewModel
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import timber.log.Timber


@Composable
fun BannerAdComposable(bannerAdSize: AdSize = AdSize.LARGE_BANNER) {

    Log.i("admobtest","called")
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 24.dp),
        factory = { context ->
            AdView(context).apply {
                adSize = bannerAdSize
                adUnitId = "ca-app-pub-3017813434968451/7179902312"
                loadAd(AdRequest.Builder().build())
            }
        }
    )
    Log.i("admobtest","called again")

}


object InterstitialAdShow {

    fun showInterstitialAd(
        activity: Activity,
        dashboardViewModel: DashboardViewModel,
        adUnitId: String
    ) {

        if (adUnitId == "ca-app-pub-3017813434968451/7179902312") {
            if (dashboardViewModel.mInterstitialAdSubmit != null)
                dashboardViewModel.mInterstitialAdSubmit?.show(activity)

            loadInterstitialSubmit(
                context = activity,
                dashboardViewModel = dashboardViewModel,
                adUnitId = adUnitId
            )
        } else {
            if (dashboardViewModel.mInterstitialAdDownload != null)
                dashboardViewModel.mInterstitialAdDownload?.show(activity)

            loadInterstitialDownload(
                context = activity,
                dashboardViewModel = dashboardViewModel,
                adUnitId = adUnitId
            )
        }

    }
}


fun loadInterstitialSubmit(
    context: Context,
    dashboardViewModel: DashboardViewModel,
    adUnitId: String
) {
    InterstitialAd.load(
        context,
        adUnitId,
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                dashboardViewModel.mInterstitialAdSubmit = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                dashboardViewModel.mInterstitialAdSubmit = interstitialAd
            }
        }
    )
}

fun loadInterstitialDownload(
    context: Context,
    dashboardViewModel: DashboardViewModel,
    adUnitId: String
) {
    InterstitialAd.load(
        context,
        adUnitId,
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                dashboardViewModel.mInterstitialAdDownload = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                dashboardViewModel.mInterstitialAdDownload = interstitialAd
            }
        }
    )
}

