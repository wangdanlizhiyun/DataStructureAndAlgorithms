package com.lzy.datastructureandalgorithms.dataStructure

import android.provider.ContactsContract
import java.lang.Exception

/**
 * Created by 李志云 2019/1/16 21:45
 */
class SearchBinaryTree<T:Comparable<T>> {

    var root: Node<T>? = null
    fun delete(data:T){
        searchNode(data)?.let { deleteNode(it) }
    }

    fun deleteNode(node:Node<T>){
        val parent = node.parent
        //削掉太子的势力
        node.parent = null
        node.left?.parent = null
        node.right?.parent = null
        //当太子被废后找一个新太子统一管理儿孙们
        var newNode:Node<T>? = null
        node.right?.let {
            newNode = getMinLeftTreeNode(it)
            //嫁接小儿子
            newNode?.left = node.left
            //新儿子头头如果还有野父亲就父子反目
            newNode?.parent?.let {parent->
                parent.parent = newNode
                newNode?.right = parent
            }
        }
        //没有长子一脉时只能传位小儿子一系了
        if (newNode == null){
            newNode = node.left
        }
        newNode?.let {
            //新太子上位
            parent?.let {
                if (parent.left == node){
                    parent.left = newNode
                }else{
                    parent.right = newNode
                }
            }
            it.parent = parent
            //直接登基
            if (parent == null){
                root = it
            }
        }
        //断后了，改朝换代吧
        if (newNode == null) {
            root = null
        }
    }
    private fun getMinLeftTreeNode(node:Node<T>):Node<T>{
        var n = node
        while (n.left != null){
            n.left?.apply { n = this }
        }
        return n
    }

    fun put(data: T): Node<T> {
        if (data == null) throw Exception("data cannot be null")
        val newNode = Node(data)
        if (root == null) {
            root = newNode
            return newNode
        }

        var parent: Node<T>? = null
        var node = root
        while (node != null) {
            parent = node
            if (data > node.data) {
                node = node.right
            } else if (data < node.data) {
                node = node.left
            } else {
                return node
            }
        }
        parent?.let {
            if (data > it.data) {
                it.right = newNode
            } else {
                it.left = newNode
            }
            newNode.parent = parent
        }
        return newNode
    }

    fun searchNode(data: T):Node<T>?{
        root?.apply {
            var node:Node<T>? = this
            while (node != null){
                if (data > node.data){
                    node = node.right
                }else if (data < node.data){
                    node = node.left
                }else {
                    return node
                }
            }
        }
        return null
    }

    fun midOrderTraverse(root:Node<T>?){
        root?.apply {
            midOrderTraverse(root.left)
            System.out.print("${root.data} ")
            midOrderTraverse(root.right)
        }
    }

    class Node<T>(var data: T) {
        var left: Node<T>? = null
        var right: Node<T>? = null
        var parent: Node<T>? = null
    }
}