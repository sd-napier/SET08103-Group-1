package com.napier.sem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.*;
import java.io.IOException;

/**
 * Provides utilities for formatting and printing city report results.
 * <p>
 * This class is responsible for converting query results into readable
 * console tables or Markdown-formatted text. It supports both grouped
 * and ungrouped outputs depending on the report scope.
 * </p>
 *
 * @author Alan Glowacz
 */
public class CityPrinter {

    /** Formatter for population numbers (e.g., 1,234,567). */
    private static final DecimalFormat NUM = new DecimalFormat("#,###");

    /** Heading for reports without grouping (e.g., world-level reports). */
    public static final String HEADING_NO_GROUP =
            "| City | Country | District | Population |\r\n";

    /** Heading for reports grouped by continent, region, country, or district. */
    public static final String HEADING_WITH_GROUP =
            "| Group | City | Country | District | Population |\r\n";

    /**
     * Prints the provided city rows to the console in a formatted table.
     *
     * @param rows      list of {@link CityRow} results to print
     * @param showGroup whether to include the group column
     */
    @SuppressWarnings("unused") // retained for manual console runs and demos
    public static void printTable(List<CityRow> rows, boolean showGroup) {
        System.out.print(showGroup ? HEADING_WITH_GROUP : HEADING_NO_GROUP);
        for (CityRow r : rows) {
            String group = showGroup ? (r.group == null ? "" : r.group) + " | " : "";
            System.out.println("| " + group +
                    r.name + " | " + r.country + " | " +
                    (r.district == null ? "" : r.district) + " | " +
                    NUM.format(r.population) + " |");
        }
        System.out.println();
    }

    /**
     * Converts the provided city rows into Markdown-formatted table lines.
     *
     * @param rows      list of {@link CityRow} results
     * @param showGroup whether to include the group column
     * @return list of Markdown table lines
     */
    public static List<String> toMarkdownRows(List<CityRow> rows, boolean showGroup) {
        ArrayList<String> out = new ArrayList<>();
        out.add(showGroup ? HEADING_WITH_GROUP : HEADING_NO_GROUP);
        for (CityRow r : rows) {
            String group = showGroup ? (r.group == null ? "" : r.group) + " | " : "";
            out.add("| " + group +
                    r.name + " | " + r.country + " | " +
                    (r.district == null ? "" : r.district) + " | " +
                    NUM.format(r.population) + " |\r\n");
        }
        return out;
    }

    /**
     * Writes the provided Markdown lines to the specified output file.
     * <p>
     * Automatically creates parent directories if they do not exist.
     * </p>
     *
     * @param filename destination file path (e.g., "reports/city_world_top32.md")
     * @param lines    list of Markdown lines to write
     * @throws IOException if file creation or writing fails
     */
    @SuppressWarnings("unused") // available to callers; not invoked in current flow
    public static void write(String filename, List<String> lines) throws IOException {
        Path p = Path.of(filename);
        Path dir = p.getParent();
        if (dir != null) {
            Files.createDirectories(dir); // Ensure output directory exists
        }
        Files.write(p, lines);
    }
}
