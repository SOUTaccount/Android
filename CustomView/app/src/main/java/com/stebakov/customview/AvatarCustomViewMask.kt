package com.stebakov.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toRectF
import com.stebakov.customview.extentions.dpToPx

class AvatarCustomViewMask @JvmOverloads constructor(
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

    private val maskPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val viewRect = Rect()
    private lateinit var resultBm: Bitmap
    private lateinit var maskBm: Bitmap
    private lateinit var srcBm: Bitmap

    init {
        if (attrs != null){
            val ta = context.obtainStyledAttributes(attrs,R.styleable.AvatarCustomViewMask)
            borderWidth = ta.getDimension(
                R.styleable.AvatarCustomViewMask_acvm_borderWidth,
                context.dpToPx(DEFAULT_BORDER_WIDTH)
            )
            borderColor = ta.getColor(R.styleable.AvatarCustomViewMask_acvm_borderColor,
                DEFAULT_BORDER_COLOR)
            initials = ta.getString(R.styleable.AvatarCustomViewMask_acvm_initials) ?: "??"
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
        prepareBitmaps(w, h)
    }

    private fun prepareBitmaps(w: Int, h: Int) {
        maskBm = Bitmap.createBitmap(w,h,Bitmap.Config.ALPHA_8)
        resultBm = maskBm.copy(Bitmap.Config.ARGB_8888,true)
        val maskCanvas = Canvas(maskBm)
        maskCanvas.drawOval(viewRect.toRectF(),maskPaint)
        maskPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        srcBm = drawable.toBitmap(w,h,Bitmap.Config.ARGB_8888)

        val resultCanvas = Canvas(resultBm)
        resultCanvas.drawBitmap(maskBm,viewRect,viewRect,null)
        resultCanvas.drawBitmap(srcBm,viewRect,viewRect,maskPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val initSize = resolveDefaultSize(widthMeasureSpec)
        setMeasuredDimension(initSize,initSize)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(resultBm, viewRect, viewRect, null)
        val half = (borderWidth/2).toInt()
        viewRect.inset(half,half)
        canvas.drawOval(viewRect.toRectF(),borderPaint)
    }

    private fun setup() {
        with(maskPaint){
            color = Color.GREEN
            style = Paint.Style.FILL
        }

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