package com.SnapUrl.url_service.utils;
import java.time.Instant;

public class SnowflakeIdGenerator {

    private final long nodeId;
    private final long epoch = 1672531200000L; // Custom epoch (e.g., Jan 1, 2023)
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    private final int nodeIdBits = 10;
    private final int sequenceBits = 12;

    private final long maxNodeId = (1L << nodeIdBits) - 1;
    private final long maxSequence = (1L << sequenceBits) - 1;

    public SnowflakeIdGenerator(long nodeId) {
        if (nodeId > maxNodeId || nodeId < 0) {
            throw new IllegalArgumentException("Node ID out of range");
        }
        this.nodeId = nodeId;
    }

    public synchronized long nextId() {
        long currentTimestamp = timestamp();

        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & maxSequence;
            if (sequence == 0) {
                // Sequence exhausted, wait for next millisecond
                currentTimestamp = waitNextMillis(currentTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = currentTimestamp;

        return ((currentTimestamp - epoch) << (nodeIdBits + sequenceBits))
                | (nodeId << sequenceBits)
                | sequence;
    }

    private long timestamp() {
        return Instant.now().toEpochMilli();
    }

    private long waitNextMillis(long currentTimestamp) {
        while (currentTimestamp <= lastTimestamp) {
            currentTimestamp = timestamp();
        }
        return currentTimestamp;
    }
}
