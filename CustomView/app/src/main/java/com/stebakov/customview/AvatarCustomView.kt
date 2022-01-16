package com.stebakov.customview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.core.animation.doOnRepeat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toRectF
import com.stebakov.customview.extentions.dpToPx
import kotlin.math.max
import kotlin.math.truncate

class AvatarCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): androidx.appcompat.widget.AppCompatImageView(context,attrs,defStyleAttr) {

    companion object{
        private const val DEFAULT_SIZE = 150
        private const val DEFAULT_BORDER_WIDTH = 2
        private const val DEFAULT_BORDER_COLOR = Color.WHITE

        private val bgColors = arrayOf(
            Color.parseColor("#FF8000"),
            Color.parseColor("#5A009D"),
            Color.parseColor("#00FF00")
        )
    }

    @Px
    var borderWidth: Float = context.dpToPx(DEFAULT_BORDER_WIDTH)
    @ColorInt
    private var borderColor: Int = Color.WHITE
    private var initials: String = "??"

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val avatarPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val initialsPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val viewRect = Rect()
    private val borderRect = Rect()
    private var size = 0

    private var isAvatarMode = true

    init {
        if (attrs != null){
            val ta = context.obtainStyledAttributes(attrs, R.styleable.AvatarCustomView)
            borderWidth = ta.getDimension(
                R.styleable.AvatarCustomView_acv_borderWidth,
                context.dpToPx(DEFAULT_BORDER_WIDTH)
            )
            borderColor = ta.getColor(
                R.styleable.AvatarCustomView_acv_borderColor,
                DEFAULT_BORDER_COLOR
            )
            initials = ta.getString(R.styleable.AvatarCustomView_acv_initials) ?: "??"
            ta.recycle()
        }
        scaleType = ScaleType.CENTER_CROP
        setup()
        setOnClickListener{
            handleLongClick()
        }
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
//отрисовываем изображение с помощью шейдера
    private fun prepareShader(w: Int, h: Int) {
        if (w == 0 || drawable == null) return
        val srcBm = drawable.toBitmap(w,h, Bitmap.Config.ARGB_8888)
        avatarPaint.shader = BitmapShader(srcBm, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val initSize = resolveDefaultSize(widthMeasureSpec)
        setMeasuredDimension(max(initSize, size),max(initSize, size))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (drawable != null && isAvatarMode) drawAvatar(canvas) else drawInitials(canvas)
        val half = (borderWidth/2).toInt()
        borderRect.set(viewRect)
        borderRect.inset(half,half)
        canvas.drawOval(borderRect.toRectF(),borderPaint)
    }
//Сохраняем наши значения, для этого используем класс SavedState
    override fun onSaveInstanceState(): Parcelable? {
        val savedState = SavedState(super.onSaveInstanceState())
        savedState.isAvatarMode = isAvatarMode
        savedState.borderWidth = borderWidth
        savedState.borderColor = borderColor
        return savedState
    }
//Получаем значения, чтобы значения сохранялись и загружались необходимо дать id нашему view
    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState){
            super.onRestoreInstanceState(state)
            isAvatarMode = state.isAvatarMode
            borderWidth = state.borderWidth
            borderColor = state.borderColor
        } else {
            super.onRestoreInstanceState(state)
        }
    }
//вставляем картинку и отрисовываем в зависимости от типа изображения
    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        if (isAvatarMode) prepareShader(width,height)
    }
//вставляем картинку и отрисовываем в зависимости от типа изображения
    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        if (isAvatarMode) prepareShader(width,height)
    }
//вставляем картинку и отрисовываем в зависимости от типа изображения
    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        if (isAvatarMode) prepareShader(width,height)
    }
//инициализируем наши параметры
    private fun setup() {
        with(borderPaint){
            style = Paint.Style.STROKE
            strokeWidth = borderWidth
            color = borderColor
        }
    }
//Присваиваем значения нашему view, если они определены - берем их, если нет - ставим дефолт
    private fun resolveDefaultSize(spec: Int): Int{
        return when(MeasureSpec.getMode(spec)){
            MeasureSpec.AT_MOST ->context.dpToPx(DEFAULT_SIZE).toInt()
            MeasureSpec.EXACTLY ->MeasureSpec.getSize(spec)
            MeasureSpec.UNSPECIFIED ->MeasureSpec.getSize(spec)
            else -> MeasureSpec.getSize(spec)
        }
    }

    private fun drawAvatar(canvas: Canvas){
        canvas.drawOval(viewRect.toRectF(),avatarPaint)
    }
//отрисовываем наши инициалы в центре холста
    private fun drawInitials(canvas: Canvas){
        initialsPaint.color = initialsToColors(initials)
        canvas.drawOval(viewRect.toRectF(),initialsPaint)
        with(initialsPaint){
            color = Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = height * 0.33f
        }
        //offSetY = (верхняя граница текста + нижняя граница текста) / 2 , т.е. половина высоты текста
        val offSetY = (initialsPaint.ascent() + initialsPaint.descent())/2
        //отрисовываем текст по центру
        canvas.drawText(initials,viewRect.exactCenterX(),viewRect.exactCenterY() - offSetY,initialsPaint)
    }

    //высчитываем индекс в нашем массиве по введенному первому символу в тексте
    private fun initialsToColors(letters: String): Int{
        val b = letters[0].toByte()
        val len = bgColors.size
        val d = b/len.toDouble()
        val index = ((d- truncate(d)) * len).toInt()
        return bgColors[index]
    }

    private fun handleLongClick(): Boolean{
        val va = ValueAnimator.ofInt(width,width*2).apply {
            duration = 300
            interpolator = LinearInterpolator()
            repeatMode = ValueAnimator.REVERSE
            repeatCount = 1
        }

        va.addUpdateListener {
            size = it.animatedValue as Int
            requestLayout()
        }
        va.doOnRepeat { toggleMode() }
        va.start()
        return true
    }

    private fun toggleMode(){
        isAvatarMode = !isAvatarMode
        invalidate()
    }

//класс который реализует сохранение наших данных
    private class SavedState : BaseSavedState, Parcelable {
        var isAvatarMode : Boolean = true
        var borderWidth : Float = 0f
        var borderColor : Int = 0

        constructor(superState: Parcelable?) : super(superState)
        constructor(src: Parcel) : super(src) {
            isAvatarMode = src.readInt() == 1
            borderWidth = src.readFloat()
            borderColor = src.readInt()
        }

        override fun writeToParcel(dst: Parcel, flags: Int) {
            super.writeToParcel(dst, flags)
            dst.writeInt(if (isAvatarMode) 1 else 0)
            dst.writeFloat(borderWidth)
            dst.writeInt(borderColor)
        }

        override fun describeContents() = 0

        companion object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel) = SavedState(parcel)

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}