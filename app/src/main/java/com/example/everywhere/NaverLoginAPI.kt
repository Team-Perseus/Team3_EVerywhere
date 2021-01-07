package com.example.everywhere

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import kotlinx.android.synthetic.main.activity_naver_login.*

class NaverLoginAPI : AppCompatActivity() {
    private val naver_client_id="Ps6u6aQD0DWvw8Y7mERB"
    private val naver_client_secret="gbnKnK0GYI"
    private val naver_client_name="EVerywhere"

    private lateinit var mOAuthLoginInstance : OAuthLogin
    private lateinit var mContext : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_naver_login)

        mContext = this

        mOAuthLoginInstance = OAuthLogin.getInstance()
        mOAuthLoginInstance.init(mContext, naver_client_id, naver_client_secret, naver_client_name)

        btn_naver_login.setOAuthLoginHandler(mOAuthLoginHandler)
    }
    val mOAuthLoginHandler: OAuthLoginHandler = object : OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if (success) {
                //val accessToken: String = mOAuthLoginModule.getAccessToken(baseContext)
                //val refreshToken: String = mOAuthLoginModule.getRefreshToken(baseContext)
                //val expiresAt: Long = mOAuthLoginModule.getExpiresAt(baseContext)
                //val tokenType: String = mOAuthLoginModule.getTokenType(baseContext)
                //var intent = Intent(this, )
            } else {
                val errorCode: String = mOAuthLoginInstance.getLastErrorCode(mContext).code
                val errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext)

                Toast.makeText(
                    baseContext, "errorCode:" + errorCode
                            + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}