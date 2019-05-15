package com.lzy.datastructureandalgorithms.dataStructure


/**
 * Created by 李志云 2019/1/29 17:24
 */
class TreeMap<K, V>(private val comparator: Comparator<in K>? = null) {
    @Transient
    private var root: TreeMapEntry<K, V>? = null
    @Transient
    private var size = 0
    @Transient
    private var modCount = 0

    fun clear() {
        size = 0
        root = null
        modCount++
    }

    fun get(key: Any): V? {
        return getEntry(key)?.value
    }

    fun remove(key: Any): V? {
        val p = getEntry(key) ?: return null
        val oldValue = p.value
        deleteEntry(p)
        return oldValue
    }

    private fun getEntry(key: Any): TreeMapEntry<K, V>? {
        var p = root
        while (p != null) {
            val cmp = compare(key, p.key)
            if (cmp < 0) {
                p = p.left
            } else if (cmp > 0) {
                p = p.right
            } else {
                return p
            }
        }
        return null
    }

    private fun deleteEntry(e: TreeMapEntry<K, V>) {
        var p = e
        modCount++
        size--
        if (p.left != null && p.right != null) {
            val s = successor(p)
            s?.let {
                p.key = s.key
                p.value = s.value
                p = s
            }
        }
        val replacement = if (p.left != null) p.left else p.right
        if (replacement != null) {
            replacement.parent = p.parent
            if (p.parent == null) {
                root = replacement
            } else if (replacement == p.parent?.left) {
                p.parent?.left = replacement
            } else {
                p.parent?.right = replacement
            }
            p.left = null
            p.right = null
            p.parent = null
            if (p.color == BLACK) {
                fixAfterDeletion(p)
            }
        } else if (p.parent == null) {
            root = null
        } else {
            if (p.color == BLACK) {
                fixAfterDeletion(p)
            }
            p.parent?.let {
                if (it.left == p) {
                    it.left = null
                } else if (it.right == p) {
                    it.right = null
                }
                p.parent = null
            }
        }
    }

    /**
     * 寻找比p大的最小的点
     */
    private fun successor(p: TreeMapEntry<K, V>): TreeMapEntry<K, V>? {
        if (p == null) {
            return null
        } else if (p.right != null) {
            var t = p.right
            t?.let {
                while (it.left != null) {
                    t = it.left
                }
            }
            return t
        } else {//删除操作时用不上
            var ch = p
            var p = ch.parent
            while (p != null && ch == p.right) {
                ch = p
                p = ch.parent
            }
            return p
        }
    }

    private fun fixAfterDeletion(x: TreeMapEntry<K, V>) {
        var p: TreeMapEntry<K, V>? = x
        while (p != null && colorOf(p) == BLACK) {
            var b = brotherOf(p)
            //case 1 红兄
            if (colorOf(b) == RED) {
                setColor(b, BLACK)
                setColor(parentOf(p), RED)
                if (p == p.parent?.left) rotateLeft(parentOf(p)) else rotateRight(parentOf(p))
                b = brotherOf(p)
            }
            //case 2黑兄2黑子
            if (colorOf(leftOf(b)) == BLACK && colorOf(rightOf(b)) == BLACK) {
                setColor(b, RED)
                p = parentOf(p)
            } else {
                //case 3 左子红
                if (colorOf(rightOf(b)) == BLACK) {
                    setColor(leftOf(b), BLACK)
                    setColor(b, RED)
                    rotateRight(b)
                    b = brotherOf(p)
                }
                //case 4 右子红
                setColor(b, colorOf(parentOf(p)))
                setColor(parentOf(p), BLACK)
                setColor(rightOf(b), BLACK)
                rotateLeft(parentOf(p))
                p = root
            }
        }
    }


    fun put(key: K, value: V): V? {
        if (key == null) throw NullPointerException()
        var t = root
        if (t == null) {
            compare(key, key)
            root = TreeMapEntry(key, value, null)
            size = 1
            modCount++
            return null
        }
        var cmp = 0
        var parent: TreeMapEntry<K, V>? = null
        do {
            t?.let {
                parent = t
                cmp = compare(key, it.key)
                if (cmp < 0) {
                    t = it.left
                } else if (cmp > 0) {
                    t = it.right
                } else {
                    return it.setValue(value)
                }
            }
        } while (t != null)
        parent?.let {
            val e = TreeMapEntry(key, value, it)
            if (cmp < 0) {
                it.left = e
            } else {
                it.right = e
            }
            fixAfterInsertion(e)
            size++
            modCount++
        }
        return null
    }

    private fun fixAfterInsertion(e: TreeMapEntry<K, V>?) {
        var x = e
        x?.color = RED
        while (x != null && x != root && x.parent?.color == RED) {
            //源码做法是多写了几行重复代码但是少执行几个判断
            //代码较少的写法
            val y = uncleOf(x)
            if (colorOf(y) == RED) {//夫叔相争，上交祖父
                setColor(parentOf(x), BLACK)
                setColor(y, BLACK)
                setColor(parentOf(parentOf(x)), RED)
                x = parentOf(parentOf(x))
            } else {
                if (x == rightOf(parentOf(x))) {//左旋夫，换一个姿势，寻找新的希望
                    x = parentOf(x)
                    rotateLeft(x)//其实可以先旋转再转移指针
                } else {//其实和上一步相辅相成，左右旋无意义时，红点冲突上移右转,从祖父的另一脉寻找新的希望
                    setColor(parentOf(x), BLACK)
                    setColor(parentOf(parentOf(x)), RED)
                    rotateRight(parentOf(parentOf(x)))
                }
            }
        }
        root?.color = BLACK
    }


    fun rotateLeft(p: TreeMapEntry<K, V>?) {
        p?.let {
            val r = p.right
            r?.let {
                p.right = r.left
                r.left?.let { it.parent = p }
                r.parent = p.parent
                if (p.parent == null) {
                    root = r
                } else if (p == p.parent?.left) {
                    p.parent?.left = r
                } else {
                    p.parent?.right = r
                }
                r.left = p
                p.parent = r
            }
        }
    }

    fun rotateRight(p: TreeMapEntry<K, V>?) {
        p?.let {
            val l = p.left
            l?.let {
                p.left = l.right
                l.right?.let { it.parent = p }
                l.parent = p.parent
                if (p.parent == null) {
                    root = l
                } else if (p == p.parent?.left) {
                    p.parent?.left = l
                } else {
                    p.parent?.right = l
                }
                l.right = p
                p.parent = l
            }
        }
    }

    private fun compare(k1: Any, k2: Any?): Int {
        return comparator?.compare(k1 as K, k2 as K) ?: (k1 as Comparable<in K>).compareTo((k2 as K))
    }


    private fun setColor(p: TreeMapEntry<K, V>?, c: Boolean) {
        p?.color = c
    }

    private fun colorOf(p: TreeMapEntry<K, V>?): Boolean {
        return p?.color ?: BLACK
    }

    private fun parentOf(p: TreeMapEntry<K, V>?): TreeMapEntry<K, V>? {
        return p?.parent
    }

    private fun leftOf(p: TreeMapEntry<K, V>?): TreeMapEntry<K, V>? {
        return p?.left
    }

    private fun rightOf(p: TreeMapEntry<K, V>?): TreeMapEntry<K, V>? {
        return p?.right
    }

    private fun brotherOf(p: TreeMapEntry<K, V>?): TreeMapEntry<K, V>? {
        return if (p == p?.parent?.left) p?.parent?.right else p?.parent?.left
    }

    private fun uncleOf(x: TreeMapEntry<K, V>?): TreeMapEntry<K, V>? {
        return if (x?.parent == x?.parent?.parent?.left) x?.parent?.parent?.right else x?.parent?.parent?.left
    }


    companion object {
        private val RED = false
        private val BLACK = true

        class TreeMapEntry<K, V>(override var key: K, override var value: V, var parent: TreeMapEntry<K, V>?) :
            Map.Entry<K, V> {
            var left: TreeMapEntry<K, V>? = null
            var right: TreeMapEntry<K, V>? = null
            var color = BLACK

            fun setValue(value: V): V {
                val oldValue = this.value
                this.value = value
                return oldValue
            }
        }
    }
}

