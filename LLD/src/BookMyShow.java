import java.util.*;

class BookMyShow{
    
    Map<Integer, Cinema> cinemas;
    Map<Integer, Show> shows;
    Map<String, Ticket> ticket;

    public void init(){
        cinemas = new HashMap<>();
        shows = new HashMap<>();
        ticket = new HashMap<>();
    }

    class Cinema{
        int cinemaId;
        int cityId;
        List<Screen> screens;

        Cinema(int cinemaId, int cityId, int screenCount, int row, int col){
            this.cinemaId = cinemaId;
            this.cityId = cityId;
            screens = new ArrayList<>();
            for(int i = 0; i < screenCount; i++){
                screens.add(new Screen(i+1, row, col));
            }
        }
    }

    class Screen{

        int screenIndex;
        int row, col;
        Screen(int screenIndex, int rows, int cols){
            this.screenIndex = screenIndex;
            this.rows = rows;
            this.cols = cols;
        }
    }

    class Show{

        int showId;
        int movieId;
        int cinemaId;
        int screenIndex;
        long startTime, endTime;
        boolean[][] seats;
        int freeSeats;

        Show(int showId, int movieId, int cinemaId, int screenIndex, long startTime, long endTime,
             int rows, int cols){

            this.showId = showId;
            this.movieId = movieId;
            this.cinemaId = cinemaId;
            this.screenIndex = screenIndex;
            this.startTime = startTime;
            this.endTime = endTime;
            seats = new boolean[rows][cols];
            freeSeats = rows*cols;
        }
    }

    class Ticket{

        String ticketId;
        int showId;
        List<int[]>bookedSeats;
        boolean active;

        Ticket(String ticketId, int showId, List<int[]>bookedSeats){
            this.ticketId = ticketId;
            this.showId = showId;
            this.bookedSeats = bookedSeats;
            this.active = true;
        }
    }

    public void addCinema(int cinemaId, int cityId, int screenCount, int screenRow, int screenColumn){

        cinemas.put(cinemaId, new Cinema(cinemaId, cityId, screenCount, screenRow, screenColumn));

    }

    public void addShow(int showId, int movieId, int cinemaId, int screenIndex, long startTime, long endTime){

        Cinema cinema = cinemas.get(cinemaId);
        Screen screen = cinema.screens.get(screenIndex-1);
        Show show = new Show(showId, movieId, cinemaId, screenIndex, startTime, endTime, screen.rows, screen.cols);
        shows.put(showId, show);

    }

    public List<String> bookTicket(String ticketId, int showId, int ticketsCount){

        Show show = shows.get(showId);
        if(show == null || show.freeSeats < ticketsCount) return new ArrayList<>();

        int row = show.seats.length;
        int col = show.seats[0].length;

        List<int[]>allocated = new ArrayList<>();

        for(int r = 0; r < row; r++){
            int count = 0;
            for(int c = 0; c < col; c++){
                if(!show.seats[r][c]) count++;
                else count = 0;
                if(count == ticketsCount){
                    for(int k = c-ticketsCount+1; k <=c; k++){
                        shows.seats[r][k] = true;
                        allocate.add(new int{r, k});
                    }
                    shows.freeSeats-=ticketsCount;
                    return finalizeBooking(ticketId, showId, allocated);
                }
            }
        }

        for(int r = 0; r < row && allocated.size() < ticketsCount; r++){
            for(int c = 0; c < col && allocated.size() < ticketsCount; c++){
                if(!shows.seats[r][c]){
                    shows.seat[r][c] = true;
                    allocated.add(new int[]{r, c});
                }
            }
        }
        show.freeSeats-=ticketsCount;
        return finalizeBooking(ticketId, shhowId, allocated);
    }


    private List<String>finalizeBooking(String ticketId, int showId, List<int[]>seats){

        ticket.put(ticketId, new Ticket(ticketId, showId, seats));
        List<String>res = new ArrayList<>();
        for(int[] s : seats){
            res.add(s[0]+ " - " +s[1]);
        }
    return res;
    }

    public boolean cancelTicket(String ticketId){

        Ticket ticket = ticket.get(ticketId);
        if(ticket == null || !ticket.active) return false;

        Show show = shows.get(ticket.showId);
        for(int[] seat : ticket.bookedSeats){
            show.seats[seat[0]][seat[1]] = false;
            show.freeSeats++;
        }
        ticket.active = false;
        return true;
    }


    public int getFreeSeatsCount(int showId){
        Show show = shows.get(showId);
        return (show == null) ? 0 : show.freeSeats;
    }

    public list<Integer> listCinemas (int movieId, int cityId){

        Set<Integer> result = new TreeSet<>();
        for(Show show : shows.values()){
            if(show.movieId == movieId){
                Cinema cinema = cinemas.get(show.cinemaId);
                if(cinema.cityId == cityId){
                    result.add(show.cinemaId)l
                }
            }
        }
        return new ArrayList<>(result);
    }


    public List<Integer> listShows(int movieId, int cinemaId){

        List<Show>list = new ArrayList<>();
        for(Show show : shows.values()){
            if(show.movieId == movieId && show.cinemaId == cinemaId){
                list.add(show);
            }
        }
        list.sort((a, b) -> {
            if(b.startTime != a.startTime){
                return Long.compare(b.startTime, a.startTime);
            }
                return Integer.compare(a.showId, b.showId);
        });

        List<Integer>res = new ArrayList<>();
        for(show s : list){
            res.add(s.showId);
        }
    return res;
    }

}