package pl.javastart.task;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Scanner;

public class Main {
    static final String DISPLAY_PATTERN = "yyyy-MM-dd HH:mm:ss";
    static final Format[] PATTERNS = new Format[]{
        new Format("yyyy-MM-dd HH:mm:ss", false),
        new Format("yyyy-MM-dd", true),
        new Format("dd.MM.yyyy HH:mm:ss", false)};

    public static void main(String[] args) {
        Main main = new Main();
        main.run(new Scanner(System.in));
    }

    public void run(Scanner scanner) {
        // uzupełnij rozwiązanie. Korzystaj z przekazanego w parametrze scannera

        ZonedDateTime defaultDateTime = getDateTime(scanner);

        if (defaultDateTime == null) {
            System.out.println("Nie udało się utworzyć daty");
        } else {
            showTimeAtDifferentZones(defaultDateTime);
        }
    }

    private void showAvailableFormats() {
        System.out.println("Dostępne formaty danych:");
        for (Format format : PATTERNS) {
            System.out.println(format);
        }
    }

    private ZonedDateTime getDateTime(Scanner scanner) {
        showAvailableFormats();
        System.out.println("Podaj datę:");
        String userInput = scanner.nextLine();
        LocalDateTime localDateTime;
        ZonedDateTime zonedDateTime = null;
        for (Format format : PATTERNS) {
            try {
                DateTimeFormatter pattern = DateTimeFormatter.ofPattern(format.getFormat());
                TemporalAccessor temporalAccessor = pattern.parse(userInput);
                if (format.isOnlyDate()) {
                    LocalDate date = LocalDate.from(temporalAccessor);
                    localDateTime = date.atTime(LocalTime.MIDNIGHT);

                } else {
                    localDateTime = LocalDateTime.from(temporalAccessor);
                }
                zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
            } catch (DateTimeParseException e) {
                //ignore
            }
        }
        return zonedDateTime;
    }

    private void showTimeAtDifferentZones(ZonedDateTime zonedDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DISPLAY_PATTERN);
        for (Zone zone : Zone.values()) {
            ZonedDateTime result = zonedDateTime.withZoneSameInstant(zone.getZoneId());
            System.out.println(zone.getDisplayName() + ": " + result.format(formatter));
        }
    }
}
