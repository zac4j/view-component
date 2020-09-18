package com.zac4j.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.floor
import kotlin.random.Random

/**
 * Create Effects with Shader
 *
 * @author: zac
 * @date: 2020/9/13
 */
class SpotLightImageView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

  private var paint = Paint()
  private var shouldDrawSpotLight = false
  private var gameOver = false

  private lateinit var droidRect: RectF
  private var droidBitmapX = 0F
  private var droidBitmapY = 0F

  private val bitmapDroid = BitmapFactory.decodeResource(
      resources,
      R.drawable.android
  )
  private val spotlight = BitmapFactory.decodeResource(resources, R.drawable.mask)

  private var shader: Shader

  private val shaderMatrix = Matrix()

  init {
    val bitmap = Bitmap.createBitmap(spotlight.width, spotlight.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val shaderPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    // Create a black bitmap texture
    shaderPaint.color = Color.BLACK
    canvas.drawRect(0F, 0F, spotlight.width.toFloat(), spotlight.height.toFloat(), shaderPaint)
    // use DST_OUT mode to mask out the spotlight from the black rectangle.
    shaderPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    canvas.drawBitmap(spotlight, 0F, 0F, shaderPaint)

    shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    paint.shader = shader
  }

  /**
   * Draw mask
   */
//  override fun onDraw(canvas: Canvas?) {
//    super.onDraw(canvas)
//
//    shaderMatrix.setTranslate(
//        100F,
//        550F
//    )
//    shader.setLocalMatrix(shaderMatrix)
//
//    // Color the background yellow
//    canvas?.drawColor(Color.CYAN)
//    canvas?.drawRect(0F, 0F, width.toFloat(), height.toFloat(), paint)
//  }

  /**
   * Draw Android bitmap
   */
  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)
    canvas?.drawColor(Color.WHITE)
    canvas?.drawBitmap(bitmapDroid, droidBitmapX, droidBitmapY, paint)

    if (!gameOver) {
      if (shouldDrawSpotLight) {
        canvas?.drawRect(0F, 0F, width.toFloat(), height.toFloat(), paint)
      } else {
        canvas?.drawColor(Color.BLACK)
      }
    }
  }

  override fun onSizeChanged(
    w: Int,
    h: Int,
    oldw: Int,
    oldh: Int
  ) {
    super.onSizeChanged(w, h, oldw, oldh)
    setupDroidRect()
  }

  override fun onTouchEvent(event: MotionEvent): Boolean {
    val eventX = event.x
    val eventY = event.y

    when (event.action) {
      MotionEvent.ACTION_DOWN -> {
        shouldDrawSpotLight = true
        if (gameOver) {
          gameOver = false
          setupDroidRect()
        }
      }

      MotionEvent.ACTION_UP -> {
        shouldDrawSpotLight = false
        gameOver = droidRect.contains(eventX, eventY)
      }
    }
    shaderMatrix.setTranslate(
        eventX - spotlight.width / 2.0f,
        eventY - spotlight.height / 2.0f
    )
    shader.setLocalMatrix(shaderMatrix)
    invalidate()

    return true
  }

  private fun setupDroidRect() {
    droidBitmapX = floor(Random.nextFloat() * (width - bitmapDroid.width))
    droidBitmapY = floor(Random.nextFloat() * (width - bitmapDroid.height))

    droidRect = RectF(
        droidBitmapX,
        droidBitmapY,
        droidBitmapX + bitmapDroid.width,
        droidBitmapY + bitmapDroid.height
    )
  }
}