package net.sympower.symbok.javac.handler.conf;

import net.sympower.symbok.SymbokTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class HandleReadWriteLockConfTest extends SymbokTest {

  @Test
  @DisplayName("Given lombok.config contains 'symbok.readWriteLock.defaultFieldName=customLock' " +
      "and method is annotated with @WriteLock " +
      "then should generate a lock field named as 'customLock' " +
      "and use it in method to generate write lock block")
  public void configOverrideLockFieldName() throws IOException {
    testClass("readwritelockconf", "ConfigOverrideLockFieldName");
  }
}
