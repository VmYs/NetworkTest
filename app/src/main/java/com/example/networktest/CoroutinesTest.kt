package com.example.networktest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {
//    GlobalScope.launch {
//        println("codes run in coroutine scope")
//        //delay()函数是一个非阻塞式的挂起函数，它只会挂起当前协程，并不会影响其他协程的运行。
//        //而Thread.sleep()方法会阻塞当前的线程，这样运行在该线程下的所有协程都会被阻塞。
//        //因此delay后的那句话并不会打印出来。
//        delay(1500)
//        println("codes run in coroutine scope finished")
//    }
//    Thread.sleep(1000)

    //runBlocking函数同样会创建一个协程的作用域，但是它可以保证在协程作用域内的所有代码和子协程没有全部执行完之前一直阻塞当前线程。
    //通常在测试环境下使用
    runBlocking {
//        println("codes run in coroutine scope")
//        delay(1500)
//        println("codes run in coroutine scope finished")
//        //launch函数在当前协程的作用域下创建多个子协程
//        launch {
//            println("launch1")
//            delay(1000)
//            println("launch1 finished")
//        }
//        launch {
//            println("launch2")
//            delay(1000)
//            println("launch2 finished")
//        }

//        //验证协程并发效率高的特性
//        //重复10万次打印耗时700多毫秒
//        val start = System.currentTimeMillis()
//        runBlocking {
//            repeat(100000) {
//                launch {
//                    println("@")
//                }
//            }
//        }
//        val end = System.currentTimeMillis()
//        println(end - start)

        //coroutineScope函数在runBlocking下创建子协程。
        //coroutineScope函数会将外部协程挂起，只有当它作用域内的所有代码和子协程都执行完毕之后，coroutineScope函数之后的代码才能得到运行。
        //coroutineScope函数只会阻塞当前协程，既不影响其他协程，也不影响任何线程。
//        coroutineScope {
//            launch {
//                for (i in 1..10) {
//                    println(i)
//                    delay(1000)
//                }
//            }
//        }
//        println("coroutineScope finished")
//    }
//    println("runBlocking finished")

//        val result = async {
//            5 + 5
//        }
//        println(result.await())

        //代替async
        val result = withContext(Dispatchers.Default) {
            5 + 5
        }
        println(result)
    }
}