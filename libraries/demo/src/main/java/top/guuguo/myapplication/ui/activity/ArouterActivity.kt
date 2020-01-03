package top.guuguo.myapplication.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.guuguo.android.lib.app.LBaseActivity
import com.guuguo.android.lib.app.LBaseFragment
import top.guuguo.myapplication.ThemeUtils


open class ArouterActivity : LBaseActivity() {

    companion object {
        val SIMPLE_AROUTER_ACTIVITY_INFO = "SIMPLE_AROUTER_ACTIVITY_INFO"

        fun <A : Activity> intentToArouterPath(activity: Activity, targetFragmentPath: String, targetActivity: Class<A>, map: HashMap<String, *>? = null, targetCode: Int = 0) {
            val intent = Intent(activity, targetActivity)
            intent.putExtra(SIMPLE_AROUTER_ACTIVITY_INFO, targetFragmentPath)
            val bundle = LBaseActivity.bundleData(map)
            intent.putExtras(bundle)

            if (targetCode == 0)
                activity.startActivity(intent)
            else
                activity.startActivityForResult(intent, targetCode)

        }
    }
    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        ThemeUtils.onActivityCreateSetTheme(this);
    }
    override fun getFragmentInstance(data: Intent?): androidx.fragment.app.Fragment? {
        val path = data?.getStringExtra(SIMPLE_AROUTER_ACTIVITY_INFO) ?: return super.getFragmentInstance(data)
        return ARouter.getInstance().build(path).navigation() as LBaseFragment?
    }
}
