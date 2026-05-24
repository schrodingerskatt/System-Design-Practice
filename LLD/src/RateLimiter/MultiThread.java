import java.util.*;

public class WebPageVisitCounter{

    private final LongAdder[] counters;
    private final int totalPages;

    public WebPageVisitCounter(int totalPages){
        this.totalPages = totalPages;
        this.counters = new LongAdder[totalPages];

        for(int i = 0; i < totalPages; i++){
            counters[i] = new LongAdder();
        }
    }

    private void incrementVisitCount(int pageIndex){
        validate(pageIndex);
        counters[pageIndex].increment();
    }

    private void validate(int pageIndex){
        if(pageIndex < 0 || pageIndex >= totalPages){
            throw new IllegalArgumentException("Invalid page index: " + pageIndex);
        }
    }

    public static void main(String[] args){

        int totalPages = 5;
        WebPageVisitCounter counter = new WebPageVisitCounter(totalPages);
        int numberOfThreads = 100;
        Thread[] threads = new Thread[numberOfThreads];

        // Simulate concurrent users
        for(int i = 0; i < numberOfThreads; i++){
            threads[i] = new Thread(() ->{
                for(int j = 0; j < 1000; j++){
                    int page = (int)(Math.random()*totalPages);
                    counter.incrementVisitCount(page);
                }
            });
            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Final Visit Counts:");
        for (int i = 0; i < totalPages; i++) {
            System.out.println("Page " + i + " → " + counter.getVisitCount(i));
        }
    }
}