package soap.repositories;

import edu.pwr.soap.PaymentServer;
import org.springframework.stereotype.Component;
import soap.other.Connector;
import soap.other.daoObjects.PaymentDao;
import soap.other.tableObjects.Payment;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.sql.Date;

@Component
public class PaymentRepository {

    private PaymentDao paymentDao;

    @PostConstruct
    public void initData() {
        paymentDao = new PaymentDao(Connector.connectToDB());
    }

    public PaymentServer findPayment(long id) {
        PaymentServer paymentServer = new PaymentServer();
        Payment payment = paymentDao.get(id);
        paymentServer.setPaymentId(payment.getPayment_Id());
        paymentServer.setPaymentDate(payment.getPayment_Date().toString());
        paymentServer.setPaymentAmount(payment.getPayment_Amount());
        paymentServer.setPersonId(payment.getPerson_Id());
        paymentServer.setEventId(payment.getEvent_Id());
        paymentServer.setInstallmentNumber(payment.getInstallment_Number());
        return paymentServer;
    }

    public ArrayList<PaymentServer> findPayment() {
        ArrayList<PaymentServer> paymentRepositories = new ArrayList<>();
        for (Payment payment : paymentDao.getAll()) {
            PaymentServer paymentServer = new PaymentServer();
            paymentServer.setPaymentId(payment.getPayment_Id());
            paymentServer.setPaymentDate(payment.getPayment_Date().toString());
            paymentServer.setPaymentAmount(payment.getPayment_Amount());
            paymentServer.setPersonId(payment.getPerson_Id());
            paymentServer.setEventId(payment.getEvent_Id());
            paymentServer.setInstallmentNumber(payment.getInstallment_Number());
            paymentRepositories.add(paymentServer);
        }
        return paymentRepositories;
    }

    public String savePayment(String date, String amount, long personId, long eventId, long installmentNumber) {
        if (paymentDao.save(new Payment(Date.valueOf(date), amount, personId, eventId, installmentNumber))) {
            return "Saved Succesfully";
        } else {
            return "Saving Failed";
        }

    }

    public String deletePayment(long id) {

        if (paymentDao.delete(new Payment(id, null, null, 0, 0, 0))) {
            return "Deleted Succesfully";
        } else {
            return "Deleting Failed";
        }

    }
}
