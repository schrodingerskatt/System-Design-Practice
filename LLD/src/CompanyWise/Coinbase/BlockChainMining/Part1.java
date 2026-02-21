class Transaction{

    int id;
    int size;
    int fee;

    public Transaction(int id, int size, int fee){
        this.id = id;
        this.size = size;
        this.fee = fee;
    }

    public List<Integer> getBestTransactions(List<Transaction> txs, int blockSize){

        List<Transaction> sorted = new ArrayList<>(txs);

        /*
        Sort transactions by: 
        1. higher fee/size
        2. higher fee (tie-break)
        3. smaller size (tie-break)
        4. Iterate once and pack if it fits
        5. O(N log N) time, O(N) space
        */

        sorted.sort((a, b) -> {
            long left = (long)b.fee*a.size;
            long right = (long)a.fee*b.size;
            if(left != right) return Long.compare(left, right);
            if(a.fee != b.fee) return Integer.compare(b.fee, a.fee);
            if(a.size != b.size) return Integer.compare(a.size, b.size);
            return Integer.compare(a.id, b.id);
        });
        List<Integer>picked = new ArrayList<>();
        int used = 0;
        for(Transaction t : sorted){
            if(used+t.size <= blockSize){
                picked.add(t.id);
                used+= t.size;
            }
        }
    return picked;
    }
}