package com.app.testapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.testapplication.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var shadowView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shadowView = View(this).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            alpha = 0.90f
            setBackgroundColor(android.graphics.Color.argb(0, 31, 38, 79)) // Equivalent to UIColor(red: 0.12, green: 0.15, blue: 0.31, alpha: 0.00)
        }

        setupLabel()
        setupShadowView()
        startShadowAnimation()

        Handler(Looper.getMainLooper()).postDelayed({
            goToDashboardActivity()
        }, 4000) // 4 seconds
    }

    private fun setupLabel() = with(binding){
        label.setTextColor(android.graphics.Color.WHITE)
        label.alpha = 0f
        label.translationY += resources.displayMetrics.widthPixels

        label.setShadowLayer(3f, 30f, 0f, android.graphics.Color.BLACK)
    }

    private fun setupShadowView() {
        addContentView(shadowView, shadowView.layoutParams)
    }

    private fun goToDashboardActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startShadowAnimation() {
        animateShadow(0.75f, shadowViewAlpha = 0.3f) {
            animateShadow(1f, 22.5f, shadowViewAlpha = 0.2f) {
                animateShadow(1f, 45f, shadowViewAlpha = 0.2f) {
                    animateShadow(1f, 67.5f, shadowViewAlpha = 0.2f) {
                        animateShadow(1f, 90f, shadowViewAlpha = 0.2f) {
                            animateShadow(1f, 112.5f, shadowViewAlpha = 0.3f) {
                                animateShadow(1f, 135f, shadowViewAlpha = 0.1f)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun animateShadow(alpha: Float, angle: Float? = null, shadowViewAlpha: Float, completion: (() -> Unit)? = null) {
        val duration = when (angle) {
            135f -> 2000L
            112.5f -> 800L
            else -> 300L
        }

        binding.label.animate()
            .alpha(alpha)
            .setDuration(duration)
            .withEndAction {
                completion?.invoke()
            }
            .start()

        angle?.let {
            val trig = calcTrig(Segment.H, 10f, it)
            binding.label.setShadowLayer(3f, trig[Segment.X] ?: 0f, trig[Segment.Y] ?: 0f, android.graphics.Color.BLACK)
        }

        shadowView.animate()
            .alpha(shadowViewAlpha)
            .setDuration(duration)
            .start()
    }

    private fun calcTrig(segment: Segment, size: Float, angle: Float): Map<Segment, Float> {
        return when (segment) {
            Segment.X -> {
                val x = size
                val y = Math.tan(Math.toRadians(angle.toDouble())).toFloat() * x
                val h = x / Math.cos(Math.toRadians(angle.toDouble())).toFloat()
                mapOf(Segment.X to x, Segment.Y to y, Segment.H to h)
            }
            Segment.Y -> {
                val y = size
                val x = y / Math.tan(Math.toRadians(angle.toDouble())).toFloat()
                val h = y / Math.sin(Math.toRadians(angle.toDouble())).toFloat()
                mapOf(Segment.X to x, Segment.Y to y, Segment.H to h)
            }
            Segment.H -> {
                val h = size
                val x = Math.cos(Math.toRadians(angle.toDouble())).toFloat() * h
                val y = Math.sin(Math.toRadians(angle.toDouble())).toFloat() * h
                mapOf(Segment.X to x, Segment.Y to y, Segment.H to h)
            }
        }
    }
}

enum class Segment {
    X, Y, H
}