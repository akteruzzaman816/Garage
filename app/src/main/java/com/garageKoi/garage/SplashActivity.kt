package com.garageKoi.garage

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import com.garageKoi.garage.base.BaseActivity
import com.garageKoi.garage.databinding.ActivitySplashBinding
import com.garageKoi.garage.utils.SharedPref

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    // Dark view to add light effect
    private val shadowView: View by lazy {
        View(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(0x00000000) // Using the equivalent RGBA color in hex
            alpha = 1f
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupLabel()
        setupShadowView()
        startShadowAnimation()
    }

    private fun setupLabel() = with(binding) {
        label.setTextColor(
            ContextCompat.getColor(this@SplashActivity, R.color.white)
        ) // Assuming the color is defined in `colors.xml`

        label.post {
            // Get the screen height
            val screenHeight = this@SplashActivity.resources.displayMetrics.heightPixels
            // Calculate the Y position for centering the label
            val centerY = (screenHeight / 2) - (label.height / 2)

            // Animate the label to the calculated center position
            label.animate().apply {
                duration = 1000
                translationY(centerY.toFloat()) // Move to the center of the screen
                start() // Start the animation
            }
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
        // check already logged in or not
        if (SharedPref.read(SharedPref.JWT_TOKEN,"")?.isNotEmpty() == true){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun startShadowAnimation() {
        val animationDuration = 2000L // Shorter duration for faster animation
        val angleSteps = 135 // Total angle steps (from 0 to 135 degrees)

        val animator = ValueAnimator.ofFloat(0f, angleSteps.toFloat()).apply {
            duration = animationDuration
            interpolator = AccelerateDecelerateInterpolator() // Smooth transition
            addUpdateListener { animation ->
                val angle = animation.animatedValue as Float
                val normalizedAngle = angle % 180 // Normalize angle for shadow animation

                // Calculate shadow properties based on the angle
                val trig = calcTrig(Segment.H, 20f, normalizedAngle)
                val shadowAlpha = 1f - (angle / angleSteps.toFloat()) // Fade out effect

                with(binding) {
                    label.alpha = 1f // Keep label fully visible
                    label.setShadowLayer(
                        3f,
                        trig[Segment.X]!!,
                        trig[Segment.Y]!!,
                        ContextCompat.getColor(this@SplashActivity, R.color.black)
                    )

                    shadowView.alpha = shadowAlpha
                }
            }
            start()
        }

        // Optionally, you can set a listener to navigate to the next activity after animation ends
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
//                goToDashboardActivity()
            }
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
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