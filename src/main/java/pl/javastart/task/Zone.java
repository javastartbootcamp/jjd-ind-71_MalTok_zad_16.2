package pl.javastart.task;

import java.time.ZoneId;

public enum Zone {
    LOCAL("Czas lokalny", ZoneId.systemDefault()),
    UTC("UTC", ZoneId.of("UTC")),
    LONDON("Londyn", ZoneId.of("Europe/London")),
    LOS_ANGELES("Los Angeles", ZoneId.of("America/Los_Angeles")),
    SYDNEY("Sydney", ZoneId.of("Australia/Sydney"));

    private final String displayName;
    private final ZoneId zoneId;

    Zone(String displayName, ZoneId zoneId) {
        this.displayName = displayName;
        this.zoneId = zoneId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }
}
