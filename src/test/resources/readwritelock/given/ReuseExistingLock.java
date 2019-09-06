package test;

import net.sympower.symbok.ReadLock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReuseExistingLock {

  private final ReentrantReadWriteLock existingLock = new ReentrantReadWriteLock();

  @ReadLock("existingLock")
  public void reuseExistingLock() {
    System.out.println("Reuse existing lock");
  }
}
