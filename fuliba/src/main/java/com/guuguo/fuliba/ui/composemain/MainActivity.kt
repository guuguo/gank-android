package com.guuguo.fuliba.ui.composemain

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.tooling.preview.Preview
import com.guuguo.fuliba.data.bean.FulibaItemBean
import com.guuguo.fuliba.data.source.FulibaRepository
import com.guuguo.fuliba.ui.composemain.ui.ComposeTheme
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
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
fun Card(text: String) {
    Row {
        Text(text = "Hello $text!")
    }
}

@Composable
fun CardList(list: MutableList<FulibaItemBean>) {
    Column {
        list.forEach {
            Card(text = it.title)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTheme(true) {
        CardList(
            mutableListOf<FulibaItemBean>().apply {
                repeat(5) {
                    add(FulibaItemBean().apply {
                        title = "你好a"
                    })
                }
            }
        )
    }
}