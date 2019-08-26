package net.sympower.lombok;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * <pre>
 * void methodAnnotatedWithReadLock() {
 *   this.&lt;LOCK_NAME&gt;.readLock().lock();
 *   try {
 *
 *     // method body
 *
 *   } finally {
 *     this.&lt;LOCK_NAME&gt;.readLock().unlock();
 *   }
 * }
 * <pre>
 */
@Target(METHOD)
@Retention(SOURCE)
public @interface ReadLock {

  /**
   * Name of the lock.
   * <p>
   * If no lock with the specified name exists a new {@link java.util.concurrent.locks.ReentrantReadWriteLock ReentrantReadWriteLock}
   * will be created, using this name.
   */
  String value() default "";
}
