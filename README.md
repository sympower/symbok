# Symbok - Lombok extension

## Gradle

    dependencies {
        compileOnly("net.sympower:symbok:1.18.10-v1-SNAPSHOT")
        annotationProcessor("net.sympower:symbok:1.18.10-v1-SNAPSHOT")
        
        compileOnly("org.projectlombok:lombok:1.18.10")
        annotationProcessor("org.projectlombok:lombok:1.18.10")
    }

## Usage
**Code when using Lombok with Symbok extension annotations**

    public class LockTest {
      
      private final ReentrantReadWriteLock existingLock = new ReentrantReadWriteLock();
      private int value;
    
      @WriteLock
      public void setValue(int value) {
        this.value = value;
      }
       
      @ReadLock 
      public int getValue() {
        return this.value;
      }
      
      @WriteLock("namedLock")
      public void createNamedLock() {
        System.out.println("New lock with custom name");
      }
      
      @ReadLock("existingLock")
      public void reuseExistingLock() {
        System.out.println("Reuse existing lock");
      }
    }

**Code if you would implement the above functionality in plain Java**

    public class LockTest {
      
      private final java.util.concurrent.locks.ReentrantReadWriteLock $readWriteLock = new ReentrantReadWriteLock();
      private final java.util.concurrent.locks.ReentrantReadWriteLock namedLock = new ReentrantReadWriteLock();  
      private final java.util.concurrent.locks.ReentrantReadWriteLock existingLock = new ReentrantReadWriteLock();  
      private int value;
        
      public void setValue(int value) {
        this.$readWriteLock.writeLock().lock();  
        try {
          this.value = value;
        } finally {
          this.$readWriteLock.writeLock().unlock();
        }
      }
      
      public int getValue() {
        this.$readWriteLock.readLock().lock();
        try {
          return this.value;
        } finally {
          this.$readWriteLock.readLock().unlock();
        }
      }
      
      public void createNamedLock() {
        this.namedLock.writeLock().lock();  
        try {
          System.out.println("New lock with custom name");
        } finally {
          this.namedLock.writeLock().unlock();
        }
      }
      
      public int reuseExistingLock() {
        this.existingLock.readLock().lock();
        try {
          System.out.println("Reuse existing lock");
        } finally {
          this.existingLock.readLock().unlock();
        }
      }
      
    }

## Configuration (lombok.config)
Default lock field name for `@ReadLock` and `@WriteLock` can be overridden:

    symbok.readWriteLock.defaultFieldName=$readWriteLock

## IntelliJ IDEA
`Enable annotation processing` from the settings to properly build and test the project.
