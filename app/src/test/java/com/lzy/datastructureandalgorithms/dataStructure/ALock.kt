package com.lzy.download

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.LockSupport
import kotlin.random.Random

/**
 * Created by 李志云 2019/3/27 10:03
 */
class ALock:Lock {
    val mArthread = AtomicReference<Thread>()
    val mQueue = LinkedBlockingQueue<Thread>()
    override fun lock() {
        while (!mArthread.compareAndSet(null, Thread.currentThread())){
            mQueue.add(Thread.currentThread())
            LockSupport.park()
            mQueue.remove(Thread.currentThread())
        }
    }

    override fun tryLock(): Boolean {
        return false
    }

    override fun tryLock(time: Long, unit: TimeUnit?): Boolean {
        return false
    }

    override fun unlock() {
        if (mArthread.compareAndSet(Thread.currentThread(),null)){
            val size = mQueue.size
            if (size > 0){
                val i = ARandom.random(size)
                var index = 0
                mQueue.forEach {
                    if (index == i){
                        LockSupport.unpark(it)
                    }else{
                        index++
                    }
                }
            }
        }
    }

    override fun lockInterruptibly() {
    }

    override fun newCondition(): Condition? {
        return null
    }
}