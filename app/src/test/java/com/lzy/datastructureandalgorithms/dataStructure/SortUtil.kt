package com.lzy.datastructureandalgorithms.dataStructure

/**
 * Created by 李志云 2019/1/16 15:40
 */
class SortUtil {

    companion object {
        fun binarySearch(array: IntArray, fromIndex: Int, toIndex: Int, key: Int): Int {
            var low = fromIndex
            var high = toIndex - 1
            while (low <= high) {
                val mid = (low + high).shr(1)
                val midValue = array[mid]
                if (key > midValue) {
                    low = mid + 1

                } else if (key < midValue) {
                    high = mid - 1
                } else {
                    return mid
                }

            }
            return -(low + 1)
        }

        fun quickSort(array: IntArray, begin: Int, end: Int) {
            if (end - begin <= 0) return
            var x = array[begin]
            var low = begin
            var high = end
            var direction = true
//            val size = high -low
            for (i in 0 until high -low) {
                if (direction) {//right to left
                    if (array[high] < x) {
                        array[low++] = array[high]
                        direction = !direction
                    } else {
                        high--
                    }
                } else {
                    if (array[low] > x) {
                        array[high--] = array[low]
                        direction = !direction
                    } else {
                        low++
                    }
                }
            }
            array[low] = x
            quickSort(array, begin, low - 1)
            quickSort(array, low + 1, end)
        }


        fun mergeSort(array: IntArray,left:Int,right:Int){
            if (left == right) return
            val mid = (left + right).shr(1)
            mergeSort(array,left,mid)
            mergeSort(array,mid+1,right)
            merge(array,left,mid+1,right)
        }

        private fun merge(array: IntArray, left: Int, mid: Int, right: Int) {
            val leftSize = mid - left
            val rightSize = right - mid + 1
            val leftArray = IntArray(leftSize)
            val rightArray = IntArray(rightSize)
            for (i in left until mid){
                leftArray[i-left] = array[i]
            }
            for (i in mid .. right){
                rightArray[i-mid] = array[i]
            }

            var i = 0
            var j = 0
            var k = left
            while(i < leftSize && j < rightSize){
                if (leftArray[i] < rightArray[j]){
                    array[k] = leftArray[i]
                    k++
                    i++
                }else{
                    array[k] = rightArray[j]
                    k++
                    j++
                }
            }
            while (i < leftSize){
                array[k] = leftArray[i]
                k++
                i++
            }
            while (j < rightSize){
                array[k] = rightArray[j]
                k++
                j++
            }
        }
    }



}