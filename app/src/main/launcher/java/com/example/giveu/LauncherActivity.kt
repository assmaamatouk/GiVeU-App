package com.example.giveu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.giveu.ui.MainActivity

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState); startActivity(
            Intent(
                this,
                MainActivity::class.java
            )
        ); finish()
    }
}
