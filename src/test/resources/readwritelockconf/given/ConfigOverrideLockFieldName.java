package test;

import net.sympower.lombok.WriteLock;

public class ConfigOverrideLockFieldName {

    @WriteLock
    public void configOverrideLockFieldName() {
        System.out.println("lock");
    }
}


