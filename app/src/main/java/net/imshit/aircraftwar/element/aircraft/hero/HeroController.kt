package net.imshit.aircraftwar.element.aircraft.hero

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.VibrationEffect
import android.os.VibratorManager
import android.view.MotionEvent
import android.view.View
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.sign

class HeroController(private val context: Context) : SensorEventListener, View.OnTouchListener {
    var x = 0f
        private set
    var y = 0f
        private set
    private var vx = 0f
    private var vy = 0f

    private var isTouching = false
    private var touchX = 0f
    private var touchY = 0f

    private var gravityAx = 0f
    private var gravityAy = 0f

    private lateinit var game: View
    private val sensorManager =
        this.context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val gravitySensor = this.sensorManager.run {
        getDefaultSensor(Sensor.TYPE_GRAVITY) ?: getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }
    private val vibrator =
        (this.context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator

    fun init(game: View) {
        this.game = game
        this.x = game.width / 2f
        this.y = game.height / 2f
        // 触摸屏
        game.setOnTouchListener(this)
    }

    fun start() {
        gravitySensor?.let {
            sensorManager.registerListener(
                this, it, SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    fun stop() {
        if (gravitySensor != null) {
            sensorManager.unregisterListener(this)
        }
    }

    fun calcForward(timeMs: Int) {
        if (isTouching) {
            val diffX = (touchX - x) / game.width * 20
            val diffY = (touchY - y) / game.width * 20
            vx = 0.05f * (diffX.absoluteValue).pow(2) * diffX.sign
            vy = 0.05f * (diffY.absoluteValue).pow(2) * diffY.sign
        } else {
            vx += gravityAx * 0.01f
            vy += gravityAy * 0.01f
        }
        x += vx * timeMs
        y += vy * timeMs
        if (x < 0f) {
            x = 0f
            vx = 0f
        } else if (x > game.width) {
            x = game.width.toFloat()
            vx = 0f
        }
        if (y < 0f) {
            y = 0f
            vy = 0f
        } else if (y > game.height) {
            y = game.height.toFloat()
            vy = 0f
        }
    }

    fun onHit() {
        vibrator.vibrate(
            VibrationEffect.createPredefined(VibrationEffect.EFFECT_DOUBLE_CLICK)
        )
    }

    // 处理重力事件
    override fun onSensorChanged(event: SensorEvent?) {
        this.gravityAx = -(event?.values?.get(0) ?: 0.0f)
        this.gravityAy = event?.values?.get(1) ?: 0.0f
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    // 处理触摸事件
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        v?.performClick()
        event?.let {
            when (it.action) {
                MotionEvent.ACTION_UP -> {
                    isTouching = false
                }

                else -> {
                    isTouching = true
                    touchX = it.x
                    touchY = it.y
                }
            }
        }
        return true
    }
}