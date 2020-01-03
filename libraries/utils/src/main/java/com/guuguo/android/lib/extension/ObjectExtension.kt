package com.guuguo.android.lib.extension

fun Any.getNameAndHashCode(): String {
    return javaClass.simpleName + "@" + Integer.toHexString(System.identityHashCode(this))
}