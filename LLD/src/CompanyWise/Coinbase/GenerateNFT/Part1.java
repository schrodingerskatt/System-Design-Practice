import java.util.*;

public class NFTGenerator{

    public static List<Map<String, String>>generateRandomNFTs(Config config, int n) {

        Map<String, List<String>> traits = config.getTraits();
        Random random = new Random();
        List<Map<String, String>> result = new ArrayList<>();

        for(int i = 0; i < n; i++){

            Map<String, String>nft = new HashMap<>();
            for(Map.Entry<String, List<String>> entry : traits.entrySet()){
                String traitName = entry.getKey();
                List<String>values = entry.getValue();
                String chosen = values.get(random.nextInt(values.size()));
                nft.put(traitName, chosen);
            }
        result.add(nft);
        }
    }
}