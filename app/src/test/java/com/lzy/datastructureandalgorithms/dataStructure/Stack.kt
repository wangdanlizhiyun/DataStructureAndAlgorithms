package com.lzy.datastructureandalgorithms.dataStructure

import java.util.*

/**
 * Created by 李志云 2019/1/12 18:06
 */
class Stack<T> {
    class Node<T>(var item: T, var next: Node<T>?)
    var top:Node<T>? = null
    var size = 0

    fun isNotEmpty():Boolean{
        return size > 0
    }
    fun push(t:T):T{
        val p = top
        val newNode = Node(t,p)
        top = newNode
        size++
        return t
    }

    fun pop():T?{
        val p = top
        if (p == null){
            return  null
        }else{
            top = p.next
            size--
            return p.item
        }
    }



}