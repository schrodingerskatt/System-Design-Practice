import java.util.*;

public class LeaderBoardScore{

    static class User{
        String userId;
        int score;

        public User(String userId, int score){
            this.userId = userId;
            this.score = score;
        }
    }

    private final Map<String, User>userMap = new HashMap<>();
    private final Map<String, Set<String>>playerToUser = new HashMap<>(); // {player -> list of users}
    private final Map<String, Integer>playerScore = new HashMap<>();

    private final TreeSet<User>leaderboard = new TreeSet<>((a, b) ->{
        if(a.score != b.score) return b.score-a.score;
        return a.userId.compareTo(b.userId);
    });

    private void addUser(String userId, List<String>playerIds){

        int totalScore = 0;
        for(String playerId : playerIds){
            int score = playerScore.getOrDefault(playerId, 0);
            totalScore+=score;
            playerToUser.computeIfAbsent(playerId, k -> new HashSet<>()).add(userId);
        }

        User user = new User(userId, totalScore);
        userMap.put(userId, user);
        leaderboard.add(User);
    }

    public void addScore(String playerId, int delta){

        playerScore.put(playerId, playerScore.get(playerId, 0)+delta);
        Set<String> users = playerToUser.get(playerId);

        if(users == null) return;
        for(String userId : users){
            User user = userMap.get(userId);
            leaderboard.remove(user);
            user.score+=delta;
            leaderboard.add(user);
        }
    }

    public List<String> getTopK(int k){

        List<String>result = new ArrayList<>(k);
        int count = 0;
        for(User user : leaderboard){
            if(count++ == k) break;
            result.add(user.userId);
        }
    return result;
    }

    public void printLeaderBoard(){
        System.out.println("LeaderBoard Score : ");
        for(User user : LeaderBoard){
            System.out.println(user.userId + "->" + user.score);
        }
        System.out.println();
    }

    public static void main(Sttring[] args){

        LeaderBoardScore lb = new LeaderBoardScore();
        lb.addUser("uA", Arrays.asList("p1", "p2"));
        lb.addUser("uB", Arrays.asList("p2"));

        System.out.println(lb.getTopK(2));

        lb.addScore("p2", 10);
        System.out.println(lb.getTopK(2));

        lb.addScore("p1", 3);
        System.out.println(lb.getTopK(1));

        lb.addScore("p2", -5);
        System.out.println(lb.getTopK(5));

        lb.printLeaderboard();
    }
}