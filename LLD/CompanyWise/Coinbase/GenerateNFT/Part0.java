import java.util.List;
import java.util.Map;

public class Config {

    private String name;
    private String size;
    private Map<String, List<String>> traits;

    public Config(String name, String size, Map<String, List<String>> traits) {
        this.name = name;
        this.size = size;
        this.traits = traits;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public Map<String, List<String>> getTraits() {
        return traits;
    }
}