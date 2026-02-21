import java.util.*;
    // -----------------------------
    // Part 2
    // -----------------------------
    public static List<Integer> getBestTransactionsWithAncestors(List<Transaction> transactions, int blockSize) {
        Map<Integer, Transaction> byId = new HashMap<>(transactions.size() * 2);
        for (Transaction t : transactions) {
            byId.put(t.id, t);
        }

        Set<Integer> selected = new HashSet<>();
        List<Integer> result = new ArrayList<>();
        int remaining = blockSize;

        long seq = 0L; // tie-breaker to keep comparator deterministic
        PriorityQueue<Candidate> pq = new PriorityQueue<>();

        // Initial package for every tx (itself + all ancestors not yet selected)
        for (Transaction t : transactions) {
            PackageInfo pkg = computeCurrentPackage(t.id, byId, selected);
            if (!pkg.ids.isEmpty()) {
                pq.offer(new Candidate(t.id, pkg.totalFee, pkg.totalSize, seq++));
            }
        }

        while (remaining > 0 && !pq.isEmpty()) {
            int checks = pq.size();
            boolean pickedInThisRound = false;
            boolean updatedAnyCandidate = false;
            List<Candidate> carry = new ArrayList<>();

            for (int i = 0; i < checks && !pq.isEmpty(); i++) {
                Candidate top = pq.poll();

                // Recompute because package changes when ancestors get selected
                PackageInfo now = computeCurrentPackage(top.txId, byId, selected);

                // Already selected indirectly by another package, or invalid chain
                if (now.ids.isEmpty()) {
                    continue;
                }

                // Stale score/size -> reinsert refreshed candidate
                if (top.pkgFee != now.totalFee || top.pkgSize != now.totalSize) {
                    pq.offer(new Candidate(top.txId, now.totalFee, now.totalSize, seq++));
                    updatedAnyCandidate = true;
                    continue;
                }

                // Fresh and feasible -> pick full package in ancestor-first order
                if (now.totalSize <= remaining) {
                    for (int id : now.ids) {
                        if (selected.contains(id)) continue; // defensive
                        Transaction tx = byId.get(id);
                        if (tx == null || tx.size > remaining) break; // defensive
                        selected.add(id);
                        result.add(id);
                        remaining -= tx.size;
                    }
                    pickedInThisRound = true;
                    break;
                }

                // Fresh but currently not fitting: keep for future rounds
                // (its package may shrink if ancestors selected through another path)
                carry.add(top);
            }

            // Return deferred candidates
            for (Candidate c : carry) {
                pq.offer(c);
            }

            // No selection made:
            // - if no candidate changed either, we are stuck -> stop.
            // - if candidates were refreshed, run next round.
            if (!pickedInThisRound && !updatedAnyCandidate) {
                break;
            }
        }

        return result;
    }

    // Current required package for txId:
    // tx itself + all not-yet-selected ancestors until selected/root.
    // Returns ancestor-first ordering.
    private static PackageInfo computeCurrentPackage(
            int txId,
            Map<Integer, Transaction> byId,
            Set<Integer> selected
    ) {
        List<Integer> rev = new ArrayList<>();
        long totalFee = 0L;
        int totalSize = 0;

        Integer cur = txId;
        // Defensive cycle detection (problem states no cycles)
        Set<Integer> seenInWalk = new HashSet<>();

        while (cur != null && !selected.contains(cur)) {
            if (!seenInWalk.add(cur)) {
                return PackageInfo.empty(); // cycle detected => skip invalid chain
            }

            Transaction tx = byId.get(cur);
            if (tx == null) {
                return PackageInfo.empty(); // missing parent/tx => skip invalid chain
            }

            rev.add(cur);
            totalFee += tx.fee;
            totalSize += tx.size;
            cur = tx.parentId;
        }

        Collections.reverse(rev); // ancestor -> ... -> child
        return new PackageInfo(rev, totalFee, totalSize);
    }

    private static final class PackageInfo {
        final List<Integer> ids;
        final long totalFee;
        final int totalSize;

        PackageInfo(List<Integer> ids, long totalFee, int totalSize) {
            this.ids = ids;
            this.totalFee = totalFee;
            this.totalSize = totalSize;
        }

        static PackageInfo empty() {
            return new PackageInfo(Collections.emptyList(), 0L, 0);
        }
    }

    private static final class Candidate implements Comparable<Candidate> {
        final int txId;
        final long pkgFee;
        final int pkgSize;
        final long seq;

        Candidate(int txId, long pkgFee, int pkgSize, long seq) {
            this.txId = txId;
            this.pkgFee = pkgFee;
            this.pkgSize = pkgSize;
            this.seq = seq;
        }

        @Override
        public int compareTo(Candidate o) {
            // Max-heap by pkgFee/pkgSize using cross multiplication:
            // this before o if this.pkgFee/this.pkgSize is larger.
            long left = this.pkgFee * (long) o.pkgSize;
            long right = o.pkgFee * (long) this.pkgSize;
            if (left != right) return Long.compare(right, left); // descending ratio

            // Tie-break: higher absolute package fee first
            if (this.pkgFee != o.pkgFee) return Long.compare(o.pkgFee, this.pkgFee);

            // Then smaller package size
            if (this.pkgSize != o.pkgSize) return Integer.compare(this.pkgSize, o.pkgSize);

            // Stable deterministic tie-break
            return Long.compare(this.seq, o.seq);
        }
    }

    // quick demo
    public static void main(String[] args) {
        List<Transaction> txs1 = Arrays.asList(
            new Transaction(1, 3, 6, null),
            new Transaction(2, 4, 8, null),
            new Transaction(3, 3, 3, null),
            new Transaction(4, 5, 15, null)
        );
        System.out.println(getBestTransactions(txs1, 10)); // [4, 1] with this tie-break

        List<Transaction> txs2 = Arrays.asList(
            new Transaction(1, 5, 5, null),
            new Transaction(2, 3, 10, 1),
            new Transaction(3, 3, 10, 2),
            new Transaction(4, 4, 20, null)
        );
        System.out.println(getBestTransactionsWithAncestors(txs2, 10)); // typically [4, 1]
    }
}