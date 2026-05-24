import java.util.*;

class RoomBooking{

    static class Meeting{
        String meetingId;
        String roomId;
        int start, end;
    
    Meeting(String meetingId, String roomId, int start, int end){
        this.meetingId = meetingId;
        this.roomId = roomId;
        this.start = start;
        this.end = end;
    }
    }

    private final TreeMap<String> rooms;
    private final Map<String, TreeMap<Integer, Meeting>>schedules;
    private final Map<String, Meeting>meetingMap;

    public RoomBooking(List<String> roomIds){
        rooms = new TreeSet<>(roomIds);
        schedules = new HashMap<>();
        meetingMap = new HashMap<>();
        for(String room : roomIds){
            schedules.put(room, new TreeMap<>());
        }
    }

    public String BookMeeting(String meetingId, int start, int end){

        if(meetingMap.containsKey(meetingId)) return "";

        for(String room : rooms){
            TreeMap<Integer, Meeting> schedule = schedules.get(room);
            if(isAvailable(schedule, start, end)){
                Meeting meeting = new Meeting(meetingId, room, start, end);
                schedule.put(start, meeting);
                meetingMap.put(meetingId, meeting);
                return room;
            }
        }
        return "";
    }

    private boolean isAvailable(TreeMap<Integer, Meeting> schedule, int start, int end){

        Map.Entry<Integer, Meeting> prev = schedule.floorEntry(start);
        if(prev != null && prev.getValue().end >= start){
            return false;
        }

        Map.Entry<Integer, Meeting>next = schedule.ceilingEntry(start);
        if(next != null && next.getValue().start <= end){
            return false;
        }

        return true;
    }

    public boolean cancelMeeting(String meetingId){

        Meeting meeting = meetingMap.get(meetingId);
        if(meeting == null) return false;

        TreeMap<Integer, Meeting>schedule = schedules.get(meeting.roomId);
        schedule.remove(meeting.start);
        meetingMap.remove(meetingId);
        return true;
    }

}