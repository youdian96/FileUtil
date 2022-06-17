package com.xlfd.commonutilsx.exts

import android.graphics.Rect
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup

/**
 * 防抖点击事件
 */
fun View.singleClick(defaultClickMills: Long = 500, block: View.OnClickListener) {
    setOnClickListener(object : View.OnClickListener {
        private var preClickTime = SystemClock.uptimeMillis()
        override fun onClick(v: View?) {
            var nowClick = SystemClock.uptimeMillis()
            if (nowClick - preClickTime < defaultClickMills) return@onClick
            preClickTime = nowClick
            block.onClick(v)
        }

    })
}

/**
 * 判断当前View是否被覆盖
 */
fun View.isViewCovered(): Boolean {
    var view = this
    var currentView = this
    val currentViewRect = Rect()
    val partVisible = currentView.getGlobalVisibleRect(currentViewRect)
    val totalHeightVisible: Boolean =
        currentViewRect.bottom - currentViewRect.top >= view.measuredHeight
    val totalWidthVisible: Boolean =
        currentViewRect.right - currentViewRect.left >= view.measuredWidth
    val totalViewVisible = partVisible && totalHeightVisible && totalWidthVisible
    if (!totalViewVisible) //if any part of the view is clipped by any of its parents,return true
        return true
    while (currentView.parent is ViewGroup) {
        val currentParent: ViewGroup = currentView.parent as ViewGroup
        if (currentParent.getVisibility() !== View.VISIBLE) //if the parent of view is not visible,return true
            return true
        val start: Int = indexOfViewInParent(currentView, currentParent)
        for (i in start + 1 until currentParent.getChildCount()) {
            val viewRect = Rect()
            view.getGlobalVisibleRect(viewRect)
            val otherView: View = currentParent.getChildAt(i)
            val otherViewRect = Rect()
            otherView.getGlobalVisibleRect(otherViewRect)
            if (Rect.intersects(
                    viewRect,
                    otherViewRect
                )
            ) //if view intersects its older brother(covered),return true
                return true
        }
        currentView = currentParent
    }
    return false
}

private fun indexOfViewInParent(view: View, parent: ViewGroup): Int {
    var index = 0
    while (index < parent.childCount) {
        if (parent.getChildAt(index) === view) break
        index++
    }
    return index
}