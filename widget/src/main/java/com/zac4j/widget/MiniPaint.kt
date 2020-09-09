package com.zac4j.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import kotlin.math.abs

/**
 * MiniPaint is a simple line drawing app. Drag your fingers to draw.
 *
 * @author: zac
 * @date: 2020/9/8
 */

private const val STROKE_WIDTH = 12F

class MiniPaint(context: Context) : View(context) {

  private lateinit var extraCanvas: Canvas
  private lateinit var extraBitmap: Bitmap

  private val backgroundColor = ResourcesCompat.getColor(resources, R.color.colorBackground, null)

  private val drawColor = ResourcesCompat.getColor(resources, R.color.colorPaint, null)

  private val paint = Paint().apply {
    color = drawColor
    // Smooths out edges of what is drawn without affecting shape
    isAntiAlias = true
    // Dithering affects how colors with higher-precision than the device are down-sampled
    isDither = true
    style = Paint.Style.STROKE
    strokeJoin = Paint.Join.ROUND
    strokeCap = Paint.Cap.ROUND
    strokeWidth = STROKE_WIDTH
  }

  private var path = Path()

  /**
   * Caching the x and y coordinates of the current touch event.
   */
  private var currentX = 0F
  private var currentY = 0F

  /**
   * Caching the latest x and y coordinates of a touch, and these values should be the starting
   * point for the next path.
   */
  private var lastX = 0F
  private var lastY = 0F

  /**
   * Distance in pixels a touch can wander before we think the user is scrolling.
   */
  private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

  override fun onTouchEvent(event: MotionEvent): Boolean {
    currentX = event.x
    currentY = event.y

    when (event.action) {
      MotionEvent.ACTION_DOWN -> touchStart()
      MotionEvent.ACTION_MOVE -> touchMove()
      MotionEvent.ACTION_UP -> touchUp()
    }

    return true
  }

  private fun touchStart() {
    path.reset()
    path.moveTo(currentX, currentY)
    lastX = currentX
    lastY = currentY
  }

  private fun touchMove() {
    // Calculate the distance that has moved (dx, dy)
    val dx = abs(currentX - lastX)
    val dy = abs(currentY - lastY)
    if (dx >= touchTolerance || dy >= touchTolerance) {
      // Add a quadratic bezier from the last point, approaching control point
      // (x1,y1), and ending at (x2,y2).
      path.quadTo(lastX, lastY, (currentX + lastX) / 2, (currentY + lastY) / 2)

      // Save the current point as next segment starting point
      lastX = currentX
      lastY = currentY

      // Draw the path in the extra bitmap to cache it
      extraCanvas.drawPath(path, paint)
    }
    invalidate()
  }

  private fun touchUp() {
    // Reset the path so it doesn't get drawn again
    path.reset()
  }

  override fun onSizeChanged(
    width: Int,
    height: Int,
    oldWidth: Int,
    oldHeight: Int
  ) {
    super.onSizeChanged(width, height, oldWidth, oldHeight)
    // avoid memory leak by recycle old bitmap before creating the new one.
    if (::extraBitmap.isInitialized) extraBitmap.recycle()
    // create a screen size bitmap
    extraBitmap = Bitmap.createBitmap(width, height, ARGB_8888)
    // create a canvas from bitmap
    extraCanvas = Canvas(extraBitmap)
    // fill canvas with background color
    extraCanvas.drawColor(backgroundColor)
  }

  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)
    canvas?.drawBitmap(extraBitmap, 0F, 0F, null)
  }
}