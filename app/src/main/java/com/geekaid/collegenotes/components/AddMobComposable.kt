package com.geekaid.collegenotes.components

import android.app.Activity
import android.content.Context
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

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 24.dp),
        factory = { context ->
            AdView(context).apply {
                adSize = bannerAdSize
                adUnitId = context.getString(R.string.ad_id_banner)
                loadAd(AdRequest.Builder().build())
            }
        }
    )

}


object InterstitialAdShow {

    fun showInterstitialAd(
        activity: Activity,
        dashboardViewModel: DashboardViewModel,
        adUnitId: String
    ) {

        if (adUnitId == activity.getString(R.string.ad_id_submit_interstitial)) {
            if (dashboardViewModel.mInterstitialAdSubmit != null)
                dashboardViewModel.mInterstitialAdSubmit?.show(activity)

            Timber.i("submit")
            loadInterstitialSubmit(
                context = activity,
                dashboardViewModel = dashboardViewModel,
                adUnitId = adUnitId
            )
        } else {
            if (dashboardViewModel.mInterstitialAdDownload != null)
                dashboardViewModel.mInterstitialAdDownload?.show(activity)

            Timber.i("download")
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

