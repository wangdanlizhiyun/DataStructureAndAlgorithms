package com.lzy.datastructureandalgorithms.dataStructure

/**
 * Created by 李志云 2019/1/12 03:29
 */
class LinkedList<T> {
    var first: Node<T>? = null
    var last: Node<T>? = null
    var size: Int = 0

    class Node<T>(var pre: Node<T>?, var item: T, var next: Node<T>?)

    fun add(t: T) {
        linkLast(t)
    }
    fun add(index: Int,item:T){
        if (index < 0 || index > size)return
        if (index == size){
            linkLast(item)
        }else{
            val target = node(index)
            target?.let {
                val pre = target.pre
                val newNode = Node<T>(pre,item,target)
                if (pre == null){
                    first = newNode
                }else{
                    pre.next = newNode
                }
                target.pre = newNode
                size++
            }
        }
    }
    fun addAll(linkedList: LinkedList<T>){
        val l = last
        if (l == null){
            first = linkedList.first
        }else{
            l.next = linkedList.first
        }
        last = linkedList.last
        size += linkedList.size
    }

    private fun linkLast(t: T) {
        val newNode = Node<T>(last, t, null)
        val l = last
        last = newNode
        if (l == null) {
            first = newNode
        } else {
            l.next = newNode
        }
        size++
    }

    fun get(index: Int): T? {
        if (index < 0 || index >= size){
            return null
        }else{
           return node(index)?.item
        }
    }

    private fun node(index: Int): Node<T>? {
        var node:Node<T>?
        if (index < size.shr(1)){
            node = first
            for (i in 0 until index){
                node = node?.next
            }

        }else{
            node = last
            for (i in size-1 downTo index){
                node = node?.pre
            }
        }
        return node
    }

    fun remove(index: Int){
        node(index)?.let { unlinkNode(it) }
    }
    fun unlinkNode(node:Node<T>){
        val pre = node.pre
        val next = node.next
        if (pre == null){
            first = node.next
        }else{
            pre.next = next
        }
        if (next == null){
            last = pre
        }else{
            next.pre = pre
        }
    }

}