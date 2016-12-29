package com.lcw.herakles.platform.common.entity.id;

import java.util.HashSet;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

/**
 * tweeter的snowflake 移植到Java
 * 
 * @author chenwulou
 *
 */
@Slf4j
public class IdWorker {
    // 机器id
    private long workerId;
    // 数据中心id
    private long datacenterId;
    // 0，并发控制
    private long sequence = 0L;
    // 时间起始标记点，作为基准，一般取系统的最近时间
    private long twepoch = 1288834974657L;
    // 机器标识位数
    private long workerIdBits = 5L;
    // 数据中心标识位数
    private long datacenterIdBits = 5L;
    // 机器ID最大值: 1023
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    // 数据中心ID最大值: 1023
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    // 毫秒内自增位
    private long sequenceBits = 12L;
    // 12
    private long workerIdShift = sequenceBits;
    // 17
    private long datacenterIdShift = sequenceBits + workerIdBits;
    // 22
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    // 4095,111111111111,12位
    private long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long lastTimestamp = -1L;

    public IdWorker(long workerId, long datacenterId) {
        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String
                    .format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format(
                    "datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        log.info(String.format(
                "worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d",
                timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId));
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            log.error(String.format("clock is moving backwards.  Rejecting requests until %d.",
                    lastTimestamp));
            throw new RuntimeException(String.format(
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }


    // test
    static class IdWorkThread implements Runnable {
        private Set<Long> set;
        private IdWorker idWorker;

        public IdWorkThread(Set<Long> set, IdWorker idWorker) {
            this.set = set;
            this.idWorker = idWorker;
        }

        @Override
        public void run() {
            int i = 0;
            long start = System.currentTimeMillis();
            while(i<=1000){
                idWorker.nextId();
            }
            System.out.println(System.currentTimeMillis()-start);
//            while (true) {
//                long id = idWorker.nextId();
//                if (!set.add(id)) {
//                    System.out.println("duplicate:" + id);
//                }
//            }
        }
    }

    public static void main(String[] args) {
        Set<Long> set = new HashSet<Long>();
        final IdWorker idWorker1 = new IdWorker(0, 0);
        final IdWorker idWorker2 = new IdWorker(1, 0);
        Thread t1 = new Thread(new IdWorkThread(set, idWorker1));
        Thread t2 = new Thread(new IdWorkThread(set, idWorker2));
        t1.setDaemon(true);
        t2.setDaemon(true);
        t1.start();
        t2.start();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
