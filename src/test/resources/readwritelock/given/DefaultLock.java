package test;

import net.sympower.lombok.ReadLock;
import net.sympower.lombok.WriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DefaultLock {

    private int value;

    @ReadLock
    public int getValue() {
        return this.value;
    }

    @WriteLock
    public void setValue(int value) {
        this.value = value;
    }
}
