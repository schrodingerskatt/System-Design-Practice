import java.util.*;

public class BankingSystemPart4 {

    // ---------------- Models ----------------

    private static class ScheduledTransfer {
        final String id;
        String source;
        final String target;
        final long amount;
        final long createdAt;
        final long executeAfter;
        boolean active = true;

        ScheduledTransfer(String id, String source, String target,
                          long amount, long createdAt, long executeAfter) {
            this.id = id;
            this.source = source;
            this.target = target;
            this.amount = amount;
            this.createdAt = createdAt;
            this.executeAfter = executeAfter;
        }
    }

    private static class Account {
        final String id;
        long balance = 0;
        long held = 0;
        long paidOutTotal = 0;

        Map<String, ScheduledTransfer> scheduled = new HashMap<>();

        Account(String id) {
            this.id = id;
        }
    }

    // ---------------- Storage ----------------

    private final Map<String, Account> accounts = new HashMap<>();
    private final Map<String, ScheduledTransfer> transfersById = new HashMap<>();
    private final Map<String, Integer> idCounter = new HashMap<>();

    // ---------------- Helpers ----------------

    private String generateTransferId(String source, long timestamp) {
        String base = source + "_" + timestamp;
        int count = idCounter.getOrDefault(base, 0) + 1;
        idCounter.put(base, count);
        return (count == 1) ? base : base + "_" + count;
    }

    // ---------------- Part 1 ----------------

    public boolean create_account(long timestamp, String customerId) {
        if (accounts.containsKey(customerId)) return false;
        accounts.put(customerId, new Account(customerId));
        return true;
    }

    public Long deposit(long timestamp, String customerId, long amount) {
        Account acc = accounts.get(customerId);
        if (acc == null) return null;
        acc.balance += amount;
        return acc.balance;
    }

    public Long pay(long timestamp, String sourceId, String targetId, long amount) {
        Account src = accounts.get(sourceId);
        Account tgt = accounts.get(targetId);

        if (src == null || tgt == null) return null;
        if (src.balance < amount) return null;

        src.balance -= amount;
        tgt.balance += amount;

        src.paidOutTotal += amount;
        return src.balance;
    }

    // ---------------- Part 2 ----------------

    public List<String> get_top_n_pay(long timestamp, int n) {
        List<String> ids = new ArrayList<>(accounts.keySet());

        ids.sort((a, b) -> {
            long pa = accounts.get(a).paidOutTotal;
            long pb = accounts.get(b).paidOutTotal;

            if (pa != pb) return Long.compare(pb, pa);
            return a.compareTo(b);
        });

        if (n > ids.size()) n = ids.size();
        return ids.subList(0, n);
    }

    // ---------------- Part 3 ----------------

    public String schedule_payment(long timestamp,
                                   String sourceId,
                                   String targetId,
                                   long amount,
                                   long delay) {

        Account src = accounts.get(sourceId);
        Account tgt = accounts.get(targetId);

        if (src == null || tgt == null) return null;
        if (src.balance < amount) return null;

        src.balance -= amount;
        src.held += amount;

        // counts as paid out immediately
        src.paidOutTotal += amount;

        String transferId = generateTransferId(sourceId, timestamp);

        ScheduledTransfer tr = new ScheduledTransfer(
                transferId,
                sourceId,
                targetId,
                amount,
                timestamp,
                timestamp + delay
        );

        src.scheduled.put(transferId, tr);
        transfersById.put(transferId, tr);

        return transferId;
    }

    public boolean accept_payment(long timestamp, String accountId, String transferId) {
        ScheduledTransfer tr = transfersById.get(transferId);
        if (tr == null || !tr.active) return false;

        if (!tr.target.equals(accountId)) return false;
        if (timestamp < tr.executeAfter) return false;

        Account src = accounts.get(tr.source);
        Account tgt = accounts.get(tr.target);

        if (src == null || tgt == null) return false;
        if (src.held < tr.amount) return false;

        src.held -= tr.amount;
        tgt.balance += tr.amount;

        tr.active = false;
        src.scheduled.remove(transferId);
        transfersById.remove(transferId);

        return true;
    }

    public boolean cancel_payment(long timestamp, String accountId, String transferId) {
        ScheduledTransfer tr = transfersById.get(transferId);
        if (tr == null || !tr.active) return false;

        if (!tr.source.equals(accountId)) return false;

        Account src = accounts.get(tr.source);
        if (src == null) return false;
        if (src.held < tr.amount) return false;

        src.held -= tr.amount;
        src.balance += tr.amount;

        tr.active = false;
        src.scheduled.remove(transferId);
        transfersById.remove(transferId);

        return true;
    }

    // ============================================================
    // PART 4 — Merge Customers
    // ============================================================

    public boolean merge_customers(long timestamp, String oldCustomerId, String newCustomerId) {
        if (oldCustomerId.equals(newCustomerId)) return false;

        Account oldAcc = accounts.get(oldCustomerId);
        Account newAcc = accounts.get(newCustomerId);

        if (oldAcc == null || newAcc == null) return false;

        // merge balances
        newAcc.balance += oldAcc.balance;
        newAcc.held += oldAcc.held;

        // merge stats (needed because Part 2 exists)
        newAcc.paidOutTotal += oldAcc.paidOutTotal;

        // move scheduled transfers
        for (ScheduledTransfer tr : oldAcc.scheduled.values()) {
            tr.source = newCustomerId;           // important!
            newAcc.scheduled.put(tr.id, tr);
        }
        oldAcc.scheduled.clear();

        // remove old customer completely
        accounts.remove(oldCustomerId);

        return true;
    }

    // Optional helper for Example 4
    public Long get_balance(long timestamp, String customerId) {
        Account acc = accounts.get(customerId);
        if (acc == null) return null;
        return acc.balance;
    }
}
