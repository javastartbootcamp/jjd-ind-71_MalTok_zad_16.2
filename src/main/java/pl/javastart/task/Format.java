package pl.javastart.task;

class Format {
    private final String format;
    private final boolean onlyDate;

    public Format(String format, boolean onlyDate) {
        this.format = format;
        this.onlyDate = onlyDate;
    }

    public String getFormat() {
        return format;
    }

    public boolean isOnlyDate() {
        return onlyDate;
    }

    @Override
    public String toString() {
        return format;
    }
}
