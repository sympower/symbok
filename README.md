# Symbok - Lombok extension

## Gradle
    dependencies {
        compileOnly("net.sympower:symbok:1.18.8-v1-SNAPSHOT")
        compileOnly("org.projectlombok:lombok:1.18.8")
        annotationProcessor("org.projectlombok:lombok:1.18.8")
    }

## Usage
**With extension**

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
      
      @WriteLock("generatedLock")
      public void createCustomLock() {
        System.out.println("New custom lock");
      }
      
      @ReadLock("existingLock")
      public void reuseExistingLock() {
        System.out.println("Reuse existing lock");
      }
    }

**Vanilla Java**

    public class LockTest {
      
      private final java.util.concurrent.locks.ReentrantReadWriteLock $readWriteLock = new ReentrantReadWriteLock();
      private final ReentrantReadWriteLock generatedLock = new ReentrantReadWriteLock();  
      private final ReentrantReadWriteLock existingLock = new ReentrantReadWriteLock();  
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
      
      public void createCustomLock() {
        this.generatedLock.writeLock().lock();  
        try {
          System.out.println("New custom lock");
        } finally {
          this.generatedLock.writeLock().unlock();
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
Default lock field name for `@ReadLock` and `@WriteLock`:
`symbok.readWriteLock.defaultFieldName=<fieldName>`