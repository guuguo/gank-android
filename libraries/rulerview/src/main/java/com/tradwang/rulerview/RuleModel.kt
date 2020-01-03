package com.tradwang.rulerview

/**
 * 尺子的类型
 * @version 0.0.1
 * @author TradWang
 * @date 2019-4-25
 */
enum class RuleModel(val value: Int) {
    HORIZONTAL_TOP(0),
    HORIZONTAL_BOTTOM(1),
    HORIZONTAL_CIRCULAR_TOP(2),
    HORIZONTAL_CIRCULAR_BOTTOM(3),
    VERTICAL_LEFT(4),
    VERTICAL_RIGHT(5),
    VERTICAL_CIRCULAR_LEFT(6),
    VERTICAL_CIRCULAR_RIGHT(7)
}