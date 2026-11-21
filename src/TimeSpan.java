import exceptions.IllegalInputTimeSpanException;
import exceptions.IllegalScaleFactorException;
import exceptions.NegativeTimeSpanException;

public class TimeSpan {
    private int totalMinutes;

    public TimeSpan() {
        totalMinutes = 0;
    }

    public TimeSpan(int minutes) {
        if (minutes < 0) {
            throw new IllegalInputTimeSpanException(
                    "Minutes cannot be negative."
            );
        }
        totalMinutes = minutes;
    }

    public TimeSpan(int hours, int minutes) {
        if (hours < 0 || minutes < 0 || minutes > 59) {
            throw new IllegalInputTimeSpanException(
                    "Invalid hours or minutes."
            );
        }
        totalMinutes = hours * 60 + minutes;
    }

    public TimeSpan(TimeSpan span) {
        if (span == null) {
            throw new IllegalInputTimeSpanException(
                    "TimeSpan cannot be null."
            );
        }
        totalMinutes = span.getTotalMinutes();
    }

    public int getHours() {
        return totalMinutes / 60;
    }

    public int getMinutes() {
        return totalMinutes % 60;
    }

    public void add(int hours, int minutes) {
        if (hours < 0 || minutes < 0 || minutes > 59) {
            throw new IllegalInputTimeSpanException(
                    "Invalid hours or minutes."
            );
        }
        totalMinutes += hours * 60 + minutes;
    }

    public void add(int minutes) {
        if (minutes < 0) {
            throw new IllegalInputTimeSpanException(
                    "Minutes cannot be negative."
            );
        }
        totalMinutes += minutes;
    }

    public void add(TimeSpan span) {
        totalMinutes += span.getTotalMinutes();
    }

    public void subtract(int hours, int minutes) {
        if (hours < 0 || minutes < 0 || minutes > 59) {
            throw new IllegalInputTimeSpanException(
                    "Invalid hours or minutes."
            );
        }

        subtract(hours * 60 + minutes);
    }

    public void subtract(int minutes) {
        if (minutes < 0) {
            throw new IllegalInputTimeSpanException(
                    "Minutes cannot be negative."
            );
        }

        if (minutes > totalMinutes) {
            throw new NegativeTimeSpanException(
                    "TimeSpan cannot become negative."
            );
        }

        totalMinutes -= minutes;
    }

    public void subtract(TimeSpan span) {
        subtract(span.getTotalMinutes());
    }

    public double getTotalHours() {
        return totalMinutes / 60.0;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public void scale(int factor) {
        if (factor <= 0) {
            throw new IllegalScaleFactorException(
                    "Scale factor must be greater than zero."
            );
        }

        totalMinutes *= factor;
    }
}