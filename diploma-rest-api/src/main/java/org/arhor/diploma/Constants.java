package org.arhor.diploma;

public final class Constants {
  private Constants() {}

  public static final class Common {
    private Common() {}

    public static final long SERIAL_VERSION = 1L;

    public static final Object[] EMPTY_ARRAY = {};
  }

  public static final class Entities {
    private Entities() {}

    public static final String ACCOUNT = "Account";
  }

  public static final class Props {
    private Props() {}

    public static final String ID = "id";
  }

  public static final class Caches {
    private Caches() {}

    public static final String ACCOUNT = "dp_cache_account";
    public static final String ACCOUNT_BY_ID = "dp_account_cache_by_id";
    public static final String ACCOUNT_BY_USERNAME = "dp_account_cache_by_username";
  }
}
