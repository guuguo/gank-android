package com.guuguo.fuliba.ui.composemain

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.Modifier
import androidx.ui.core.drawShadow
import androidx.ui.core.setContent
import androidx.ui.core.tag
import androidx.ui.foundation.Text
import androidx.ui.foundation.drawBackground
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.guuguo.fuliba.data.bean.FulibaItemBean
import com.guuguo.fuliba.data.source.FulibaRepository
import com.guuguo.fuliba.ui.composemain.ui.ComposeTheme
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            setContent {
                ComposeTheme(true) {
                    Loading("加载中")
                }
            }
            val list = FulibaRepository.getFuliItemList(0)
            list?.let {
                setContent {
                    ComposeTheme(true) {
                        CardList(list)
                    }
                }
            }
        }
    }
}

@Composable
fun FulibaItem(text: String) {
    Stack(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp).fillMaxWidth()
            .height(110.dp)
    ) {
        Stack(
            modifier = Modifier.padding(top=30.dp,start = 10.dp).fillMaxSize()
                .drawShadow(elevation = 6.dp, shape = RoundedCornerShape(3.dp))
                .drawBackground(Color.White, RoundedCornerShape(3.dp)).padding(10.dp)
        ) { }
        Row {
            Text(text = "$text")
        }

    }
}

@Composable
fun Loading(text: String) {
    ConstraintLayout(modifier = Modifier.fillMaxSize(), constraintSet = ConstraintSet {
        val msg = tag("msg")
        val progress = tag("progress")
        progress.apply {
            left constrainTo parent.left
            right constrainTo parent.right
            top constrainTo parent.top
            createVerticalChain(
                progress,
                msg,
                chainStyle = ConstraintSetBuilderScope.ChainStyle.Packed
            )
        }
        msg.apply {
            left constrainTo parent.left
            right constrainTo parent.right
            bottom constrainTo parent.bottom
        }
    }) {
        CircularProgressIndicator(modifier = Modifier.tag("progress"))
        Text(text = text, modifier = Modifier.tag("msg"))
    }
}

@Composable
fun CardList(list: MutableList<FulibaItemBean>) {
    Column {
        list.forEach {
            FulibaItem(text = it.title)
        }
    }
}

@Composable
fun LoadingPreview() {
    ComposeTheme(true) {
        Loading("加载中")
    }
}

@Composable
@Preview(showBackground = true)
fun DefaultPreview() {
    ComposeTheme(true) {
        CardList(
            mutableListOf<FulibaItemBean>().apply {
                repeat(2) {
                    add(FulibaItemBean().apply {
                        title = "你好a"
                    })
                }
            }
        )
    }
}