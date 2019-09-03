package test;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class GenerateCustomLock {
    private final ReentrantReadWriteLock generatedLock = new ReentrantReadWriteLock();

    public GenerateCustomLock() {
    }

    public void createCustomLock() {
        WriteLock var1 = this.generatedLock.writeLock();
        var1.lock();

        try {
            System.out.println("New custom lock");
        } finally {
            var1.unlock();
        }

    }
}
