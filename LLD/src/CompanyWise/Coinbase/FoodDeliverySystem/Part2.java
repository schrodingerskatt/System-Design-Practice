import java.util.*;

class Order{

    int id;
    int amount;
    int timeStamp;

    Order(int id, int amount, int timeStamp){
        this.id = id;
        this.amount = amount;
        this.timeStamp = timeStamp;
    }
}

class OrderAnalytics {

    private List<Order> orders;
    private long[] prefixSum;

    public OrderAnalytics(List<Order> orders){
        this.orders = new ArrayList<>(orders);
        this.orders.sort(Comparator.comparingInt(o -> o.timeStamp));
        prefixSum = new long[this.orders.size()];
        buildPrefix();
    }

    private void buildPrefix(){

        long runningSum = 0;
        for(int i = 0; i < orders.size(); i++){
            runningSum+= orders.get(i).amount;
            prefixSum[i] = runningSum;
        }
    }

    public void queryStats(int start, int end){

        int left = lowerBound(start);
        int right = upperBound(end);

        if(left > right){
            return;
        }

        int count = right-left+1;
        long total = prefixSum[right] - (left > 0 ? prefixSum[left - 1] : 0);
        double avg = (double)total/count;

        System.out.println("Count: " + count);
        System.out.println("Total Amount: " + total);
        System.out.println("Average: " + avg);
    }

    private int lowerBound(int target) {

        int l = 0, r = orders.size() - 1;
        while(l <= r){
            int mid = l+(r-l)/2;
            if(orders.get(mid).timeStamp < target){
                l = mid + 1;
            }else{
                r = mid - 1;
            }
        }
    return l;
    }

    private int upperBound(int target) {
        
        int l = 0, r = orders.size() - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (orders.get(mid).timestamp <= target)
                l = mid + 1;
            else
                r = mid - 1;
        }
        return r;
    }



}