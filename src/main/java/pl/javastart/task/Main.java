package pl.javastart.task;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.run(new Scanner(System.in));
    }

    public void run(Scanner scanner) {
        // uzupełnij rozwiązanie. Korzystaj z przekazanego w parametrze scannera

        Format[] patterns = Format.values();

        ZonedDateTime defaultDateTime = getDateTime(scanner, patterns);

        if (defaultDateTime == null) {
            System.out.println("Nie udało się utworzyć daty");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Format.DATE_TIME_WITH_DASH.getFormat());

            System.out.println("Czas lokalny: " + defaultDateTime.format(formatter));

            String utcTime = getTimeAtZone(defaultDateTime, "UTC");
            System.out.println("UTC: " + utcTime);

            String londonTime = getTimeAtZone(defaultDateTime, "Europe/London");
            System.out.println("Londyn: " + londonTime);

            String losAngelesTime = getTimeAtZone(defaultDateTime, "America/Los_Angeles");
            System.out.println("Los Angeles: " + losAngelesTime);

            String sydneyTime = getTimeAtZone(defaultDateTime, "Australia/Sydney");
            System.out.println("Sydney: " + sydneyTime);
        }
    }

    private void showAvailableFormats(Format[] patternsArray) {
        System.out.println("Dostępne formaty danych:");
        for (Format format : patternsArray) {
            System.out.println(format.getFormat());
        }
    }

    private ZonedDateTime getDateTime(Scanner scanner, Format[] patternsArray) {
        showAvailableFormats(patternsArray);
        System.out.println("Podaj datę:");
        String userInput = scanner.nextLine();
        LocalDateTime localDateTime;
        ZonedDateTime zonedDateTime = null;
        for (Format format : patternsArray) {
            try {
                DateTimeFormatter pattern = DateTimeFormatter.ofPattern(format.getFormat());
                TemporalAccessor temporalAccessor = pattern.parse(userInput);
                if (format.getFormat().equals(Format.DATE_WITH_DASH.getFormat())) {
                    LocalDate date = LocalDate.from(temporalAccessor);
                    localDateTime = date.atTime(0, 0, 0);

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

    private String getTimeAtZone(ZonedDateTime zonedDateTime, String zoneName) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Format.DATE_TIME_WITH_DASH.getFormat());
        ZoneId utcZone = ZoneId.of(zoneName);
        ZonedDateTime result = zonedDateTime.withZoneSameInstant(utcZone);
        return result.format(formatter);
    }

    enum Format {
        DATE_TIME_WITH_DASH("yyyy-MM-dd HH:mm:ss"),
        DATE_WITH_DASH("yyyy-MM-dd"),
        DATE_TIME_WITH_DOT("dd.MM.yyyy HH:mm:ss");

        private final String format;

        Format(String format) {
            this.format = format;
        }

        public String getFormat() {
            return format;
        }
    }
}
