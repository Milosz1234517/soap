package soap.other.tableObjects;


import java.sql.Date;

public class Event {

  private long event_Id;
  private String name;
  private String place;
  private Date date;

  public Event(long event_Id, String name, String place, Date date) {
    this.event_Id = event_Id;
    this.name = name;
    this.place = place;
    this.date = date;
  }

  public Event(String name, String place, Date date) {
    this.name = name;
    this.place = place;
    this.date = date;
  }

  public long getEvent_Id() {
    return event_Id;
  }

  public void setEvent_Id(long event_Id) {
    this.event_Id = event_Id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
  }


  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return name + " " + place + " " + date;
  }
}
