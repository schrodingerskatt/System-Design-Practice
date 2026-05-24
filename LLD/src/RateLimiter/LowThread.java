import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class WebpageVisitCounter {

    private final ConcurrentHashMap<Integer, AtomicInteger> map;
    private final int totalPages;

    public WebpageVisitCounter(int totalPages) {
        this.totalPages = totalPages;
        this.map = new ConcurrentHashMap<>();

        // initialize all pages
        for (int i = 0; i < totalPages; i++) {
            map.put(i, new AtomicInteger(0));
        }
    }

    // increment visit count for pageIndex by 1
    public void incrementVisitCount(int pageIndex) {
        validate(pageIndex);
        map.get(pageIndex).incrementAndGet(); // atomic update
    }

    // return total visit count for a given page
    public int getVisitCount(int pageIndex) {
        validate(pageIndex);
        return map.get(pageIndex).get();
    }

    private void validate(int pageIndex) {
        if (pageIndex < 0 || pageIndex >= totalPages) {
            throw new IllegalArgumentException("Invalid page index: " + pageIndex);
        }
    }

    // ---------------- MAIN METHOD ----------------
    public static void main(String[] args) throws InterruptedException {

        int totalPages = 5;
        WebpageVisitCounter counter = new WebpageVisitCounter(totalPages);

        int threads = 100;
        Thread[] arr = new Thread[threads];

        for (int i = 0; i < threads; i++) {
            arr[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    int page = (int) (Math.random() * totalPages);
                    counter.incrementVisitCount(page);
                }
            });
            arr[i].start();
        }

        for (Thread t : arr) {
            t.join();
        }

        System.out.println("Final counts:");
        for (int i = 0; i < totalPages; i++) {
            System.out.println("Page " + i + " → " + counter.getVisitCount(i));
        }
    }
}