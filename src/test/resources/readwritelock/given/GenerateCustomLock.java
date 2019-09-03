package test;

import net.sympower.symbok.WriteLock;

public class GenerateCustomLock {

    @WriteLock("generatedLock")
    public void createCustomLock() {
        System.out.println("New custom lock");
    }
}


