//package com.guuguo.learnsave.app.fragment
//
//import android.animation.Animator
//import android.content.Context
//import android.os.Bundle
//import android.os.Handler
//import android.support.v4.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.view.ViewTreeObserver
//import android.view.animation.AccelerateDecelerateInterpolator
//import android.view.inputmethod.InputMethodManager
//import android.widget.EditText
//import android.widget.ImageView
//import android.widget.LinearLayout
//
//import com.guuguo.learnsave.R
//import com.guuguo.learnsave.app.base.BaseFragment
//import com.guuguo.learnsave.extension.squareRoot
//import com.guuguo.learnsave.presenter.SearchRevealPresenter
//import com.guuguo.learnsave.view.IBaseView
//
//import io.codetail.animation.ViewAnimationUtils
//
//import kotterknife.bindView
//
//public class SearchRevealFragment : BaseFragment(), IBaseView {
//
//    private var centerX: Int = 0
//    private var centerY: Int = 0
//
//    var presenter: SearchRevealPresenter? = null
//
//    val content by bindView<View>(R.id.content)
//    val edit_search by bindView<EditText>(R.id.edit_search)
//    val edit_lay by bindView<LinearLayout>(R.id.edit_lay)
//    val llItems by bindView<LinearLayout>(R.id.items)
//    val IVsearch by bindView<ImageView>(R.id.img_search)
//
//    override fun getLayoutResId(): Int {
//        return R.layout.fragment_searcch
//    }
//
//    fun onBackPressed(): Boolean {
//        var mRevealAnimator = ViewAnimationUtils.createCircularReveal(content, centerX, centerY, 20f, squareRoot(content.width, content.height))
//        mRevealAnimator!!.resume()
//        mRevealAnimator.addListener(object : Animator.AnimatorListener {
//            override fun onAnimationStart(p0: Animator?) {
//                content.visibility = View.VISIBLE
//            }
//
//            override fun onAnimationEnd(p0: Animator?) {
//                content.visibility = View.INVISIBLE
//                activity!!.supportFragmentManager.popBackStack()
//            }
//
//            override fun onAnimationCancel(p0: Animator?) {
//            }
//
//            override fun onAnimationRepeat(p0: Animator?) {
//            }
//        })
//        mRevealAnimator.setDuration(200)
//        mRevealAnimator.setStartDelay(100)
//        mRevealAnimator.setInterpolator(AccelerateDecelerateInterpolator())
//        mRevealAnimator.start()
//        return true
//    }
//
//    fun onClick(view: View) {
//        when (view.id) {
//            R.id.root -> onBackPressed()
//        }
//    }
//
//    override fun initPresenter() {
//        presenter = SearchRevealPresenter(activity!!, this)
//        presenter?.init()
//    }
//
//    override fun initIView() {
//        edit_lay.getViewTreeObserver().addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
//            override fun onPreDraw(): Boolean {
//                edit_lay.getViewTreeObserver().removeOnPreDrawListener(this)
//                llItems.setVisibility(View.INVISIBLE)
//                llItems.setOnClickListener(View.OnClickListener { view ->
//                    when (view.getId()) {
//                        R.id.root -> onBackPressed()
//                    }
//                })
//
//                edit_lay.setVisibility(View.INVISIBLE)
//
//                centerX = IVsearch.left + IVsearch.width / 2
//                centerY = IVsearch.top + IVsearch.height / 2
//
////                val mRevealAnimator = ViewAnimationUtils.createCircularReveal(edit_lay, centerX, centerY, 20f, squareRoot(edit_lay.getWidth(), edit_lay.getHeight()))
////                mRevealAnimator.addListener(object : Animator.AnimatorListener {
////                    override fun onAnimationRepeat(p0: Animator?) {
////                    }
////
////                    override fun onAnimationCancel(p0: Animator?) {
////                    }
////
////                    override fun onAnimationStart(p0: Animator?) {
////                    }
////
////                    override fun onAnimationEnd(p0: Animator?) {
////                        Handler().postDelayed({
////                            llItems.setVisibility(View.VISIBLE)
////                            edit_search.requestFocus()
////                            if (activity != null) {
////                                val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
////                                imm.showSoftInput(edit_search, InputMethodManager.SHOW_IMPLICIT)
////                            }
////                        }, 100)
////                    }
////                })
////                mRevealAnimator.duration = 200
////                mRevealAnimator.startDelay = 100
////                mRevealAnimator.interpolator = AccelerateDecelerateInterpolator()
////                mRevealAnimator.start()
//                return true
//            }
//        })
//    }
//
//    override fun showProgress() {
//    }
//
//    override fun hideProgress() {
//    }
//
//    override fun showErrorView(e: Throwable) {
//    }
//
//    override fun showTip(msg: String) {
//    }
//
//
//}
