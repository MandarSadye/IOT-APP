package com.example.mandar.hackerthon_app.DataResources

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * Created by mandar on 29-12-2017.
 */
class MyFirebaseInstanceIdService : FirebaseInstanceIdService() {
    companion object {
        var rrr :String? = null
    }
    private final var REG_TOKEN = "REG_TOKEN"
    override fun onTokenRefresh() {
        super.onTokenRefresh()
        var RecentToken : String? = FirebaseInstanceId.getInstance().getToken()
        Log.d(REG_TOKEN,RecentToken)
        rrr = RecentToken
    }
}