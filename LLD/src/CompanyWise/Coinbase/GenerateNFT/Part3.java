import java.util.List;
import java.util.Map;

class WeightedValue {
    String value;
    int weight;

    public WeightedValue(String value, int weight) {
        this.value = value;
        this.weight = weight;
    }
}

class Config {
    String name;
    String size;
    Map<String, List<WeightedValue>> traits;

    public Config(String name, String size, Map<String, List<WeightedValue>> traits) {
        this.name = name;
        this.size = size;
        this.traits = traits;
    }

    public Map<String, List<WeightedValue>> getTraits() {
        return traits;
    }
}

public class NFTGeneratorPart3 {

    public static List<Map<String, String>> generateUniqueWeightedNFTs(Config config, int n){

        Map<String, List<WeightedValue>> traits = config.getTraits();

        long totalCombinations = 1;
        for(List<WeightedValue> values : traits.values()){
            totalCombinations*=values.size();
        }

        int target = (int) Math.min(n, totalCombinations);

        Random random = new Random();
        Set<Map<String, String>> uniqueNFTs = new HashSet<>();

        // pre compute CDF's for each trait
        Map<String, int[]> cdfMap = new HashMap<>();
        Map<String, String[]> valueMap = new HashMap<>();

        for(Map.Entry<String, List<WeightedValue>> entry : traits.entrySet()){

            String trait = entry.getKey();
            List<WeightedValue> values = entry.getValue();

            int[] cdf = new int[values.size()];
            String[] valueArr = new String[values.size()];

            int runningSum = 0;

            for(int i = 0; i < values.size(); i++){
                runningSum+= values.get(i).weight;
                cdf[i] = runningSum;
                valueArr[i] = values.get(i).value;
            }
            cdfMap.put(trait, cdf);
            valueMap.put(trait, valueArr);
        }

        List<String> traitNames = new ArrayList<>(traits.keySet());
        while(uniqueNFTs.size() < target){

            Map<String, String> nft = new HashMap<>();

            for(String trait : traitNames){
                String chosen = sample(cdfMap.get(trait), valueMap.get(trait), random);
                nft.put(trait, chosen);
            }
        uniqueNFTs.add(NFT);
        }
    return new ArrayList<>(uniqueNFTs);
    }


    private static String sample(int[] cdf, String[] values, Random random){

        int totalWeight = cdf[cdf.length - 1];
        int r = random.nextInt(totalWeight) + 1;

        int low = 0, high = cdf.length-1;
        while(low < high){
            int mid = low+(high-low)/2;
            if(cdf[mid] >= r) high = mid;
            else low = mid + 1;
        }
        return values[low];
    }
}