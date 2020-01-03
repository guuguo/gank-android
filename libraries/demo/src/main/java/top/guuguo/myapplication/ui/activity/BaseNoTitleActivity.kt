package top.guuguo.myapplication.ui.activity

import androidx.appcompat.widget.Toolbar
import com.guuguo.android.lib.app.LBaseActivity
import top.guuguo.myapplication.R


class BaseNoTitleActivity : LBaseActivity() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_base_no_title
    }

}
