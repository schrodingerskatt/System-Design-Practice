import java.util.*;

public class NFTGeneratorPart2{

    public static List<Map<String, String>> generateUniqueNFTs(Config config, int n) {

        long total = 1;
         Map<String, List<String>> traits = config.getTraits();
        for(Map<String, List<String>> value : traits.values()){
            total*=values.size();
        }
        int target = (int) Math.min(total, n);

        Random random = new Random();
        Set<Map<String, String>> uniqueNFTs = new HashSet<>();
        List<String> traitNames = new ArrayList<>(traits.keySet());

        while(uniqueNFTs.size() < target){

            Map<String, String> nft = new HashMap<>();
            for (String trait : traitNames) {
                List<String> values = traits.get(trait);
                String chosen = values.get(random.nextInt(values.size()));
                nft.put(trait, chosen);
            }
            uniqueNFTs.add(nft);
        }
    return new ArrayList<>(uniqueNFTs);
    }
}