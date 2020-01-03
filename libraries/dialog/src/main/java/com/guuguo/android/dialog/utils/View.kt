//
//@file:Suppress("NOTHING_TO_INLINE") // Aliases to other public API.
//
//package com.guuguo.android.dialog.utils
//
//import android.graphics.Bitmap
//import android.support.annotation.Px
//import android.support.annotation.RequiresApi
//import android.support.annotation.StringRes
//import android.support.v4.view.ViewCompat
//import android.view.View
//import android.view.ViewGroup
//import android.view.ViewTreeObserver
//import android.view.accessibility.AccessibilityEvent
//
///**
// * Performs the given action when this view is next laid out.
// *
// * @see doOnLayout
// */
//inline fun View.doOnNextLayout(crossinline action: (view: View) -> Unit) {
//    addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
//        override fun onLayoutChange(
//                view: View,
//                left: Int,
//                top: Int,
//                right: Int,
//                bottom: Int,
//                oldLeft: Int,
//                oldTop: Int,
//                oldRight: Int,
//                oldBottom: Int
//        ) {
//            view.removeOnLayoutChangeListener(this)
//            action(view)
//        }
//    })
//}
//
///**
// * Performs the given action when this view is laid out. If the view has been laid out and it
// * has not requested a layout, the action will be performed straight away, otherwise the
// * action will be performed after the view is next laid out.
// *
// * @see doOnNextLayout
// */
//
///**
// * Performs the given action when the view tree is about to be drawn.
// */
//inline fun View.doOnPreDraw(crossinline action: (view: View) -> Unit) {
//    val vto = viewTreeObserver
//    vto.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
//        override fun onPreDraw(): Boolean {
//            action(this@doOnPreDraw)
//            when {
//                vto.isAlive -> vto.removeOnPreDrawListener(this)
//                else -> viewTreeObserver.removeOnPreDrawListener(this)
//            }
//            return true
//        }
//    })
//}
//
///**
// * Sends [AccessibilityEvent] of type [AccessibilityEvent.TYPE_ANNOUNCEMENT].
// *
// * @see View.announceForAccessibility
// */
//@RequiresApi(16)
//inline fun View.announceForAccessibility(@StringRes resource: Int) {
//    val announcement = resources.getString(resource)
//    announceForAccessibility(announcement)
//}
//
///**
// * Updates this view's relative padding. This version of the method allows using named parameters
// * to just set one or more axes.
// *
// * @see View.setPaddingRelative
// */
//@RequiresApi(17)
//inline fun View.updatePaddingRelative(
//        @Px start: Int = paddingStart,
//        @Px top: Int = paddingTop,
//        @Px end: Int = paddingEnd,
//        @Px bottom: Int = paddingBottom
//) {
//    setPaddingRelative(start, top, end, bottom)
//}
//
///**
// * Updates this view's padding. This version of the method allows using named parameters
// * to just set one or more axes.
// *
// * @see View.setPadding
// */
//inline fun View.updatePadding(
//        @Px left: Int = paddingLeft,
//        @Px top: Int = paddingTop,
//        @Px right: Int = paddingRight,
//        @Px bottom: Int = paddingBottom
//) {
//    setPadding(left, top, right, bottom)
//}
//
///**
// * Sets the view's padding. This version of the method sets all axes to the provided size.
// *
// * @see View.setPadding
// */
//inline fun View.setPadding(@Px size: Int) {
//    setPadding(size, size, size, size)
//}
//
///**
// * Version of [View.postDelayed] which re-orders the parameters, allowing the action to be placed
// * outside of parentheses.
// *
// * ```
// * view.postDelayed(200) {
// *     doSomething()
// * }
// * ```
// *
// * @return the created Runnable
// */
//inline fun View.postDelayed(delayInMillis: Long, crossinline action: () -> Unit): Runnable {
//    val runnable = Runnable { action() }
//    postDelayed(runnable, delayInMillis)
//    return runnable
//}
//
///**
// * Version of [View.postOnAnimationDelayed] which re-orders the parameters, allowing the action
// * to be placed outside of parentheses.
// *
// * ```
// * view.postOnAnimationDelayed(16) {
// *     doSomething()
// * }
// * ```
// *
// * @return the created Runnable
// */
//@RequiresApi(16)
//inline fun View.postOnAnimationDelayed(
//        delayInMillis: Long,
//        crossinline action: () -> Unit
//): Runnable {
//    val runnable = Runnable { action() }
//    postOnAnimationDelayed(runnable, delayInMillis)
//    return runnable
//}
///**
// * Returns true when this view's visibility is [View.VISIBLE], false otherwise.
// *
// * ```
// * if (view.isVisible) {
// *     // Behavior...
// * }
// * ```
// *
// * Setting this property to true sets the visibility to [View.VISIBLE], false to [View.GONE].
// *
// * ```
// * view.isVisible = true
// * ```
// */
//inline var View.isVisible: Boolean
//    get() = visibility == View.VISIBLE
//    set(value) {
//        visibility = if (value) View.VISIBLE else View.GONE
//    }
//
///**
// * Returns true when this view's visibility is [View.INVISIBLE], false otherwise.
// *
// * ```
// * if (view.isInvisible) {
// *     // Behavior...
// * }
// * ```
// *
// * Setting this property to true sets the visibility to [View.INVISIBLE], false to [View.VISIBLE].
// *
// * ```
// * view.isInvisible = true
// * ```
// */
//inline var View.isInvisible: Boolean
//    get() = visibility == View.INVISIBLE
//    set(value) {
//        visibility = if (value) View.INVISIBLE else View.VISIBLE
//    }
//
///**
// * Returns true when this view's visibility is [View.GONE], false otherwise.
// *
// * ```
// * if (view.isGone) {
// *     // Behavior...
// * }
// * ```
// *
// * Setting this property to true sets the visibility to [View.GONE], false to [View.VISIBLE].
// *
// * ```
// * view.isGone = true
// * ```
// */
//inline var View.isGone: Boolean
//    get() = visibility == View.GONE
//    set(value) {
//        visibility = if (value) View.GONE else View.VISIBLE
//    }
//
///**
// * Executes [block] with the View's layoutParams and reassigns the layoutParams with the
// * updated version.
// *
// * @see View.getLayoutParams
// * @see View.setLayoutParams
// **/
//inline fun View.updateLayoutParams(block: ViewGroup.LayoutParams.() -> Unit) {
//    updateLayoutParams<ViewGroup.LayoutParams>(block)
//}
//
///**
// * Executes [block] with a typed version of the View's layoutParams and reassigns the
// * layoutParams with the updated version.
// *
// * @see View.getLayoutParams
// * @see View.setLayoutParams
// **/
//@JvmName("updateLayoutParamsTyped")
//inline fun <reified T : ViewGroup.LayoutParams> View.updateLayoutParams(block: T.() -> Unit) {
//    val params = layoutParams as T
//    block(params)
//    layoutParams = params
//}
