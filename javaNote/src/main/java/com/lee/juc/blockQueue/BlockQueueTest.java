package com.lee.juc.blockQueue;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockQueueTest {
    @Test
    public void test() throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        // 队列满不会阻塞
        boolean a = queue.offer("a");
        // add == offer
        boolean b = queue.add("a");
        // 队列满了会被阻塞
        /**
         * while (count == items.length) {
         *        notFull.await();
         *        enqueue(e);
         *
         *    await() 将当前节点放入到等待队列，并且释放state+唤醒后面的节点。
         *
         *
         *
         *
           }
         */
        queue.put("a");
        System.out.println(queue.size());
        queue.poll();
    }


    /**
     *         public final void await() throws InterruptedException {
     *             if (Thread.interrupted())
     *                 throw new InterruptedException();
     *                 // 这里将新节点加入到等待队列
     *             Node node = addConditionWaiter();
     *             // 这里释放state并唤醒等待线程
     *             int savedState = fullyRelease(node);
     *             int interruptMode = 0;
     *             while (!isOnSyncQueue(node)) {
     *                 LockSupport.park(this);
     *                 if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
     *                     break;
     *             }
     *             if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
     *                 interruptMode = REINTERRUPT;
     *             if (node.nextWaiter != null) // clean up if cancelled
     *                 unlinkCancelledWaiters();
     *             if (interruptMode != 0)
     *                 reportInterruptAfterWait(interruptMode);
     *         }
     */
}
