package models;

public class ReservationEvent {
    private int id;
    private int user_id;
    private int event_id;

    public ReservationEvent(int user_id, int event_id) {
        this.user_id = user_id;
        this.event_id = event_id;
    }

    public ReservationEvent(int id, int user_id, int event_id) {
        this.id = id;
        this.user_id = user_id;
        this.event_id = event_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public ReservationEvent() {
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    @Override
    public String toString() {
        return "ReservationEvent{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", event_id=" + event_id +
                '}';
    }
}
