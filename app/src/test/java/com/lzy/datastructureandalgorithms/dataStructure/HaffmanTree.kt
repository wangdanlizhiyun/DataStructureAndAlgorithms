package com.lzy.datastructureandalgorithms.dataStructure

import org.junit.Test
import java.lang.StringBuilder
import java.lang.reflect.ParameterizedType
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by 李志云 2019/1/24 17:12
 */
class HaffmanTree<T> {
    lateinit var root: TreeNode<T>

    fun createHuffmanTree(list: ArrayList<TreeNode<T>>): TreeNode<T> {
        while (list.size > 1) {
            Collections.sort(list)
            val left = list.get(list.size - 1)
            val right = list.get(list.size - 2)
            val parent = TreeNode<T>(null, left.weight + right.weight)
            parent.leftChild = left
            parent.rightChild = right
            left.parent = parent
            right.parent = parent
            list.remove(left)
            list.remove(right)
            list.add(parent)
        }
        root = list.get(0)
        return root
    }

    fun showHuffman(root: TreeNode<T>) {
        val list: java.util.LinkedList<TreeNode<T>> = java.util.LinkedList()
        list.offer(root)
        while (list.isNotEmpty()) {
            val node = list.pop()
            if (node.leftChild == null && node.rightChild == null) {
                System.out.print("${node.data}_${node.weight} ")
            }
            node.leftChild?.let { list.offer(it) }
            node.rightChild?.let { list.offer(it) }
        }
    }

    fun getCode(node: TreeNode<T>) :String{
        val stack = com.lzy.datastructureandalgorithms.dataStructure.Stack<String>()
//        val stack = java.util.Stack<String>()
        var temp: TreeNode<T>? = node
        while (temp != null) {
            temp.parent?.let {
                stack.push(if (it.leftChild == temp) "0" else "1")
            }
            temp = temp.parent
        }
        val sb = StringBuilder()
        while (stack.isNotEmpty()){
            sb.append(stack.pop())
        }
        return sb.toString()
    }

    @Test
    fun test() {
        val list = ArrayList<TreeNode<String>>()
        val g = TreeNode("g", 6)
        list.add(TreeNode("a", 50))
        list.add(TreeNode("b", 10))
        list.add(TreeNode("c", 5))
        list.add(TreeNode("d", 20))
        list.add(TreeNode("e", 150))
        list.add(g)
        val haffmanTree = HaffmanTree<String>()
        haffmanTree.createHuffmanTree(list)
        haffmanTree.showHuffman(haffmanTree.root)
        System.out.print("${haffmanTree.getCode(g)}")

    }

    companion object {
        class TreeNode<T>(var data: T?, var weight: Int) : Comparable<TreeNode<T>> {
            var leftChild: TreeNode<T>? = null
            var rightChild: TreeNode<T>? = null
            var parent: TreeNode<T>? = null


            override fun compareTo(other: TreeNode<T>): Int {
                if (this.weight > other.weight) return -1
                if (this.weight < other.weight) return 1
                return 0
            }

        }
    }
}