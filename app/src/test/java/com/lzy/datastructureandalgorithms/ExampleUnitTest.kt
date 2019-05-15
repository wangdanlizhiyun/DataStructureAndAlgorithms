package com.lzy.datastructureandalgorithms

import android.util.Log
import com.lzy.datastructureandalgorithms.dataStructure.Random
import com.lzy.datastructureandalgorithms.dataStructure.SearchBinaryTree
import com.lzy.datastructureandalgorithms.dataStructure.SortUtil
import com.lzy.download.ALock
import org.junit.Test

import org.junit.Assert.*
import java.util.*
import kotlin.collections.HashMap

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val array = intArrayOf(1,2,55,66,75,6,84,85,55,6,8,3,5,77,6,78,79,80,81,83,44,22,33)


        val num = 3;
        when(num){
            2->{}
            3,4->{}
            5,6->{}
            7,8,9->{}
            else->{

            }

        }
//        SortUtil.mergeSort(array,0,array.size - 1)
        System.out.print("原始数据")
        array.forEach {
            System.out.print("$it ")
        }
        System.out.print("\n")

        val searchBinaryTree = SearchBinaryTree<Int>()
        array.forEach {
            searchBinaryTree.put(it)
        }


        System.out.print("中序遍历树\n")
        searchBinaryTree.midOrderTraverse(searchBinaryTree.root)

        searchBinaryTree.delete(80)

        System.out.print("\n删除80后中序遍历树\n")
        searchBinaryTree.midOrderTraverse(searchBinaryTree.root)


        var i = 0
        var j = 0
        val lock = ALock()
        fun inc() {
            try {
                lock.lock()
                i++
                j += 2
            } finally {
                lock.unlock()
            }
        }
        for (i in 0 until 2) {
            Thread() {
                kotlin.run {
                    for (j in 0 until 1000) {
                        inc()
                    }
                }
            }.start()
        }

        Thread.sleep(1000)
        Log.e("test", "$i $j")
    }


}
