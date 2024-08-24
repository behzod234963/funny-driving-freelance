package me.funnydriving.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import me.funnydriving.R
import kotlin.math.abs

@SuppressLint("ViewConstructor")
class GameVisuals(private val ctx:Context, private val gameRules: GameRules):View(ctx) {

    private var painter: Paint? = null
    private var gameSpeed = 1
    private var time = 0
    private var score = 0
    private var mainCarPosition = 0
    private val bootCars = ArrayList<HashMap<String,Any>>()
    private var viewWidth = 0
    private var viewHeight = 0
    private var isFinished = false

    init {

        painter = Paint()

    }

    @SuppressLint("DrawAllocation", "UseCompatLoadingForDrawables")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight
        if (time % 700 < 10 + gameSpeed){
            val map = HashMap<String,Any>()
            map["lanes"] = (0..2).random()
            map["startingTime"] = time
            bootCars.add(map)
        }
        time += 10 + gameSpeed
        val carWidth = viewWidth/5
        val carHeight = carWidth + 10
        painter!!.style = Paint.Style.FILL
        val mainCarDrawableResources = resources.getDrawable(R.drawable.ic_main_car)
        mainCarDrawableResources.setBounds(
            mainCarPosition * viewWidth / 3 + viewHeight / 20 + 5,
            viewHeight -2 - carHeight,
            mainCarPosition * viewWidth / 3 + viewWidth / 20 + carWidth - 5,
            viewHeight-2
        )
        mainCarDrawableResources.draw(canvas)
        painter!!.color = Color.GREEN
        var highScore = 0

        for(car in bootCars.indices){
            try {

                val carX = bootCars[car]["lanes"] as Int * viewWidth / 3 + viewWidth / 15
                val carY = time - bootCars[car]["startingTime"] as Int
                val bootCarsDrawableRes = resources.getDrawable(R.drawable.ic_boot_car)
                bootCarsDrawableRes.setBounds(
                    carX + 25,carY - carHeight,carX + carWidth - 25,carY
                )
                bootCarsDrawableRes.draw(canvas)
                if (bootCars[car]["lanes"] as Int == mainCarPosition){
                    if (carY > viewHeight -2 - carHeight && carY < viewHeight - 2){
                        isFinished = true
                        gameRules.finishGame(score)
                    }
                }
                if (carY > viewHeight + carHeight){
                    bootCars.removeAt(car)
                    score++
                    gameSpeed = 1 + abs(score/8)
                    if (score > highScore){
                        highScore = score
                    }
                }

            }catch (exception:Exception){
                Log.d("exception", "${exception.printStackTrace()} ")
            }
        }
        painter!!.color = Color.WHITE
        painter!!.textSize = 40f
        canvas.drawText("Счёт : $score",80f,80f, painter!!)
        canvas.drawText("Скорость : $gameSpeed",380f,80f, painter!!)
        if (isFinished){
            canvas.drawText("Игра окончена!!!",380f,880f, painter!!)
        }
        invalidate()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_DOWN->{
                val horizontalMove = event.x
                if (horizontalMove < viewWidth/2){
                    if (mainCarPosition>0){
                        mainCarPosition--
                    }
                }
                if (horizontalMove > viewWidth/2){
                    if (mainCarPosition<2){
                        mainCarPosition++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP->{}
        }
        return true
    }
}