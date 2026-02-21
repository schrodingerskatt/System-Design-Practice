/* Part 2 — Top N Pay Statistics  */

import java.util.*;

public class BankingSystemPart2{

    private final Map<String, Integer>balance = new HashMap<>();
    private final Map<String, Integer>paidOut = new HashMap<>();

    public boolean create_account(int timeStamp, String customerId){

        if(balance.containsKey(customerId)) return false;
        balance.put(customerId, 0);
        paidOut.put(customerId, 0);
        return true;
    }

    public Integer deposit(int timeStamp, String customerId, Integer amount){

        if(!balance.containsKey(customerId)) return null;
        balance.put(customerId, balance.get(customerId)+amount);
        return balance.get(customerId);
    }

    public Integer pay(int timeStamp, String sourceId, String targetId, Integer amount){

        if(!balance.containsKey(sourceId) || !balance.containsKey(targetId)) return null;

        Integer srcBal = balance.get(sourceId);
        if(srcBal < amount) return null;

        balance.put(sourceId, srcBal-amount);
        balance.put(targetId, balance.get(targetId)+amount);

        paidOut.put(sourceId, paidOut.get(sourceId)+amount);
        return balance.get(sourceId);
    }


    public List<String> get_top_n_pay(int timeStamp, int n){

        List<String> ids = new ArrayList<>(paidOut.keySet());
        ids.sort((a, b) -> {
            Integer pa = paidOut.get(a);
            Integer pb = paidOut.get(b);
            if(pa != pb) return Integer.compare(pb, pa);
            return a.compareTo(b);
        });

        if(n > ids.size()) n = ids.size();
        return ids.subList(0, n);
    }
}