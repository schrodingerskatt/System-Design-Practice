import java.util.*;

class ClickCounter{

    private Deque<Integer>queue;

    public ClickCounter(){
        queue = new ArrayDeque<>();
    }

    public void recordClick(int timestamp){
        queue.offerLast(timestamp);
    }

    public int getRecentClicks(int timestamp){
        while(!queue.isEmpty() && queue.peekFirst() <= timestamp-300){
            queue.pollFirst();
        }
    return queue.size();
    }


}

// Token Bucket Approach
class ClickCounter{

    private int[] times;
    private int[] hits;

    public ClickCounter(){
        times = new int[300];
        hits = new int[300];
    }

    public void recordClick(int timestamp){
        int idx = timestamp%300;
        if(times[idx] != timestamp){
            times[idx] = timestamp;
            hits[idx] = 1;
        }else{
            hits[idx]++;
        }
    }

    public int getRecentClicks(int timestamp){
        int total = 0;
        for(int i = 0; i < 300; i++){
            if(timestamp-times[i] < 300){
                total+=hits[i];
            }
        }
    return total;
    }
}