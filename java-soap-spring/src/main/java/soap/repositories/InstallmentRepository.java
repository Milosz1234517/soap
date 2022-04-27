package soap.repositories;

import edu.pwr.soap.InstallmentServer;
import org.springframework.stereotype.Component;
import soap.other.Connector;
import soap.other.daoObjects.InstallmentDao;
import soap.other.tableObjects.Installment;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.util.ArrayList;

@Component
public class InstallmentRepository {

    private InstallmentDao installmentDao;

    @PostConstruct
    public void initData() {
        installmentDao = new InstallmentDao(Connector.connectToDB());
    }

    public InstallmentServer findInstallment(long id) {
        InstallmentServer installmentServer = new InstallmentServer();
        Installment installment = installmentDao.get(id);
        installmentServer.setInstallmentId(installment.getInstallment_Id());
        installmentServer.setInstallmentAmount(installment.getInstallment_Amount());
        installmentServer.setEventId(installment.getEvent_Id());
        installmentServer.setDuedate(installment.getDue_Date().toString());
        installmentServer.setInstallmentNumber(installment.getInstallment_Number());

        return installmentServer;
    }

    public ArrayList<InstallmentServer> findInstallments() {
        ArrayList<InstallmentServer> installmentServers = new ArrayList<>();
        for (Installment installment : installmentDao.getAll()) {
            InstallmentServer installmentServer = new InstallmentServer();
            installmentServer.setInstallmentId(installment.getInstallment_Id());
            installmentServer.setInstallmentAmount(installment.getInstallment_Amount());
            installmentServer.setEventId(installment.getEvent_Id());
            installmentServer.setDuedate(installment.getDue_Date().toString());
            installmentServer.setInstallmentNumber(installment.getInstallment_Number());
            installmentServers.add(installmentServer);
        }
        return installmentServers;
    }

    public String saveInstallment(String amount, long event, String date, long number) {
        if (installmentDao.save(new Installment(event, number, Date.valueOf(date), amount))) {
            return "Saved Succesfully";
        } else {
            return "Saving Failed";
        }

    }

    public String deleteInstallment(long id) {

        if (installmentDao.delete(new Installment(id,0, 0, null, null))) {
            return "Deleted Succesfully";
        } else {
            return "Deleting Failed";
        }

    }
}
