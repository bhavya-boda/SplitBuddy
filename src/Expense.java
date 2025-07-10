import java.util.List;

public class Expense {
    private double amount;
    private User paidBy;
    private List<Split> splits;
    private String expenseType;

    public Expense(double amount, User paidBy, List<Split> splits, String expenseType) {
        this.amount = amount;
        this.paidBy = paidBy;
        this.splits = splits;
        this.expenseType = expenseType;
    }

    public double getAmount() {
        return amount;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public List<Split> getSplits() {
        return splits;
    }

    public String getExpenseType() {
        return expenseType;
    }
}
