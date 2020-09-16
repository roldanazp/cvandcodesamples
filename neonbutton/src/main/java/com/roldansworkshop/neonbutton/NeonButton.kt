package com.roldansworkshop.neonbutton

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import kotlin.properties.Delegates

class NeonButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var widthSize = 0
    private var heightSize = 0

    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap

    private val backgroundRect = RectF()
    private val backgroundPaint = Paint()

    private val pressedRect = RectF()
    private val pressedPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var decoDx = 0F
    private var decoDy = 0F

    private val decoWidth = context.resources.getDimension(R.dimen.button_deco_width)
    private val decoHeight = context.resources.getDimension(R.dimen.button_deco_height)

    private val decoProjectionDelta = 1F
    private val decoMotionDelta = decoWidth * 6.0F

    private var pressedWidthMax = 0.0F
    private var pressedWidthPercentage = 0.0F

    /**
     * Attributes
     */
    private var text: CharSequence
    private var textSize: Float
    private var color: Int

    var buttonState: ButtonState by
        Delegates.observable<ButtonState>(ButtonState.Idle) { p, old, new -> updateState(new) }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.actionMasked) {
            ACTION_DOWN -> {
                buttonState = ButtonState.Pressed
                return true
            }
            ACTION_UP -> {
                val rect = Rect(left, top, right, bottom)
                if (!rect.contains(
                        left + event.x.toInt(),
                        top + event.y.toInt()
                    )
                ) {
                    buttonState = ButtonState.Idle
                }else{
                    buttonState = ButtonState.Clicked
                    performClick()
                }
                return false
            }
        }
        return false
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.NeonButton,
            0, 0).apply {
            try {
                text = resources.getText(
                    getResourceId(R.styleable.NeonButton_text, R.string.button_default_text))
                textSize = resources.getDimension(
                    getResourceId(R.styleable.NeonButton_textSize, R.dimen.default_text_size))
                color = context.getColor(
                    getResourceId(R.styleable.NeonButton_neonColor, R.color.colorNeonGreen))
            } finally {
                recycle()
            }
        }
    }

    private fun updateState(buttonState:ButtonState){
        when(buttonState){
            ButtonState.Clicked -> {
            }
            ButtonState.Pressed -> {
            }
            ButtonState.Idle -> {
            }
        }
    }

    private fun updateExtras(){
        extraCanvas.drawColor(context.getColor(android.R.color.black))
        onDrawDeco(extraCanvas)
        onDrawPressedCircle(extraCanvas)
        onDrawText(extraCanvas)
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        updateExtras()
        canvas?.drawBitmap(extraBitmap, 0f, 0f, null)
    }

    private fun onDrawDeco(canvas: Canvas){

        backgroundPaint.color = color
        backgroundRect.right = decoWidth
        backgroundRect.bottom = decoHeight
        canvas.save()

        var transition = Pair(decoDx, decoDy)
        var position = Pair(decoDx, decoDy)

        for (i in 1..300) {
            canvas.translate(transition.first, transition.second)
            canvas.drawRect(backgroundRect, backgroundPaint)
            transition = nextDecoDelta(position, decoProjectionDelta)
            position = Pair(position.first + transition.first, position.second + transition.second)

        }

        transition = nextDecoDelta(Pair(decoDx, decoDy), decoMotionDelta)
        decoDx += transition.first
        decoDy += transition.second

        canvas.restore()

    }

    private fun onDrawPressedCircle(canvas: Canvas){

        if ( pressedWidthPercentage < 1 && buttonState == ButtonState.Pressed ) pressedWidthPercentage += 0.08F
        else if ( pressedWidthPercentage > 0 && pressedWidthPercentage < 1 && buttonState != ButtonState.Pressed ) pressedWidthPercentage += 0.08F
        else if ( pressedWidthPercentage >= 1 && buttonState != ButtonState.Pressed ) pressedWidthPercentage = 0.0F

        val circleDiameter = pressedWidthMax * pressedWidthPercentage

        pressedPaint.color = color
        pressedRect.right = circleDiameter
        pressedRect.bottom = circleDiameter
        canvas.save()
        canvas.translate((widthSize-circleDiameter)*0.5f, (heightSize-circleDiameter)*0.5f)
        canvas.drawArc(pressedRect, -90f, 360f, true, pressedPaint)
        canvas.restore()

    }


    private fun nextDecoDelta(position: Pair<Float, Float>, delta:Float): Pair<Float, Float>{
        val verticalEnd = (heightSize.toFloat() - decoHeight)
        val horizontalEnd = (widthSize.toFloat() - decoWidth)
        if(position.first <= 0F && position.second < verticalEnd){
            return Pair(0F, if (position.second + delta <= verticalEnd) delta else (verticalEnd - position.second))
        }else if(position.first < horizontalEnd && position.second >= verticalEnd){
            return Pair(if (position.first + delta <= horizontalEnd) delta else (horizontalEnd - position.first), 0F)
        }else if(position.first >= horizontalEnd && position.second > 0) {
            return Pair(0F, if (position.second - delta >= 0) -delta else -position.second)
        }else{// if(position.first > 0 && position.second <= 0F) {
            return Pair(if (position.first - delta >= 0) -delta else -position.first, 0F)
        }
    }

    private fun onDrawText(canvas: Canvas){
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.color = if (buttonState != ButtonState.Pressed) color else context.getColor(android.R.color.black)
        textPaint.textSize = textSize
        canvas.save()
        canvas.translate((widthSize/2).toFloat(), (heightSize / 2 - (textPaint.descent() + textPaint.ascent()) / 2))
        canvas.drawText(text.toString(), 0f, 0f, textPaint)
        canvas.restore()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if(::extraBitmap.isInitialized) extraBitmap.recycle()
        extraBitmap = Bitmap.createBitmap(widthSize, heightSize, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        updateState(buttonState)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        pressedWidthMax = widthSize * 1.5F
        setMeasuredDimension(w, h)
    }

}