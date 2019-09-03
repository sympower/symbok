package test;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class DefaultLock {
    private final ReentrantReadWriteLock $readWriteLock = new ReentrantReadWriteLock();
    private int value;

    public DefaultLock() {
    }

    public int getValue() {
        ReadLock var1 = this.$readWriteLock.readLock();
        var1.lock();

        try {
            return this.value;
        } finally {
            var1.unlock();
        }
    }

    public void setValue(int var1) {
        WriteLock var2 = this.$readWriteLock.writeLock();
        var2.lock();

        try {
            this.value = var1;
        } finally {
            var2.unlock();
        }

    }
}
