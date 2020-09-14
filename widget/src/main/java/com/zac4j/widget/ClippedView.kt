package com.zac4j.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Region
import android.os.Build
import android.util.AttributeSet
import android.view.View

/**
 * Clipping canvas objects
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

  private var rectF = RectF(
      rectInset,
      rectInset,
      clipRectRight - rectInset,
      clipRectBottom - rectInset
  )

  private val rejectRow = rowFour + rectInset + 2 * clipRectBottom

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
    drawQuickRejectExample(canvas)
  }

  /**
   * Draw the clipped rectangle.
   */
  private fun drawClippedRectangle(canvas: Canvas) {
    // define a clipping rectangle region
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
    canvas.save()
    // move the origin to the right for the next rectangle
    canvas.translate(columnTwo, rowOne)
    // use the subtraction of two clipping rectangles to create a frame
    canvas.clipRect(
        2 * rectInset, 2 * rectInset,
        clipRectRight - 2 * rectInset,
        clipRectBottom - 2 * rectInset
    )

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
      canvas.clipRect(
          4 * rectInset, 4 * rectInset,
          clipRectRight - 4 * rectInset,
          clipRectBottom - 4 * rectInset,
          Region.Op.DIFFERENCE
      )
    } else {
      canvas.clipOutRect(
          4 * rectInset, 4 * rectInset,
          clipRectRight - 4 * rectInset,
          clipRectBottom - 4 * rectInset
      )
    }
    drawClippedRectangle(canvas)
    canvas.restore()
  }

  private fun drawCircularClippingExample(canvas: Canvas) {
    canvas.save()
    canvas.translate(columnOne, rowTwo)
    // clear any lines and curves from the path but unlike reset(),
    // keeps the internal data structure for faster reuse.
    path.rewind()
    path.addCircle(
        circleRadius, clipRectBottom - circleRadius,
        circleRadius, Path.Direction.CCW
    )
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
      canvas.clipPath(path, Region.Op.DIFFERENCE)
    } else {
      canvas.clipOutPath(path)
    }
    drawClippedRectangle(canvas)
    canvas.restore()
  }

  private fun drawIntersectionClippingExample(canvas: Canvas) {
    canvas.save()
    canvas.translate(columnTwo, rowTwo)
    canvas.clipRect(
        clipRectLeft, clipRectTop,
        clipRectRight - smallRectOffset,
        clipRectBottom - smallRectOffset
    )
    //
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
      canvas.clipRect(
          clipRectLeft + smallRectOffset,
          clipRectTop + smallRectOffset,
          clipRectRight, clipRectBottom,
          Region.Op.INTERSECT
      )
    } else {
      canvas.clipRect(
          clipRectLeft + smallRectOffset,
          clipRectTop + smallRectOffset,
          clipRectRight, clipRectBottom
      )
    }

    drawClippedRectangle(canvas)
    canvas.restore()
  }

  private fun drawCombinedClippingExample(canvas: Canvas) {
    canvas.save()
    canvas.translate(columnOne, rowThree)
    path.rewind()
    path.addCircle(
        clipRectLeft + rectInset + circleRadius,
        clipRectTop + circleRadius + rectInset,
        circleRadius, Path.Direction.CCW
    )
    path.addRect(
        clipRectRight / 2 - circleRadius,
        clipRectTop + circleRadius + rectInset,
        clipRectRight / 2 + circleRadius,
        clipRectBottom - rectInset, Path.Direction.CCW
    )
    canvas.clipPath(path)
    drawClippedRectangle(canvas)
    canvas.restore()
  }

  private fun drawRoundedRectangleClippingExample(canvas: Canvas) {
    canvas.save()
    canvas.translate(columnTwo, rowThree)
    path.rewind()
    path.addRoundRect(
        rectF,
        clipRectRight / 4,
        clipRectRight / 4,
        Path.Direction.CCW
    )
    canvas.clipPath(path)
    drawClippedRectangle(canvas)
    canvas.restore()
  }

  private fun drawOutsideClippingExample(canvas: Canvas) {
    canvas.save()
    canvas.translate(columnOne, rowFour)
    canvas.clipRect(
        2 * rectInset, 2 * rectInset,
        clipRectRight - 2 * rectInset,
        clipRectBottom - 2 * rectInset
    )
    drawClippedRectangle(canvas)
    canvas.restore()
  }

  private fun drawTranslatedTextExample(canvas: Canvas) {
    canvas.save()
    paint.color = Color.GREEN
    // Align the RIGHT side of the text with the origin
    paint.textAlign = Paint.Align.LEFT
    // Apply transformation to canvas.
    canvas.translate(columnTwo, textRow)
    // Draw text
    canvas.drawText(
        context.getString(R.string.translated),
        clipRectLeft, clipRectTop, paint
    )
    canvas.restore()
  }

  private fun drawSkewedTextExample(canvas: Canvas) {
    canvas.save()
    paint.color = Color.YELLOW
    paint.textAlign = Paint.Align.RIGHT
    // Position text
    canvas.translate(columnTwo, textRow)
    // Apply skew transformation
    canvas.skew(0.2f, 0.3f)
    canvas.drawText(
        context.getString(R.string.skewed),
        clipRectLeft, clipRectTop, paint
    )
    canvas.restore()
  }

  private fun drawQuickRejectExample(canvas: Canvas) {
    val inClipRectangle = RectF(
        clipRectRight / 2,
        clipRectBottom / 2,
        clipRectRight * 2,
        clipRectBottom * 2
    )

    val notInClipRectangle =
      RectF(
          RectF(
              clipRectRight + 1,
              clipRectBottom + 1,
              clipRectRight * 2,
              clipRectBottom * 2
          )
      )

    canvas.save()
    canvas.translate(columnOne, rejectRow)
    canvas.clipRect(
        clipRectLeft, clipRectTop,
        clipRectRight, clipRectBottom
    )
    if (canvas.quickReject(inClipRectangle, Canvas.EdgeType.AA)) {
      canvas.drawColor(Color.WHITE)
    } else {
      canvas.drawColor(Color.WHITE)
      canvas.drawRect(inClipRectangle, paint)
    }

    canvas.restore()
  }

}