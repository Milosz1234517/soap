package soap.other.tableObjects;


public class Person {

  private long person_Id;
  private String name;
  private String surname;

  public Person(long person_Id, String name, String surname) {
    this.person_Id = person_Id;
    this.name = name;
    this.surname = surname;
  }

  public Person(String name, String surname) {
    this.name = name;
    this.surname = surname;
  }


  public long getPerson_Id() {
    return person_Id;
  }

  public void setPerson_Id(long person_Id) {
    this.person_Id = person_Id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  @Override
  public String toString() {
    return name + " " + surname;
  }
}
