package net.sympower.symbok.javac.handler;

import net.sympower.symbok.SymbokTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class HandleReadWriteLockTest extends SymbokTest {

  @Test
  @DisplayName("Given methods are annotated with @ReadLock and @WriteLock " +
      "then should generate default lock and use it in methods to generate lock blocks")
  public void defaultLock() throws IOException {
    testClass("readwritelock", "DefaultLock");
  }

  @Test
  @DisplayName("Given method is annotated with @WriteLock(\"namedLock\") " +
      "and there is no field named \"namedLock\" in this class " +
      "then should generate lock field named as \"namedLock\" " +
      "and use it in given method to generate write lock block")
  public void namedLock() throws IOException {
    testClass("readwritelock", "NamedLock");
  }

  @Test
  @DisplayName("Given method is annotated with @ReadLock(\"existingLock\") " +
      "and there is a field named \"existingLock\" in this class " +
      "then should use \"existingLock\" field in given method to generate read lock block")
  public void reuseExistingLock() throws IOException {
    testClass("readwritelock", "ReuseExistingLock");
  }
}
