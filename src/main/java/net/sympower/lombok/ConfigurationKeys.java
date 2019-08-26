package net.sympower.lombok;

import lombok.core.configuration.ConfigurationKey;
import lombok.core.configuration.ConfigurationKeysLoader;
import org.kohsuke.MetaInfServices;

@MetaInfServices
public class ConfigurationKeys implements ConfigurationKeysLoader {

  public static final ConfigurationKey<String> READ_WRITE_LOCK_DEFAULT_FIELD_NAME =
      new ConfigurationKey<String>(
          "symbok.readWriteLock.defaultFieldName",
          "Default lock field name for @ReadLock and @WriteLock"
      ) {};
}
