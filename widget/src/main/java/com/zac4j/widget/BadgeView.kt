package com.zac4j.widget

import android.R.attr
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.TabWidget
import androidx.appcompat.widget.AppCompatTextView

/**
 * A simple text label view that can be applied as a "badge" to any given [android.view.View].
 * This class is intended to be instantiated at runtime rather than included in XML layouts.
 *
 * @author Jeff Gilfelt
 */
class BadgeView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = attr.textViewStyle,
  target: View? = null,
  tabIndex: Int = 0
) : AppCompatTextView(
    context, attrs, defStyle
) {

  /**
   * Returns the target View this badge has been attached to.
   */
  private var target: View? = null
  /**
   * Returns the positioning of this badge.
   *
   * one of POSITION_TOP_LEFT, POSITION_TOP_RIGHT, POSITION_BOTTOM_LEFT, POSITION_BOTTOM_RIGHT,
   * POSTION_CENTER.
   */
  /**
   * Set the positioning of this badge.
   *
   * @param layoutPosition one of POSITION_TOP_LEFT, POSITION_TOP_RIGHT, POSITION_BOTTOM_LEFT,
   * POSITION_BOTTOM_RIGHT, POSTION_CENTER.
   */
  private var badgePosition = 0

  /**
   * Returns the horizontal margin from the target View that is applied to this badge.
   */
  private var horizontalBadgeMargin = 0

  /**
   * Returns the vertical margin from the target View that is applied to this badge.
   */
  private var verticalBadgeMargin = 0
  private var badgeColor = 0
  private var isShown = false
  private var badgeBg: ShapeDrawable? = null
  private var targetTabIndex = 0

  /**
   * Constructor -
   *
   * create a new BadgeView instance attached to a target [android.view.View].
   *
   * @param context context for this view.
   * @param target the View to attach the badge to.
   */
  constructor(
    context: Context,
    target: View?
  ) : this(context, null, attr.textViewStyle, target, 0) {
  }

  /**
   * Constructor -
   *
   * create a new BadgeView instance attached to a target [android.widget.TabWidget]
   * tab at a given index.
   *
   * @param context context for this view.
   * @param target the TabWidget to attach the badge to.
   * @param index the position of the tab within the target.
   */
  constructor(
    context: Context,
    target: TabWidget?,
    index: Int
  ) : this(context, null, attr.textViewStyle, target, index)

  private fun init(
    context: Context,
    target: View?,
    tabIndex: Int
  ) {
    this.target = target
    targetTabIndex = tabIndex

    // apply defaults
    badgePosition = DEFAULT_POSITION
    horizontalBadgeMargin = dipToPixels(DEFAULT_MARGIN_DIP)
    verticalBadgeMargin = horizontalBadgeMargin
    badgeColor = DEFAULT_BADGE_COLOR
    typeface = Typeface.DEFAULT_BOLD
    val paddingPixels = dipToPixels(DEFAULT_LR_PADDING_DIP)
    setPadding(paddingPixels, 0, paddingPixels, 0)
    setTextColor(DEFAULT_TEXT_COLOR)
    fadeIn = AlphaAnimation(0F, 1F)
    fadeIn?.interpolator = DecelerateInterpolator()
    fadeIn?.duration = 200
    fadeOut = AlphaAnimation(1F, 0F)
    fadeOut?.interpolator = AccelerateInterpolator()
    fadeOut?.duration = 200
    isShown = false
    if (this.target != null) {
      applyTo(this.target)
    } else {
      show()
    }
  }

  private fun applyTo(target: View?) {
    var target = target
    val lp = target!!.layoutParams
    val parent = target.parent
    val container = FrameLayout(context!!)
    if (target is TabWidget) {

      // set target to the relevant tab child container
      target = target.getChildTabViewAt(targetTabIndex)
      this.target = target
      (target as ViewGroup?)!!.addView(
          container,
          LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
      )
      this.visibility = GONE
      container.addView(this)
    } else {
      val group = parent as ViewGroup
      val index = group.indexOfChild(target)
      group.removeView(target)
      group.addView(container, index, lp)
      container.addView(target)
      this.visibility = GONE
      container.addView(this)
      group.invalidate()
    }
  }

  /**
   * Make the badge visible in the UI.
   */
  fun show() {
    show(false, null)
  }

  /**
   * Make the badge visible in the UI.
   *
   * @param animate flag to apply the default fade-in animation.
   */
  fun show(animate: Boolean) {
    show(animate, fadeIn)
  }

  /**
   * Make the badge visible in the UI.
   *
   * @param anim Animation to apply to the view when made visible.
   */
  fun show(anim: Animation?) {
    show(true, anim)
  }

  /**
   * Make the badge non-visible in the UI.
   */
  fun hide() {
    hide(false, null)
  }

  /**
   * Make the badge non-visible in the UI.
   *
   * @param animate flag to apply the default fade-out animation.
   */
  fun hide(animate: Boolean) {
    hide(animate, fadeOut)
  }

  /**
   * Make the badge non-visible in the UI.
   *
   * @param anim Animation to apply to the view when made non-visible.
   */
  fun hide(anim: Animation?) {
    hide(true, anim)
  }

  /**
   * Toggle the badge visibility in the UI.
   */
  fun toggle() {
    toggle(false, null, null)
  }

  /**
   * Toggle the badge visibility in the UI.
   *
   * @param animate flag to apply the default fade-in/out animation.
   */
  fun toggle(animate: Boolean) {
    toggle(animate, fadeIn, fadeOut)
  }

  /**
   * Toggle the badge visibility in the UI.
   *
   * @param animIn Animation to apply to the view when made visible.
   * @param animOut Animation to apply to the view when made non-visible.
   */
  fun toggle(
    animIn: Animation?,
    animOut: Animation?
  ) {
    toggle(true, animIn, animOut)
  }

  private fun show(
    animate: Boolean,
    anim: Animation?
  ) {
    if (background == null) {
      if (badgeBg == null) {
        badgeBg = defaultBackground
      }
      setBackgroundDrawable(badgeBg)
    }
    applyLayoutParams()
    if (animate) {
      startAnimation(anim)
    }
    this.visibility = VISIBLE
    isShown = true
  }

  private fun hide(
    animate: Boolean,
    anim: Animation?
  ) {
    this.visibility = GONE
    if (animate) {
      startAnimation(anim)
    }
    isShown = false
  }

  private fun toggle(
    animate: Boolean,
    animIn: Animation?,
    animOut: Animation?
  ) {
    if (isShown) {
      hide(animate && animOut != null, animOut)
    } else {
      show(animate && animIn != null, animIn)
    }
  }

  /**
   * Increment the numeric badge label. If the current badge label cannot be converted to
   * an integer value, its label will be set to "0".
   *
   * @param offset the increment offset.
   */
  fun increment(offset: Int): Int {
    val txt = text
    var i: Int
    i = if (txt != null) {
      try {
        txt.toString()
            .toInt()
      } catch (e: NumberFormatException) {
        0
      }
    } else {
      0
    }
    i = i + offset
    text = i.toString()
    return i
  }

  /**
   * Decrement the numeric badge label. If the current badge label cannot be converted to
   * an integer value, its label will be set to "0".
   *
   * @param offset the decrement offset.
   */
  fun decrement(offset: Int): Int {
    return increment(-offset)
  }

  private val defaultBackground: ShapeDrawable
    private get() {
      val r = dipToPixels(DEFAULT_CORNER_RADIUS_DIP)
      val outerR = floatArrayOf(
          r.toFloat(), r.toFloat(), r.toFloat(), r.toFloat(), r.toFloat(), r.toFloat(), r.toFloat(),
          r.toFloat()
      )
      val rr = RoundRectShape(outerR, null, null)
      val drawable = ShapeDrawable(rr)
      drawable.paint.color = badgeColor
      return drawable
    }

  private fun applyLayoutParams() {
    val lp = FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    when (badgePosition) {
      POSITION_TOP_LEFT -> {
        lp.gravity = Gravity.LEFT or Gravity.TOP
        lp.setMargins(horizontalBadgeMargin, verticalBadgeMargin, 0, 0)
      }
      POSITION_TOP_RIGHT -> {
        lp.gravity = Gravity.RIGHT or Gravity.TOP
        lp.setMargins(0, verticalBadgeMargin, horizontalBadgeMargin, 0)
      }
      POSITION_BOTTOM_LEFT -> {
        lp.gravity = Gravity.LEFT or Gravity.BOTTOM
        lp.setMargins(horizontalBadgeMargin, 0, 0, verticalBadgeMargin)
      }
      POSITION_BOTTOM_RIGHT -> {
        lp.gravity = Gravity.RIGHT or Gravity.BOTTOM
        lp.setMargins(0, 0, horizontalBadgeMargin, verticalBadgeMargin)
      }
      POSITION_CENTER -> {
        lp.gravity = Gravity.CENTER
        lp.setMargins(0, 0, 0, 0)
      }
      else -> {
      }
    }
    layoutParams = lp
  }

  /**
   * Is this badge currently visible in the UI?
   */
  override fun isShown(): Boolean {
    return isShown
  }

  /**
   * Set the horizontal/vertical margin from the target View that is applied to this badge.
   *
   * @param badgeMargin the margin in pixels.
   */
  fun setBadgeMargin(badgeMargin: Int) {
    horizontalBadgeMargin = badgeMargin
    verticalBadgeMargin = badgeMargin
  }

  /**
   * Set the horizontal/vertical margin from the target View that is applied to this badge.
   *
   * @param horizontal margin in pixels.
   * @param vertical margin in pixels.
   */
  fun setBadgeMargin(
    horizontal: Int,
    vertical: Int
  ) {
    horizontalBadgeMargin = horizontal
    verticalBadgeMargin = vertical
  }
  /**
   * Returns the color value of the badge background.
   */
  /**
   * Set the color value of the badge background.
   *
   * @param badgeColor the badge background color.
   */
  var badgeBackgroundColor: Int
    get() = badgeColor
    set(badgeColor) {
      this.badgeColor = badgeColor
      badgeBg = defaultBackground
    }

  private fun dipToPixels(dip: Int): Int {
    val r = resources
    val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip.toFloat(), r.displayMetrics)
    return px.toInt()
  }

  companion object {
    const val POSITION_TOP_LEFT = 1
    const val POSITION_TOP_RIGHT = 2
    const val POSITION_BOTTOM_LEFT = 3
    const val POSITION_BOTTOM_RIGHT = 4
    const val POSITION_CENTER = 5
    private const val DEFAULT_MARGIN_DIP = 5
    private const val DEFAULT_LR_PADDING_DIP = 5
    private const val DEFAULT_CORNER_RADIUS_DIP = 8
    private const val DEFAULT_POSITION = POSITION_TOP_RIGHT
    private val DEFAULT_BADGE_COLOR = Color.parseColor("#CCFF0000") //Color.RED;
    private const val DEFAULT_TEXT_COLOR = Color.WHITE
    private var fadeIn: Animation? = null
    private var fadeOut: Animation? = null
  }

  init {
    init(context, target, tabIndex)
  }
}