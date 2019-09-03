package test;

import net.sympower.symbok.ReadLock;
import net.sympower.symbok.WriteLock;

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
