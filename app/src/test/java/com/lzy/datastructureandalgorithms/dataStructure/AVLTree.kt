package com.lzy.datastructureandalgorithms.dataStructure

import android.view.animation.RotateAnimation
import java.util.*

/**
 * Created by 李志云 2019/1/26 16:36
 */
const val LH = 1
const val RH = -1
const val EH = 0

class AVLTree<T : Comparable<T>> {
    var root: Node<T>? = null
    var size = 0

    class Node<T : Comparable<T>>(var element: T, var parent: Node<T>?) {
        var balance = 0
        var left: Node<T>? = null
        var right: Node<T>? = null
    }

    fun insertElement(element: T): Boolean {
        var t = root
        if (t == null) {
            root = Node<T>(element, null)
            size = 1
            root?.balance = 0
            return true
        } else {
            var parent: Node<T>? = null
            val e: Comparable<in T> = element
            var cmp = 0
            do {
                parent = t
                t?.let {
                    cmp = e.compareTo(it.element)
                    if (cmp < 0) {
                        t = it.left
                    } else if (cmp > 0) {
                        t = it.right
                    } else {
                        return false
                    }
                }
            } while (t != null)
            //开始插入数据
            parent?.let {
                val child = Node(element,parent)
                if (cmp < 0){
                    parent?.left = child
                }else{
                    parent?.right = child
                }
            }
            //检查平衡
            while (parent != null){
                cmp = e.compareTo(parent.element)
                if(cmp < 0){
                    parent.balance++
                }else{
                    parent.balance--
                }
                if (parent.balance == 0){
                    break
                }
                if (Math.abs(parent.balance) == 2){
                    fixAfterInsertion(parent)
                }else{
                    parent = parent.parent
                }
            }
        }
        size++
        return true
    }

    fun fixAfterInsertion(parent:Node<T>){
        if (parent.balance == 2){
            leftBalance(parent)
        }else if (parent.balance == -2){
            rightBalance(parent)
        }
    }

    fun rightBalance(t: Node<T>) {
        val tr = t.right
        tr?.let {
            when (tr.balance) {
                LH -> {
                    val trl = tr.left
                    trl?.let {
                        rightRotate(tr)
                        rightRotate(t)
                        when (trl.balance) {
                            RH -> {
                                t.balance = LH
                                tr.balance = EH
                                trl.balance = EH
                            }
                            LH -> {
                                t.balance = EH
                                tr.balance = RH
                                trl.balance = EH
                            }
                            EH -> {
                                t.balance = EH
                                tr.balance = EH
                                trl.balance = EH
                            }
                        }
                    }
                }
                RH -> {
                    leftRotate(t)
                    t.balance = EH
                    tr.balance = EH
                }
                else -> {

                }
            }
        }
    }

    fun leftBalance(t: Node<T>) {

    }

    fun rightRotate(x: Node<T>) {

    }

    fun leftRotate(x: Node<T>) {
        x?.let {
            val y = x.right
            y?.let {
                x.right = y.left
                y.left?.let {
                    it.parent = x
                }
                if (x.parent == null) {
                    root = y
                } else {
                    x.parent?.let { parent ->
                        if (parent.right == x) {
                            parent.right = y
                        } else if (parent.left == x) {
                            parent.left = y
                        }
                    }
                }
                y.left = x
                x.parent = y
            }

        }
    }
}