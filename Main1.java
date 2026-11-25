// Лабораторна робота №2
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Scanner;

// ЗАВДАННЯ 1 — ВАРІАНТ 5 створення дати та операції з датами
class MyDate {
    private int year, month, day;
    private static int count = 0;

    public MyDate(int year, int month, int day) {
        LocalDate.of(year, month, day); 
        this.year = year;
        this.month = month;
        this.day = day;
        count++;
    }

    // Формат рядка: рік.місяць.день, наприклад 2026.11.25
    public MyDate(String value) {
        this(parsePart(value, 0), parsePart(value, 1), parsePart(value, 2));
    }

    private static int parsePart(String value, int index) {
        String[] parts = value.trim().split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Введіть дату у форматі рік.місяць.день");
        }
        return Integer.parseInt(parts[index]);
    }

    public MyDate(LocalDate date) {
        this(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }

    private LocalDate value() {
        return LocalDate.of(year, month, day);
    }

    public MyDate plusDays(long days) { return new MyDate(value().plusDays(days)); }
    public MyDate minusDays(long days) { return new MyDate(value().minusDays(days)); }
    public boolean isLeapYear() { return value().isLeapYear(); }
    public int getYear() { return year; }
    public int getMonth() { return month; }
    public int getDay() { return day; }

    public void setYear(int year) {
        LocalDate.of(year, month, day);
        this.year = year;
    }

    public void setMonth(int month) {
        LocalDate.of(year, month, day);
        this.month = month;
    }

    public void setDay(int day) {
        LocalDate.of(year, month, day);
        this.day = day;
    }

    public long daysBetween(MyDate other) {
        return ChronoUnit.DAYS.between(value(), other.value());
    }

    public boolean isBefore(MyDate other) { return value().isBefore(other.value()); }
    public boolean isAfter(MyDate other) { return value().isAfter(other.value()); }
    public static int getCount() { return count; }

    @Override
    public String toString() {
        return String.format("%04d.%02d.%02d", year, month, day);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MyDate)) return false;
        MyDate other = (MyDate) object;
        return year == other.year && month == other.month && day == other.day;
    }

    @Override
    public int hashCode() { return value().hashCode(); }
}
// ЗАВДАННЯ 2 — ВАРІАНТ 9 обчислення площі та об'єму трикутної призми
class Prism {
    private double height;
    private double side;
    private static int count = 0;

    public Prism(double height, double side) {
        if (!Double.isFinite(height) || !Double.isFinite(side)
                || height <= 0 || side <= 0) {
            throw new IllegalArgumentException("Висота і сторона мають бути додатними числами.");
        }
        this.height = height;
        this.side = side;
        count++;
    }

    public double lateralArea() { return 3 * side * height; }
    public double volume() { return Math.sqrt(3) / 4 * side * side * height; }
    public static int getCount() { return count; }

    @Override
    public String toString() {
        return String.format(Locale.US,
                "Призма: висота %.2f, сторона основи %.2f", height, side);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Prism)) return false;
        Prism other = (Prism) object;
        return Double.compare(height, other.height) == 0
                && Double.compare(side, other.side) == 0;
    }

    @Override
    public int hashCode() { return java.util.Objects.hash(height, side); }
}
// ЗАВДАННЯ 3 — ВАРІАНТ 3 визначення дати за номером дня року
class CalendarTask {
    private static int count = 0;

    public static LocalDate findDate(int dayOfYear, int year) {
        int max = LocalDate.of(year, 12, 31).getDayOfYear();
        if (dayOfYear < 1 || dayOfYear > max) {
            throw new IllegalArgumentException(
                    "Для цього року номер дня має бути від 1 до " + max);
        }
        count++;
        return LocalDate.ofYearDay(year, dayOfYear);
    }

    public static int getCount() { return count; }
}

public class Main1 {
    private static final Scanner in = new Scanner(
            new InputStreamReader(System.in, StandardCharsets.UTF_8))
            .useLocale(new Locale("uk", "UA"));

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (java.io.UnsupportedEncodingException ex) {
            throw new IllegalStateException("Кодування UTF-8 не підтримується.", ex);
        }

        while (true) {
            System.out.println("\nЛАБОРАТОРНА РОБОТА №2");
            System.out.println("1 — Робота з датами (завдання 1, варіант 5)");
            System.out.println("2 — Обчислення параметрів призми (завдання 2, варіант 9)");
            System.out.println("3 — Календар (завдання 3, варіант 3)");
            System.out.println("0 — Вихід");
            int choice = readInt("Ваш вибір: ");

            try {
                switch (choice) {
                    case 1: dateMenu(); break;
                    case 2: prismMenu(); break;
                    case 3: calendarMenu(); break;
                    case 0:
                        System.out.println("Завершення програми.");
                        return;
                    default:
                        System.out.println("Невідомий пункт меню.");
                }
            } catch (IllegalArgumentException | java.time.DateTimeException ex) {
                System.out.println("Помилка: " + ex.getMessage());
            }
        }
    }

    private static void dateMenu() {
        MyDate date = new MyDate(readInt("Рік: "),
                readInt("Місяць: "), readInt("День: "));
        System.out.println("Створена дата: " + date);
        System.out.println("Високосний рік: " + (date.isLeapYear() ? "так" : "ні"));
        System.out.println("Дата через N днів: " +
                date.plusDays(readInt("Введіть N для додавання: ")));
        System.out.println("Дата за N днів до заданої: " +
                date.minusDays(readInt("Введіть N для віднімання: ")));

        MyDate second = new MyDate(readInt("Рік другої дати: "),
                readInt("Місяць другої дати: "), readInt("День другої дати: "));
        System.out.println("Днів між датами (зі знаком): " + date.daysBetween(second));
        System.out.println("Перша дата раніше другої: " + date.isBefore(second));
        System.out.println("Перша дата пізніше другої: " + date.isAfter(second));
        System.out.println("Дати рівні: " + date.equals(second));
        System.out.println("Кількість створених об'єктів MyDate: " + MyDate.getCount());
    }

    private static void prismMenu() {
        Prism prism = new Prism(readDouble("Висота призми: "),
                readDouble("Сторона трикутної основи: "));
        System.out.println(prism);
        System.out.printf(Locale.US, "Бічна площа: %.2f%n", prism.lateralArea());
        System.out.printf(Locale.US, "Об'єм: %.2f%n", prism.volume());

        Prism same = new Prism(readDouble("Висота другої призми: "),
                readDouble("Сторона основи другої призми: "));
        System.out.println("Призми рівні: " + prism.equals(same));
        System.out.println("Кількість створених об'єктів Prism: " + Prism.getCount());
    }

    private static void calendarMenu() {
        int day = readInt("Номер дня року: ");
        int year = readInt("Рік: ");
        LocalDate date = CalendarTask.findDate(day, year);
        String month = date.getMonth().getDisplayName(TextStyle.FULL, new Locale("uk"));
        String weekday = date.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("uk"));
        System.out.println("Дата: " + date.getDayOfMonth() + " " + month
                + " " + year + ", " + weekday);
        System.out.println("Кількість виконаних пошуків: " + CalendarTask.getCount());
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!in.hasNextInt()) {
            System.out.print("Введіть ціле число: ");
            in.next();
        }
        return in.nextInt();
    }

    private static double readDouble(String prompt) {
        System.out.print(prompt);
        while (!in.hasNextDouble()) {
            System.out.print("Введіть число з комою як десятковим роздільником (наприклад, 12,5): ");
            in.next();
        }
        return in.nextDouble();
    }
}
