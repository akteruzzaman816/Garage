package com.app.testapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.testapplication.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding


    // Dark view to add light effect
    private val shadowView: View by lazy {
        View(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(0x001F2F4F) // Using the equivalent RGBA color in hex
            alpha = 0.90f
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupLabel()
        setupShadowView()
        startShadowAnimation()

        Handler(Looper.getMainLooper()).postDelayed({
            goToDashboardActivity()
        }, 4000) // 4 seconds
    }

    private fun setupLabel() = with(binding) {
        label.setTextColor(
            ContextCompat.getColor(this@SplashActivity, R.color.white)
        ) // Assuming the color is defined in `colors.xml`

        // Basic animation similar to moving the label's center
        label.animate().apply {
            duration = 1000
            translationYBy(this@SplashActivity.resources.displayMetrics.widthPixels.toFloat()) // Move by a specific amount
            start() // Start the animation
        }

        label.alpha = 0f
        label.setShadowLayer(
            3f,
            30f,
            0f,
            ContextCompat.getColor(this@SplashActivity, R.color.black)
        ) // Adjust shadow properties
    }


    private fun setupShadowView() {
        // Obtain the root view of the activity
        val rootView = findViewById<ViewGroup>(android.R.id.content)
        // Add the shadow view to the root view
        rootView.addView(shadowView)
        // Bring the shadow view to the front
        shadowView.bringToFront()
    }

    private fun goToDashboardActivity() {
        Log.d("SplashActivity", "Navigating to Dashboard")

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startShadowAnimation() {
        animateShadow(0.75f, 0.3f) {
            animateShadow(1f, 0.2f, 22.5f) {
                animateShadow(1f, 0.2f, 45f) {
                    animateShadow(1f, 0.2f, 67.5f) {
                        animateShadow(1f, 0.2f, 90f) {
                            animateShadow(1f, 0.3f, 112.5f) {
                                animateShadow(1f, 0.1f, 135f)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun animateShadow(
        alpha: Float,
        shadowViewAlpha: Float,
        angle: Float? = null,
        completion: (() -> Unit)? = null
    ) = with(binding){
        label.animate().alpha(alpha).setDuration(300).setListener(null).start()

        angle?.let {
            val trig = calcTrig(Segment.H, 10f, it)
            label.setShadowLayer(
                3f,
                trig[Segment.X]!!,
                trig[Segment.Y]!!,
                ContextCompat.getColor(this@SplashActivity,R.color.black)
            )
        }

        shadowView.animate().alpha(shadowViewAlpha).setDuration(300).withEndAction {
            completion?.invoke()
        }.start()
    }

    private fun calcTrig(segment: Segment, size: Float, angle: Float): Map<Segment, Float> {
        val angleInRadians = Math.toRadians(angle.toDouble()).toFloat()
        return when (segment) {
            Segment.X -> {
                val x = size
                val y = (Math.tan(angleInRadians.toDouble()) * x).toFloat()
                val h = (x / Math.cos(angleInRadians.toDouble())).toFloat()
                mapOf(Segment.X to x, Segment.Y to y, Segment.H to h)
            }

            Segment.Y -> {
                val y = size
                val x = (y / Math.tan(angleInRadians.toDouble())).toFloat()
                val h = (y / Math.sin(angleInRadians.toDouble())).toFloat()
                mapOf(Segment.X to x, Segment.Y to y, Segment.H to h)
            }

            Segment.H -> {
                val h = size
                val x = (Math.cos(angleInRadians.toDouble()) * h).toFloat()
                val y = (Math.sin(angleInRadians.toDouble()) * h).toFloat()
                mapOf(Segment.X to x, Segment.Y to y, Segment.H to h)
            }
        }
    }
}

enum class Segment {
    X, Y, H
}