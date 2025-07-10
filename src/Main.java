import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ExpenseManager expenseManager = new ExpenseManager();

        while (true) {
            System.out.println("\n=== Splitwise ===");
            System.out.println("1. Add expense");
            System.out.println("2. Show my balance");
            System.out.println("3. Settle expense with person");
            System.out.println("4. Show all balances");
            System.out.println("5. Clear all data");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            int option = sc.nextInt();
            sc.nextLine(); 

            switch (option) {
                case 1:
                    System.out.print("Enter payer userId: ");
                    String paidBy = sc.nextLine().trim();
                    if (!expenseManager.getUsers().containsKey(paidBy)) {
                        expenseManager.addOrUpdateUser(paidBy, paidBy);
                    }

                    System.out.print("Enter total amount: ");
                    double amount = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Enter number of users to split among: ");
                    int count = sc.nextInt();
                    sc.nextLine();

                    List<String> userIds = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        System.out.print("Enter userId #" + (i + 1) + ": ");
                        String userId = sc.nextLine().trim();
                        if (!expenseManager.getUsers().containsKey(userId)) {
                            expenseManager.addOrUpdateUser(userId, userId);
                        }
                        userIds.add(userId);
                    }

                    List<Split> splits = new ArrayList<>();

                    if (count == 1) {
                        splits.add(new ExactSplit(expenseManager.getUsers().get(userIds.get(0)), amount));
                        expenseManager.addExpense("EXACT", amount, paidBy, splits);
                    } else {
                        System.out.println("Choose split type (enter number):");
                        System.out.println("1) Equal");
                        System.out.println("2) Exact");
                        System.out.println("3) Percentage");
                        int splitChoice = sc.nextInt();
                        sc.nextLine();

                        switch (splitChoice) {
                            case 1:
                                for (String uid : userIds) {
                                    splits.add(new EqualSplit(expenseManager.getUsers().get(uid)));
                                }
                                expenseManager.addExpense("EQUAL", amount, paidBy, splits);
                                break;
                            case 2:
                                double totalExact = 0;
                                for (String uid : userIds) {
                                    System.out.print("Enter exact amount for " + uid + ": ");
                                    double exactAmt = sc.nextDouble();
                                    totalExact += exactAmt;
                                    splits.add(new ExactSplit(expenseManager.getUsers().get(uid), exactAmt));
                                }
                                sc.nextLine();
                                if (Math.abs(totalExact - amount) > 0.01) {
                                    System.out.println("Error: sums (" + totalExact + ") != total (" + amount + ").");
                                } else {
                                    expenseManager.addExpense("EXACT", amount, paidBy, splits);
                                }
                                break;
                            case 3:
                                double totalPct = 0;
                                for (String uid : userIds) {
                                    System.out.print("Enter percentage for " + uid + ": ");
                                    double pct = sc.nextDouble();
                                    totalPct += pct;
                                    splits.add(new PercentageSplit(expenseManager.getUsers().get(uid), pct));
                                }
                                sc.nextLine();
                                if (Math.abs(totalPct - 100.0) > 0.01) {
                                    System.out.println("Error: total percentage (" + totalPct + "%) must be 100%.");
                                } else {
                                    expenseManager.addExpense("PERCENTAGE", amount, paidBy, splits);
                                }
                                break;
                            default:
                                System.out.println("Invalid choice.");
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter your userId: ");
                    String userId = sc.nextLine().trim();
                    if (!expenseManager.getUsers().containsKey(userId)) {
                        expenseManager.addOrUpdateUser(userId, userId);
                    }
                    expenseManager.showUserBalance(userId);
                    break;

                case 3:
                    System.out.print("Enter your userId: ");
                    String fromUserId = sc.nextLine().trim();
                    if (!expenseManager.getUsers().containsKey(fromUserId)) {
                        System.out.println("User not found: " + fromUserId);
                        break;
                    }
                    System.out.print("Enter other userId to settle with: ");
                    String toUserId = sc.nextLine().trim();
                    if (!expenseManager.getUsers().containsKey(toUserId)) {
                        System.out.println("User not found: " + toUserId);
                        break;
                    }
                    double owed = expenseManager.getBalance(fromUserId, toUserId);
                    if (owed >= 0) {
                        System.out.println("No outstanding balance from " 
                            + fromUserId + " to " + toUserId + ".");
                    } else {
                        expenseManager.settleExpense(fromUserId, toUserId, -owed);
                    }
                    break;

                case 4:
                    expenseManager.showBalances();
                    break;

                case 5:
                    expenseManager.clearAllData();
                    break;

                case 0:
                    System.out.println("Exiting. Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
