package com.stebakov.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toRectF
import com.stebakov.customview.extentions.dpToPx

class AvatarCustomViewShader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): androidx.appcompat.widget.AppCompatImageView(context,attrs,defStyleAttr) {

    companion object{
        private const val DEFAULT_SIZE = 150
        private const val DEFAULT_BORDER_WIDTH = 2
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
    }

    @Px
    var borderWidth: Float = context.dpToPx(DEFAULT_BORDER_WIDTH)
    @ColorInt
    private var borderColor: Int = Color.WHITE
    private var initials: String = "??"

    private val avatarPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val viewRect = Rect()

    init {
        if (attrs != null){
            val ta = context.obtainStyledAttributes(attrs, R.styleable.AvatarCustomViewShader)
            borderWidth = ta.getDimension(
                R.styleable.AvatarCustomViewShader_acvs_borderWidth,
                context.dpToPx(DEFAULT_BORDER_WIDTH)
            )
            borderColor = ta.getColor(
                R.styleable.AvatarCustomViewShader_acvs_borderColor,
                DEFAULT_BORDER_COLOR
            )
            initials = ta.getString(R.styleable.AvatarCustomViewShader_acvs_initials) ?: "??"
        }
        scaleType = ScaleType.CENTER_CROP
        setup()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w == 0) return
        with(viewRect){
            left = 0
            top = 0
            right = w
            bottom = h
        }
        prepareShader(w, h)
    }

    private fun prepareShader(w: Int, h: Int) {
        val srcBm = drawable.toBitmap(w,h,Bitmap.Config.ARGB_8888)
        avatarPaint.shader = BitmapShader(srcBm,Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val initSize = resolveDefaultSize(widthMeasureSpec)
        setMeasuredDimension(initSize,initSize)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawOval(viewRect.toRectF(),avatarPaint)
        val half = (borderWidth/2).toInt()
        viewRect.inset(half,half)
        canvas.drawOval(viewRect.toRectF(),borderPaint)
    }

    private fun setup() {

        with(borderPaint){
            style = Paint.Style.STROKE
            strokeWidth = borderWidth
            color = borderColor
        }
    }

    private fun resolveDefaultSize(spec: Int): Int{
        return when(MeasureSpec.getMode(spec)){
            MeasureSpec.AT_MOST ->context.dpToPx(DEFAULT_SIZE).toInt()
            MeasureSpec.EXACTLY ->MeasureSpec.getSize(spec)
            MeasureSpec.UNSPECIFIED ->MeasureSpec.getSize(spec)
            else -> MeasureSpec.getSize(spec)
        }
    }
}