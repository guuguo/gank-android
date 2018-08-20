package com.guuguo.android.lib.widget

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import com.guuguo.android.R
import com.guuguo.android.lib.extension.safe

/**
 * mimi 创造于 2017-07-06.
 * 项目 order
 */

class MySearchView : FrameLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.MySearchView, defStyleAttr, 0)
        initAttr(context, attributes)
        attributes.recycle()
        initView()
    }
    private fun initAttr(context: Context, attributes: TypedArray) {
        hint = attributes.getString(R.styleable.MySearchView_msv_hint).safe("搜索")
    }

    var onBackClick: (v: View) -> Unit = {}
    var onTextChange: (str: String) -> Unit = {}
    var searchClick: (str: String) -> Unit = {}

    lateinit var holder: ViewHolder
    var hint: String = "搜索"

    fun searchText(): String = holder.edtSearch?.text?.toString().safe()
    fun addSearchText(str: String, selected: Boolean = false) {
        holder.edtSearch?.let {
            it.text.append(str)
            if (selected)
                it.setSelection(it.text.length - str.length, it.text.length)
        }
    }

    fun clearSearchText() {
        holder.edtSearch?.setText("")
    }

    fun focus() {
        holder.edtSearch?.requestFocus()
    }

    fun replaceSelectionSearchText(str: String) {
        holder.edtSearch?.let {
            it.text.replace(it.selectionStart, it.selectionEnd, str)
        }
    }

    fun delete() {
        holder.edtSearch?.let {
            if (it.selectionStart == it.selectionEnd && it.selectionStart != 0)
                it.text.delete(it.selectionStart - 1, it.selectionStart)
            else
                it.text.delete(it.selectionStart, it.selectionEnd)
        }
    }

    fun searchText(str: String, selected: Boolean = false) {
        holder.edtSearch?.let {
            it.setText(str)
            if (selected)
                it.selectAll()
        }
    }


    private fun obtainAttributes(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.MySearchView)
        ta.recycle()
    }

    private fun initView() {
        val layout = LayoutInflater.from(context).inflate(R.layout.base_include_search, this, false)
        holder = ViewHolder(layout)
        this.addView(layout)

        holder.edtSearch.hint = hint

        holder.btnBack.setOnClickListener { v -> onBackClick(v) }
        holder.btnClear.setOnClickListener {
            holder.edtSearch.setText("")
        }
        holder.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0.isNullOrEmpty())
                    holder.btnClear.visibility = View.GONE
                else
                    holder.btnClear.visibility = View.VISIBLE
                onTextChange(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        holder.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchClick(holder.edtSearch.text.toString())
                true
            } else false
        }
    }

    class ViewHolder(var view: View) {
        val btnBack = view.findViewById<View>(R.id.iv_back)
        val btnClear = view.findViewById<View>(R.id.iv_close)
        val edtSearch = view.findViewById<EditText>(R.id.edt_search)
    }

}
