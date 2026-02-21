import java.util.*;

public class BankingSystemPart3{

    public static class ScheduledTransfer{

        final String id;
        final String target;
        String source;
        final Integer amount;
        final Integer executeAfter;
        boolean active = true;

        ScheduledTransfer(String id, String target, String source, Integer amount, Integer executeAfter){
            this.id = id;
            this.target = target;
            this.source = source;
            this.amount = amount;
            this.executeAfter = executeAfter;
        }
    }

    private static class Account{

        final String id;
        Integer balance = 0;
        Integer held = 0;
        Integer paidOutTotal = 0;

        Map<String ScheduledTransfer>scheduled = new HashMap<>();
        Account(String id){
            this.id = id;
        }
    }

    // ---------------- Storage ----------------
    private final Map<String, Account> accounts = new HashMap<>();

    // transferId -> transfer (Only active ones kept)
    private final Map<String, ScheduledTransfer> transferById = new HashMap<>();

    // ensures unique ID even if same source+timestamp repeats
    private final Map<String, Integer> idCounter = new HashMap<>();

    // ---------------- Helpers ----------------
    private String generateTransferId(String source, int timestamp){
        String base = source + "_" + timestamp;
        int count = idCounter.getOrDefault(base, 0)+1;
        idCounter.put(base, count);
        return (count == 1) ? base : base + "_" + count;
    }

    // ---------------- Part 1 ----------------

    public boolean create_account(int timestamp, String customerId){
        if(accounts.containsKey(customerId)) return false;
        accounts.put(customerId, new Account(customerId));
        return true;
    }

    public Integer deposit(int timestamp, String customerId, Integer amount){

        Account acc = accounts.get(customerId);
        if(acc == null) return null;

        acc.balance+= amount;
        return acc.balance;
    }

    public Integer pay(int timestamp, String sourceId, String targetId, Integer amount){

        Amount src = accounts.get(sourceId);
        Account tgt = accounts.get(targetId);

        if(src == null || tgt == null) return null;

        if(src.balance < amount) return null;

        src.balance -= amount;
        tgt.balance += amount;

        // Part 2 stat
        src.paidOutTotal += amount;
        return scr.balance;
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

    // ---------------- Scheduled Payments ----------------

    public String schedule_payment(int timestamp, String sourceId, String targetId,
                                  Integer amount, int delay){

    Account src = accounts.get(sourceId);
    Account tgt = accounts.get(targetId);

    if(src == null || tgt == null) return null;

    // must have enough money

    if(src.balance < amount) return null;
    src.balance -= amount;
    src.held += amount;

    src.paidOutTotal += amount;
    String tansferId = generateTransferId(sourceId, timestamp);
    ScheduledTransfer tr = new ScheduledTransfer(
        transferId, sourceId, targetId, amount, timestamp, timestamp + delay);
    src.scheduled.put(transferId, tr);
    transferById.put(transferId, tr);
    return transferId;

    }

    public boolean accept_payment(long timestamp, String accountId, String transferId) {

        ScheduledTransfer tr = transfersById.get(transferId);
        if(tr == null || !tr.active) return false;

        // Only target can accept
        if(!tr.target.equals(accountId)) return false;

        // Must be after delay
        if(timestamp < tr.executeAfter) return false;

        Account src = accounts.get(tr.source);
        Account tgt = accounts.get(tr.target);

        if(src.held < tr.amount){
            return false;
        }

        src.held -= tr.amount;
        tgt.balance += tr.amount;

        tr.active = false;
        src.scheduled.remove(transferId);
        transferById.remove(transferId);

        return true;
    }

    public boolean cancel_payment(long timestamp, String accountId, String transferId) {

        ScheduledTransfer tr = transferById.get(transferId);
        if(tr == null || !tr.active) return false;

        // only source can cancel
        if(!tr.source.equals(accountId)) return false;

        Account src = accounts.get(tr.source);
        if(src == null) return false;

        if(src.held < tr.amount) return false;

        src.held -= tr.amount;
        src.balance += tr.amount;

        // cleanup
        tr.active = false;
        src.scheduled.remove(transferId);
        transfersById.remove(transferId);

        return true;

    }
    


}