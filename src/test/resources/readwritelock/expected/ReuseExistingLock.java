package test;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

public class ReuseExistingLock {
    private final ReentrantReadWriteLock existingLock = new ReentrantReadWriteLock();

    public ReuseExistingLock() {
    }

    public void reuseExistingLock() {
        ReadLock var1 = this.existingLock.readLock();
        var1.lock();

        try {
            System.out.println("Reuse existing lock");
        } finally {
            var1.unlock();
        }

    }
}
