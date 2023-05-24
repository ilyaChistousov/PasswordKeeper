package chistousov.ilya.passwordkeeper.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import chistousov.ilya.passwordkeeper.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}