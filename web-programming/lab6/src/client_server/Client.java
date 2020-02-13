package client_server;

import controller.VoucherProcessing.RemoteVoucherFirm;
import controller.VoucherProcessing.VoucherFirm;
import model.TravelVoucher;
import org.apache.log4j.Logger;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Class that represents RMI Client
 * @autor Alexander Rai
 * @version 1.0
 */
public class Client {
    private static final Logger clientLogger = Logger.getLogger(Client.class);

    public static void main(String[] args) {
        clientLogger.info("Client started working");
        Registry reg;
        RemoteVoucherFirm remoteVoucherFirm;
        try {
            reg = LocateRegistry.getRegistry(9001);
            remoteVoucherFirm = (RemoteVoucherFirm) reg.lookup("VoucherFirm");
        } catch (RemoteException | NotBoundException e) {
            clientLogger.info(e);
            return;
        }
        Random random = new Random();
        while(true) {

            try {
                int operation = random.nextInt(4);

                switch (operation){
                    case 0:
                        System.out.println("Get all vouchers: " + Arrays.toString(remoteVoucherFirm.getTravelVouchers()
                                .toArray()));
                        break;
                    case 1:
                        List<TravelVoucher> sortedByPrice = remoteVoucherFirm.sortVouchersByPrice(true);
                        System.out.println("Vouchers sorted by price: " + Arrays.toString(sortedByPrice.toArray()));
                        break;
                    case 2:
                        List<TravelVoucher> sortedByDuration = remoteVoucherFirm.sortVouchersByDuration(true);
                        System.out.println("Vouchers sorted by price: " + Arrays.toString(sortedByDuration.toArray()));
                        break;
                    case 3:
                        List<TravelVoucher> travelVouchers = remoteVoucherFirm.getTravelVouchers();
                        TravelVoucher travelVoucher = travelVouchers.get(random.nextInt(travelVouchers.size()));
                        System.out.println("Random voucher: " + travelVoucher);
                        break;
                    default:
                        System.out.println("Unknown operation");
                        break;
                }
            } catch (RemoteException e) {
                clientLogger.info(e.getMessage());
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                clientLogger.info(e.getMessage());
                break;
            }
        }
    }
}
