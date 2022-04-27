package soap.other.tableObjects;


import java.sql.Date;

public class Payment {

  private long payment_Id;
  private Date payment_Date;
  private String payment_Amount;
  private long person_Id;
  private long event_Id;
  private long installment_Number;

  public Payment(long payment_Id, Date payment_Date, String payment_Amount, long person_Id, long event_Id, long installment_Number) {
    this.payment_Id = payment_Id;
    this.payment_Date = payment_Date;
    this.payment_Amount = payment_Amount;
    this.person_Id = person_Id;
    this.event_Id = event_Id;
    this.installment_Number = installment_Number;
  }

  public Payment(Date payment_Date, String payment_Amount, long person_Id, long event_Id, long installment_Number) {
    this.payment_Date = payment_Date;
    this.payment_Amount = payment_Amount;
    this.person_Id = person_Id;
    this.event_Id = event_Id;
    this.installment_Number = installment_Number;
  }

  public long getPayment_Id() {
    return payment_Id;
  }

  public void setPayment_Id(long payment_Id) {
    this.payment_Id = payment_Id;
  }


  public Date getPayment_Date() {
    return payment_Date;
  }

  public void setPayment_Date(Date payment_Date) {
    this.payment_Date = payment_Date;
  }


  public String getPayment_Amount() {
    return payment_Amount;
  }

  public void setPayment_Amount(String payment_Amount) {
    this.payment_Amount = payment_Amount;
  }


  public long getPerson_Id() {
    return person_Id;
  }

  public void setPerson_Id(long person_Id) {
    this.person_Id = person_Id;
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

}
