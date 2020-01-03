package top.guuguo.myapplication.ui.activity

import androidx.appcompat.widget.Toolbar
import top.guuguo.myapplication.R


class BaseTitleActivity : ArouterActivity() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_base_title
    }


    override fun getToolBar(): Toolbar = findViewById(R.id.id_tool_bar)

    override fun getHeaderTitle(): String {
        return "simple view"
    }
}
