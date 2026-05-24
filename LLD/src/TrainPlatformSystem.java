import java.util.*;

class TrainPlatformManager{

    static class TrainSlot{
        int start, end;
        String trainId;

        TrainSlot(int s, int e, String roomIdd){
            this.start = s;
            this.end = e;
            this.trainId = id;
        }
    }

    static class Platform{

        int id;
        TreeSet<TrainSlot>schedule;

        Platform(int id){
            this.id = id;
            this.schedule = new TreeSet<>((a, b) ->{
                if(a.start != b.start) return a.start-b.start;
                return a.trainId.compareTo(b.trainId);
            });
        }
    }

    List<Platform>platforms;
    Map<String, TrainSlot>trainMap;

    public TrainPlatformManager(int platformCount){
        platforms = new ArrayList<>();
        for(int i = 0; i < platformCount; i++){
            platforms.add(new Platform(i));
        }
        trainMap = new HashMap<>();
    }

    public String assignPlatform(String trainId, int arrivalTime, int waitTime) {

        int bestPlatform = -1;
        int minDelay = Integer.MAX_VALUE;
        int finalStart = -1, finalEnd = -1;

        for(Platform p : platforms){
            int start = arrivalTime;
            int end = start+waitTime-1;

            while(True){
                TrainSlot dummy = new TrainSlot(start, start, "");
                TrainSlot floor = p.schedule.floor(dummy);
                TrainSlot ceil = p.schedule.ceil(dummy);

                boolean conflict = false;
                if(floor != null && floor.end >= start){
                    start = floor.end+1;
                    end = start+waitTime-1;
                    conflict = true;
                }else if(ceil != null && ceil.start <= end){
                    start = ceil.end+1;
                    end = start+waitTime-1;
                    conflict = true;
                }
                if(!conflict) break;
                int delay = start-arrivalTime;
                if(delay < minDelay || (delay == minDelay && p.id < bestPlatform)){
                    minDelay = delay;
                    bestPlatform = p.id;
                    finalStart = start;
                    finalEnd = end;
                }
            }
            Platform chosen = platforms.get(bestPlatform);
            TrainSlot slot = new TrainSlot(finalStart, finalEnd, trainId);
            chosen.schedule.add(slot);
            trainMap.put(trainId, slot);
            return bestPlatform + "," + minDelay;

        }

    }

    public String getTrainAtPlatform(int platformNumber, int timestamp){

        Platform p = platforms.get(platformNumber);
        TrainSlot dummy = new TrainSlot(timestamp, timestamp, "");
        TrainSlot floor = p.schedule.floor(dummy);

        if(floor != null && floor.start <= timestamp && floor.end >= timestamp){
            return floor.trainId;
        }
    return "";
    }

    public int getPlatformOfTrain(String trainId, int timestamp){

        TrainSlot slot = trainMap.get(trainId);
        if(slot == null) return "";

        if(slot.start <= timestamp && slot.end >= timestamp){
            for(Platform p : platforms){
                if(p.schedule.contains(slot)){
                    return p.id;
                }
            }
        }


    }
}