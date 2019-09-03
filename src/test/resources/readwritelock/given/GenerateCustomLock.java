package test;

import net.sympower.lombok.WriteLock;

public class GenerateCustomLock {

    @WriteLock("generatedLock")
    public void createCustomLock() {
        System.out.println("New custom lock");
    }
}


