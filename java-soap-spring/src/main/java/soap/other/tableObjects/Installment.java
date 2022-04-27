package soap.other.tableObjects;


import java.sql.Date;

public class Installment {

  private long installment_Id;
  private long event_Id;
  private long installment_Number;
  private Date due_Date;
  private String installment_Amount;

  public Installment(long installment_Id, long event_Id, long installment_Number, Date due_Date, String installment_Amount) {
    this.installment_Id = installment_Id;
    this.event_Id = event_Id;
    this.installment_Number = installment_Number;
    this.due_Date = due_Date;
    this.installment_Amount = installment_Amount;
  }

  public Installment(long event_Id, long installment_Number, Date due_Date, String installment_Amount) {
    this.event_Id = event_Id;
    this.installment_Number = installment_Number;
    this.due_Date = due_Date;
    this.installment_Amount = installment_Amount;
  }

  public long getInstallment_Id() {
    return installment_Id;
  }

  public void setInstallment_Id(long installment_Id) {
    this.installment_Id = installment_Id;
  }


  public long getEvent_Id() {
    return event_Id;
  }

  public void setEvent_Id(long event_Id) {
    this.event_Id = event_Id;
  }


  public long getInstallment_Number() {
    return installment_Number;
  }

  public void setInstallment_Number(long installment_Number) {
    this.installment_Number = installment_Number;
  }


  public Date getDue_Date() {
    return due_Date;
  }

  public void setDue_Date(Date due_Date) {
    this.due_Date = due_Date;
  }


  public String getInstallment_Amount() {
    return installment_Amount;
  }

  public void setInstallment_Amount(String installment_Amount) {
    this.installment_Amount = installment_Amount;
  }

  @Override
  public String toString() {
    return installment_Number + " " + due_Date + " " + installment_Amount;
  }
}
