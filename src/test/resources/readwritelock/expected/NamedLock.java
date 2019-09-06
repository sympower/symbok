package test;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class NamedLock {

  private final ReentrantReadWriteLock namedLock = new ReentrantReadWriteLock();

  public NamedLock() {
  }

  public void createNamedLock() {
    WriteLock var1 = this.namedLock.writeLock();
    var1.lock();

    try {
      System.out.println("New lock with custom name");
    }
    finally {
      var1.unlock();
    }

  }
}
