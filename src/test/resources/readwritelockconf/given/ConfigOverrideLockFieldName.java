package test;

import net.sympower.symbok.WriteLock;

public class ConfigOverrideLockFieldName {

  @WriteLock
  public void configOverrideLockFieldName() {
    System.out.println("lock");
  }
}


