package com.zac4j.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * Desc:
 *
 * @author: zac
 * @date: 2020/9/10
 */
class ClippedView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

  private val paint = Paint().apply {
    // Smooth out edges of what is drawn without affecting shape.
    isAntiAlias = true
    strokeWidth = resources.getDimension(R.dimen.strokeWidth)
    textSize = resources.getDimension(R.dimen.textSize)
  }

  private val path = Path()

  /**
   * The dimens for clipping rectangle.
   */
  private val clipRectRight = resources.getDimension(R.dimen.clipRectRight)
  private val clipRectBottom = resources.getDimension(R.dimen.clipRectBottom)
  private val clipRectTop = resources.getDimension(R.dimen.clipRectTop)
  private val clipRectLeft = resources.getDimension(R.dimen.clipRectLeft)

  /**
   * The inset of rectangles and the offset of small rectangle.
   */
  private val rectInset = resources.getDimension(R.dimen.rectInset)
  private val smallRectOffset = resources.getDimension(R.dimen.smallRectOffset)

  /**
   * The radius of a circle.
   */
  private val circleRadius = resources.getDimension(R.dimen.circleRadius)

  /**
   * The dimens for text inside of rectangle.
   */
  private val textOffset = resources.getDimension(R.dimen.textOffset)
  private val textSize = resources.getDimension(R.dimen.textSize)

  /**
   * Set up the coordinates for two columns.
   */
  private val columnOne = rectInset
  private val columnTwo = columnOne + rectInset + clipRectRight

  /**
   * Add the coordinates for each row, including the final row for the transformed text.
   */
  private val rowOne = rectInset
  private val rowTwo = rowOne + rectInset + clipRectBottom
  private val rowThree = rowTwo + rectInset + clipRectBottom
  private val rowFour = rowThree + rectInset + clipRectBottom
  private val textRow = rowFour + (1.5F * clipRectBottom)

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    drawBackAndUnclippedRectangle(canvas)
    drawDifferenceClippingExample(canvas)
    drawCircularClippingExample(canvas)
    drawIntersectionClippingExample(canvas)
    drawCombinedClippingExample(canvas)
    drawRoundedRectangleClippingExample(canvas)
    drawOutsideClippingExample(canvas)
    drawSkewedTextExample(canvas)
    drawTranslatedTextExample(canvas)
  }

  /**
   * Draw the clipped rectangle.
   */
  private fun drawClippedRectangle(canvas: Canvas) {
    canvas.clipRect(
        clipRectLeft, clipRectTop,
        clipRectRight, clipRectBottom
    )

    // set the background to white
    canvas.drawColor(Color.WHITE)

    // draw a diagonal
    paint.color = Color.RED
    canvas.drawLine(
        clipRectLeft, clipRectTop,
        clipRectRight, clipRectBottom, paint
    )

    // draw a circle
    paint.color = Color.GREEN
    canvas.drawCircle(
        circleRadius, clipRectBottom - circleRadius,
        circleRadius, paint
    )

    // draw text
    paint.color = Color.BLUE
    // align the right side of the text with the origin
    paint.textSize = textSize
    paint.textAlign = Paint.Align.RIGHT
    canvas.drawText(
        context.getString(R.string.clipping),
        clipRectRight, textOffset, paint
    )
  }

  private fun drawBackAndUnclippedRectangle(canvas: Canvas) {
    canvas.drawColor(Color.GRAY)
    canvas.save()
    canvas.translate(columnOne, rowOne)
    drawClippedRectangle(canvas)
    canvas.restore()
  }

  private fun drawDifferenceClippingExample(canvas: Canvas) {
  }

  private fun drawCircularClippingExample(canvas: Canvas) {
  }

  private fun drawIntersectionClippingExample(canvas: Canvas) {
  }

  private fun drawCombinedClippingExample(canvas: Canvas) {
  }

  private fun drawRoundedRectangleClippingExample(canvas: Canvas) {
  }

  private fun drawOutsideClippingExample(canvas: Canvas) {
  }

  private fun drawTranslatedTextExample(canvas: Canvas) {
  }

  private fun drawSkewedTextExample(canvas: Canvas) {
  }

  private fun drawQuickRejectExample(canvas: Canvas) {
  }

}