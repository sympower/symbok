package net.sympower.lombok;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * <pre>
 * void methodAnnotatedWithWriteLock() {
 *   this.&lt;LOCK_NAME&gt;.writeLock().lock();
 *   try {
 *
 *     // method body
 *
 *   } finally {
 *     this.&lt;LOCK_NAME&gt;.writeLock().unlock();
 *   }
 * }
 * </pre>
 */
@Target(METHOD)
@Retention(SOURCE)
public @interface WriteLock {

  /**
   * Name of the lock.
   * <p>
   * If no lock with the specified name exists a new {@link java.util.concurrent.locks.ReentrantReadWriteLock ReentrantReadWriteLock}
   * will be created, using this name.
   */
  String value() default "";
}
