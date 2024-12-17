package com.xin.sharding.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Snowflake {

    /** 序列的掩码，12个1，也就是(0B111111111111=0xFFF=4095) */
    private static final long SEQUENCE_MASK = 0xFFF;

    /**系统起始时间，这里取2020-01-01 **/
    private long startTimeStamp = 1577836800000L;

    /** 上次生成 ID 的时间截 */
    private long lastTimestamp = -1L;

    /** 工作机器 ID(0~31) */
    private long workerId;

    /** 数据中心 ID(0~31) */
    private long datacenterId;

    /** 毫秒内序列(0~4095) */
    private long sequence = 0L;


    /**
     * @param datacenterId 数据中心 ID (0~31)
     * @param workerId     工作机器 ID (0~31)
     */
    public Snowflake(@Value("${snowflake.datacenterId}") long datacenterId, @Value("${snowflake.workerId}") long workerId) {
        if (workerId > 31 || workerId < 0) {
            throw new IllegalArgumentException("workId必须在0-31之间，当前="+workerId);
        }
        if (datacenterId > 31 || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId必须在0-31之间，当前="+datacenterId);
        }

        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 加锁，线程安全
     * @return long 类型的 ID
     */
    public synchronized long nextId() {
        long timestamp = currentTime();

        // 如果当前时间小于上一次 ID 生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("时钟回退！时间差="+(lastTimestamp - timestamp));
        }

        // 同一毫秒内，序列增加
        if (lastTimestamp == timestamp) {
            //超出阈值。思考下为什么这么运算？
            sequence = (sequence + 1) & SEQUENCE_MASK;
            // 毫秒内序列溢出
            if (sequence == 0) {
                //自旋等待下一毫秒
                while ((timestamp= currentTime()) <= lastTimestamp);
            }
        } else {
            //已经进入下一毫秒，从0开始计数
            sequence = 0L;
        }

        //赋值为新的时间戳
        lastTimestamp = timestamp;

        //移位拼接
        long id = ((timestamp - startTimeStamp) << 22)
                | (datacenterId << 17)
                | (workerId << 12)
                | sequence;

        System.out.println("new id = "+id);
        System.out.println("bit id = "+toBit(id));

        return id;
    }


    /**
     * 返回当前时间，以毫秒为单位
     */
    protected long currentTime() {
        return System.currentTimeMillis();
    }

    /**
     * 转成二进制展示
     */
    public static String toBit(long id){
        String bit = StringUtils.leftPad(Long.toBinaryString(id), 64, "0");
        return bit.substring(0,1) +
                " - " +
                bit.substring(1,42) +
                " - " +
                bit.substring(42,52)+
                " - " +
                bit.substring(52,64);
    }
    public static void main(String[] args) {
        Snowflake idWorker = new Snowflake(1, 1);

        for (int i = 0; i < 10; i++) {
            long id = idWorker.nextId();
            System.out.println(id);
            System.out.println(toBit(id));
        }
        System.out.println(toBit(503226745374126080L));

    }
}