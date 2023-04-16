package com.dicoding.picodiploma.loginwithanimation.view.main

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityMainBinding
import com.dicoding.picodiploma.loginwithanimation.model.UserPreference
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.welcome.WelcomeActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                binding.nameTextView.text = getString(R.string.greeting, user.name)
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }

    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            mainViewModel.logout()
        }
    }
}

// Android provide framework to accommodate property animation
// API Property Animation is within `android.animation`

// Animator is the base class to make animation. But commonly, the subclass is the one that is used. There is 3 subclass:
// - ValueAnimator: provide a machine to `manage animation time & value` (time, shape, position, etc), also set the `target object`
// - ObjectAnimator: subclass of ValueAnimator. It allows to decide the `animation object` & `property value` to make ObjectAnimator instance
// - AnimatorSet: supports mechanism to merge animation

// Evaluator decide how an animation property counts the given value. These are the evaluator:
// - IntEvaluator: default evaluator to count Int value
// - FloatEvaluator: default evaluator to count Float value
// - ArgbEvaluator: default evaluator to count color & hexadecimal value
// - TypeEvaluator: Allows developer to make their own evaluator

// Interpolator: to set property value within the series of animation. Types of Interpolator:
// - Anticipate Interpolator: changes starts from back then thrown to front
// - Overshoot Interpolator: changes shoots to front, to last value, then back
// - Decelerate Interpolator: Interpolator with high rate to slow
// - Anticipate Overshoot Interpolator: Changes starts from back, shoots to front beyond target, then back to last value
// - Bounce Interpolator: changes bounced in last value
// - Cycle Interpolator: animation being repeated within a number of cycle
// - Linear Interpolator: animation with fixed rate of change
// - Accelerate Decelerate Interpolator: animation with slow start-end rate, but fast in the middle
// - accelerate Interpolator: rate is gradually increased
// --- Infographic: https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/academy/dos:0f130b48a558cb4d7deb9307b6ae260b20220124163445.jpeg

// Property in Animation:
// - View.ALPHA: set transparency of View
// - View.ROTATION: set rotation in clockwise
// - View.SCALE_X: scale View by x coordinate
// - View.SCALE_YL scale View by y coordinate
// - View.TRANSLATION_X: Move to X by certain amount
// - View.TRANSLATION_Y: Move to Y by certain amount

// ValueAnimator components:
// - TimeInterpolator: define interpolation of the animation
// - TypeEvaluator: count the value of property when animation is going
// - int duration: set animation duration
// - int startPropertyValue: set initial value of property when being run
// - int endPropertyValue: set final value of property after being run
// - start(): to start animation

// TimeInterpolator works by `modifying elapsed fraction` with `interpolation value`. Example:
// - before interpolated: https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/academy/dos:d012cee307379cde58102c81f2c212e720220124165135.jpeg
// - after interpolated: https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/academy/dos:ed57a036fe51035496a3a883a71e1f9220220124165135.jpeg