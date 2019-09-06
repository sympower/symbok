package test;

import net.sympower.symbok.WriteLock;

public class NamedLock {

  @WriteLock("namedLock")
  public void createNamedLock() {
    System.out.println("New lock with custom name");
  }
}


