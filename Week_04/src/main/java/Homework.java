import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Homework {
    public static void main(String[] args) {
        _1_threadJoin();
        _2_synchronized_1();
        _3_synchronized_2();
        _4_lock_1();
        _5_lock_2();
        _6_future_1();
        _7_futureTask_1();
        _8_stream();
        _9_countDownLatch();
        _10_cyclicBarrier();
    }

    private static void _1_threadJoin() {
        final Long[] result = new Long[1];
        Thread thread = new Thread(() -> result[0] = SomeHeavyWork.doWork(),
                "WorkerThread");
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(JSONObject.toJSON(result[0]));
    }

    private static void _2_synchronized_1() {
        final Long[] result = new Long[1];
        Object lockv_1 = new Object();
        Thread thread = new Thread(() -> {
            synchronized (lockv_1) {
                result[0] = SomeHeavyWork.doWork();
                lockv_1.notifyAll();
            }
        }, "WorkerThread");
        thread.start();
        synchronized (lockv_1) {
            while (result[0] == null) {
                try {
                    lockv_1.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(JSONObject.toJSON(result[0]));
        }
    }

    /**
     * 简单将主线程的打印逻辑放在异步线程的后面，然后会因为synchronized阻塞住
     */
    private static void _3_synchronized_2() {
        final Long[] result = new Long[1];
        Object lockv_1 = new Object();
        Thread thread = new Thread(() -> {
            synchronized (lockv_1) {
                result[0] = SomeHeavyWork.doWork();
            }
        }, "WorkerThread");
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (lockv_1) {
            System.out.println(JSONObject.toJSON(result[0]));
        }
    }

    private static void _4_lock_1() {
        final Long[] result = new Long[1];
        Lock lock = new ReentrantLock();
        Thread thread = new Thread(() -> {
            lock.lock();
            try {
                result[0] = SomeHeavyWork.doWork();
            } finally {
                lock.unlock();
            }
        }, "WorkerThread");
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (result[0] == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(JSONObject.toJSON(result[0]));
    }

    private static void _5_lock_2() {
        final Long[] result = new Long[1];
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Thread thread = new Thread(() -> {
            lock.lock();
            try {
                result[0] = SomeHeavyWork.doWork();
            } finally {
                condition.signalAll();
                lock.unlock();
            }
        }, "WorkerThread");
        thread.start();

        lock.tryLock();
        while (result[0] == null) {
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();
        System.out.println(JSONObject.toJSON(result[0]));
    }

    private static void _6_future_1() {
        final Long[] result = new Long[1];
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Long> future = executorService.submit(SomeHeavyWork::doWork);
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private static void _7_futureTask_1() {
        final Long[] result = new Long[1];
        FutureTask<Long> task = new FutureTask<Long>(SomeHeavyWork::doWork);
        new Thread(task).start();
        try {
            System.out.println(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
        }
    }

    private static void _8_stream() {
        List<Integer> list = new ArrayList<>();
        IntStream.range(0, 1).forEach(i -> list.add(i));
        BlockingQueue<Long> blockingQueue = new LinkedBlockingQueue(1);
        List<Long> longList = list.stream().parallel()
                .map(i -> SomeHeavyWork.doWork())
                .collect(Collectors.toList());
        longList.stream().forEach(
                i -> {
                    try {
                        blockingQueue.put(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
        System.out.println("blockingQueue:" + blockingQueue.toString());
    }

    private static void _9_countDownLatch() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        GetResult getResult = new GetResult(0, countDownLatch);
        for (int i = 0; i < 1; i++) {
            new Thread(getResult).start();
        }
        try {
            countDownLatch.await(); // 注意跟CyclicBarrier不同，这里在主线程await
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getResult.result());
        System.out.println("==>主线程执行结束。。。。");
    }

    private static void _10_cyclicBarrier() {
        final boolean[] flag = {false};
        CyclicBarrier cyclicBarrier = new CyclicBarrier(1, new Runnable() {
            @Override
            public void run() {
                System.out.println("回调>>" + Thread.currentThread().getName());
                System.out.println("回调>>线程组执行结束");
                System.out.println("==>各个子线程执行结束。。。。");
                flag[0] = true;
            }
        });
        GetResult2 getResult = new GetResult2(0, cyclicBarrier);
        for (int i = 0; i < 1; i++) {
            new Thread(getResult).start();
        }
        while (!flag[0]) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(getResult.result());
    }

    static class GetResult implements Runnable {
        private int id;
        private CountDownLatch latch;
        private Long result = 0L;

        public GetResult(int id, CountDownLatch latch) {
            this.id = id;
            this.latch = latch;
        }

        @Override
        public void run() {
            synchronized (this) {
                result = SomeHeavyWork.doWork();
                latch.countDown();
            }
        }

        public Long result() {
            return result;
        }
    }

    static class GetResult2 implements Runnable {
        private int id;
        private CyclicBarrier cyc;
        private Long result = 0L;

        public GetResult2(int id, CyclicBarrier cyc) {
            this.id = id;
            this.cyc = cyc;
        }

        @Override
        public void run() {
            synchronized (this) {
                try {
                    result = SomeHeavyWork.doWork();
                    cyc.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public Long result() {
            return result;
        }
    }
}
