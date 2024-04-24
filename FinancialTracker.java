import java.io.FileWriter;
import java.io.IOException;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class FinancialTracker {
    private Map<Month, MonthlyFinancialRecord> records;
    private Club club; // Club class is available

    public FinancialTracker(Club club) {
        this.club = club;
        records = new HashMap<>();
        logFinancialData();
    }

    private void logFinancialData() {
        Month[] months = club.getMonths();

        for (Month month : months) {
            double revenue = club.getRevenue(month);
            double expenses = club.getExpenses(month);
            double profit = club.getProfit(month);
            logMonth(month, revenue, expenses, profit);
        }
    }

    public void logMonth(Month month, double totalRevenue, double totalExpenses, double profit) {
        MonthlyFinancialRecord record = new MonthlyFinancialRecord(month, totalRevenue, totalExpenses, profit);
        records.put(month, record);
        // Optionally, save to a text file
        saveToFile(record);
    }

    private void saveToFile(MonthlyFinancialRecord record) {
        try (FileWriter writer = new FileWriter("financial_records.txt", true)) {
            writer.write(record.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add methods to retrieve profit information as needed
}

class MonthlyFinancialRecord {
    private Month month;
    private double totalRevenue;
    private double totalExpenses;
    private double profit;

    public MonthlyFinancialRecord(Month month, double totalRevenue, double totalExpenses, double profit) {
        this.month = month;
        this.totalRevenue = totalRevenue;
        this.totalExpenses = totalExpenses;
        this.profit = profit;
    }

    public double getProfit() {
        return profit;
    }

    @Override
    public String toString() {
        return month + ": Revenue - $" + totalRevenue + ", Expenses - $" + totalExpenses + ", Profit - $" + profit;
    }
}