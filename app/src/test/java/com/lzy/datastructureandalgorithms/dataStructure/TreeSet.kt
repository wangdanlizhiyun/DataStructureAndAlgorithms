package com.lzy.datastructureandalgorithms.dataStructure

/**
 * Created by 李志云 2019/1/30 17:25
 */

class TreeSet<E>(private @Transient val m: TreeMap<E, Any>) {
        private val PRESENT = Any()

    fun add(e:E):Boolean{
        return m.put(e,PRESENT) == null
    }
    fun remove(o:Any):Boolean{
        return m.remove(o) == PRESENT
    }
    fun clear(){
        m.clear()
    }
}

