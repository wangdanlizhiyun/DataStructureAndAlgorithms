package com.lzy.download

/**
 * Created by 李志云 2019/1/14 11:55
 */
class ARandom {

    companion object {
        val a = 2.shl(19) + 1
        val b = 2.shl(10) + 1
        val m = 2.shl(29)
        var last = System.currentTimeMillis() % m
        fun random(max: Int): Int {
            last = (a * last + b) % m
            return (last.toFloat() / m * max).toInt()
        }
    }
}