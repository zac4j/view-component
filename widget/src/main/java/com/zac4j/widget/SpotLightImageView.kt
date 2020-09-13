package com.zac4j.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * Desc:
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

  private lateinit var winnerRect: RectF
  private var droidBitmapX = 0F
  private var droidBitmapY = 0F

  private val bitmapDroid = BitmapFactory.decodeResource(
      resources,
      R.drawable.android
  )
  private val spotlight = BitmapFactory.decodeResource(resources, R.drawable.mask)

  private lateinit var shader: Shader

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

}