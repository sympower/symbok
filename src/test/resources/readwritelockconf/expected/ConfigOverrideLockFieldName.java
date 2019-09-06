package test;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class ConfigOverrideLockFieldName {

  private final ReentrantReadWriteLock customLock = new ReentrantReadWriteLock();

  public ConfigOverrideLockFieldName() {
  }

  public void configOverrideLockFieldName() {
    WriteLock var1 = this.customLock.writeLock();
    var1.lock();

    try {
      System.out.println("lock");
    }
    finally {
      var1.unlock();
    }

  }
}
