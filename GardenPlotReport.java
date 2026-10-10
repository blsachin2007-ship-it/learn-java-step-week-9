import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Garden Plot Area Report Solution
 * Demonstrates Open-Closed Principle (OCP) using Polymorphism and Factory Design Pattern.
 */
abstract class Plot {
    private final String owner;
    private final String shapeName;

    public Plot(String owner, String shapeName) {
        this.owner = owner;
        this.shapeName = shapeName;
    }

    public String getOwner() {
        return owner;
    }

    public String getShapeName() {
        return shapeName;
    }

    /**
     * Abstract method for calculating area based on plot shape.
     */
    public abstract double calculateArea();

    /**
     * Formats output string for the plot report.
     */
    public String getReportLine() {
        return String.format(Locale.US, "%s (%s): %.2f", owner, shapeName, calculateArea());
    }
}

class CirclePlot extends Plot {
    private final double radius;

    public CirclePlot(String owner, double radius) {
        super(owner, "CIRCLE");
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class RectanglePlot extends Plot {
    private final double length;
    private final double width;

    public RectanglePlot(String owner, double length, double width) {
        super(owner, "RECTANGLE");
        this.length = length;
        this.width = width;
    }

    @Override
    public double calculateArea() {
        return length * width;
    }
}

class TrianglePlot extends Plot {
    private final double base;
    private final double height;

    public TrianglePlot(String owner, double base, double height) {
        super(owner, "TRIANGLE");
        this.base = base;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }
}

class PlotFactory {
    public static Plot createPlot(String inputLine) {
        if (inputLine == null || inputLine.trim().isEmpty()) {
            throw new IllegalArgumentException("Input line cannot be empty.");
        }

        String[] parts = inputLine.trim().split("\\s+");
        String shapeType = parts[0].toUpperCase();
        String owner = parts[1];

        switch (shapeType) {
            case "CIRCLE":
                double radius = Double.parseDouble(parts[2]);
                return new CirclePlot(owner, radius);
            case "RECTANGLE":
                double length = Double.parseDouble(parts[2]);
                double width = Double.parseDouble(parts[3]);
                return new RectanglePlot(owner, length, width);
            case "TRIANGLE":
                double base = Double.parseDouble(parts[2]);
                double height = Double.parseDouble(parts[3]);
                return new TrianglePlot(owner, base, height);
            default:
                throw new IllegalArgumentException("Unsupported shape type: " + shapeType);
        }
    }
}

class GardenReport {
    public static void printReport(List<Plot> plots) {
        double totalArea = 0.0;
        for (Plot plot : plots) {
            double area = plot.calculateArea();
            totalArea += area;
            System.out.println(plot.getReportLine());
        }
        System.out.printf(Locale.US, "Total Area: %.2f%n", totalArea);
    }
}

public class GardenPlotReport {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) {
            return;
        }

        int n = scanner.nextInt();
        scanner.nextLine(); // consume newline

        List<Plot> plots = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty()) {
                    plots.add(PlotFactory.createPlot(line));
                }
            }
        }

        GardenReport.printReport(plots);
    }
}
