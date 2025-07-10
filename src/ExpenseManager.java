import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseManager {
    private Map<String, User> users;
    private List<Expense> expenses;
    private Map<String, Map<String, Double>> balanceSheet; 

    public ExpenseManager() {
        users = new HashMap<>();
        expenses = new ArrayList<>();
        balanceSheet = new HashMap<>();
    }

    public void clearAllData() {
        users.clear();
        expenses.clear();
        balanceSheet.clear();
        System.out.println("All data cleared.");
    }

    public void addOrUpdateUser(String userId, String name) {
        if (!users.containsKey(userId)) {
            User user = new User(userId, name);
            users.put(userId, user);
            balanceSheet.put(userId, new HashMap<>());
            for (String otherId : users.keySet()) {
                if (!otherId.equals(userId)) {
                    balanceSheet.get(userId).put(otherId, 0.0);
                    balanceSheet.get(otherId).put(userId, 0.0);
                }
            }
            System.out.println("Added new user: " + userId + " (" + name + ")");
        }
    }

    public void addExpense(String expenseType, double amount, String paidByUserId, List<Split> splits) {
        Expense expense = new Expense(amount, users.get(paidByUserId), splits, expenseType);
        expenses.add(expense);

        switch (expenseType) {
            case "EQUAL":
                int totalUsers = splits.size();
                double splitAmount = Math.round((amount / totalUsers) * 100.0) / 100.0;
                for (Split split : splits) {
                    split.setAmount(splitAmount);
                }
                break;
            case "EXACT":
                break;
            case "PERCENTAGE":
                for (Split split : splits) {
                    PercentageSplit ps = (PercentageSplit) split;
                    double pctAmt = Math.round((amount * ps.getPercentage() / 100.0) * 100.0) / 100.0;
                    ps.setAmount(pctAmt);
                }
                break;
            default:
                System.out.println("Invalid expense type: " + expenseType);
                return;
        }

        for (Split split : splits) {
            String payerId = paidByUserId;
            String payeeId = split.getUser().getUserId();
            if (payerId.equals(payeeId)) continue;
            double owed = split.getAmount();

            Map<String, Double> payerBalances = balanceSheet.get(payerId);
            payerBalances.put(payeeId,
                    payerBalances.getOrDefault(payeeId, 0.0) + owed);

            Map<String, Double> payeeBalances = balanceSheet.get(payeeId);
            payeeBalances.put(payerId,
                    payeeBalances.getOrDefault(payerId, 0.0) - owed);
        }
    }

    public double getBalance(String fromUserId, String toUserId) {
        Map<String, Double> map = balanceSheet.get(fromUserId);
        if (map == null) return 0.0;
        return map.getOrDefault(toUserId, 0.0);
    }

    public void settleExpense(String fromUserId, String toUserId, double amountToSettle) {
        // assume users exist
        Map<String, Double> fromBalances = balanceSheet.get(fromUserId);
        Map<String, Double> toBalances   = balanceSheet.get(toUserId);

        fromBalances.put(toUserId, 0.0);
        toBalances.put(fromUserId, 0.0);

        System.out.println("You paid Rs " + amountToSettle + " to " + toUserId + ".");
        System.out.println("You are all settled with " + toUserId + ".");
    }
    public void showBalances() {
        boolean empty = true;
        for (Map.Entry<String, Map<String, Double>> entry : balanceSheet.entrySet()) {
            String userId = entry.getKey();
            for (Map.Entry<String, Double> bal : entry.getValue().entrySet()) {
                double amt = bal.getValue();
                if (amt != 0) {
                    empty = false;
                    printBalance(userId, bal.getKey(), amt);
                }
            }
        }
        if (empty) {
            System.out.println("No balances.");
        }
    }
    public void showUserBalance(String userId) {
        Map<String, Double> balances = balanceSheet.get(userId);
        if (balances == null) {
            System.out.println("No data for user: " + userId);
            return;
        }
        boolean hasNonZero = false;
        for (Map.Entry<String, Double> entry : balances.entrySet()) {
            double amt = entry.getValue();
            String otherId = entry.getKey();
            if (amt < 0) {
                hasNonZero = true;
                System.out.printf("You owe Rs %.2f to %s%n", Math.abs(amt), otherId);
            } else if (amt > 0) {
                hasNonZero = true;
                System.out.printf("%s owes you Rs %.2f%n", otherId, amt);
            }
        }
        if (!hasNonZero) {
            System.out.println("You have no balances.");
        }
    }

    private void printBalance(String user1Id, String user2Id, double amount) {
        if (amount > 0) {
            System.out.println(
                users.get(user2Id).getName() +
                " owes Rs " + amount +
                " to " + users.get(user1Id).getName()
            );
        }
    }

    public Map<String, User> getUsers() {
        return users;
    }
}
