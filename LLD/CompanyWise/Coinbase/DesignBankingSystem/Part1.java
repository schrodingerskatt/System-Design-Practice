import java.util.*;

public class BankingSystemPart1{

    private final Map<String, long> balance = new HashMap<>();

    public boolean create_account(int timeStamp, String customerId){
        if(balance.containsKey(customerId)){
            return false;
        }
        balance.put(customerId, 0);
        return true;
    }

    public int deposit(int timeStamp, String customerId, int amount){
        if(!balance.containsKey(customerId)) return false;
        balance.put(customerId, balance.get(customerId)+amount);
        return balance.get(customerId);
    }

    public Integer pay(int timeStamp, String sourceId, String targetId, long amount){

        if(!balance.containsKey(sourceId) || !balance.containsKey(targetId)){
            return null;
        }

        if(srcBal < amount) return null;

        balance.put(sourceId, balance.get(sourceId)-amount);
        balance.put(sourceId, balance.get(targetId)+amount);

        return balance.get(sourceId);
    }
}