package client_server;
/**
 * Class that represents RMI Server
 * @autor Alexander Rai
 * @version 1.0
 */
import controller.VoucherProcessing.RemoteVoucherFirm;
import controller.VoucherProcessing.VoucherFirm;
import org.apache.log4j.Logger;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    private static final Logger serverLogger = Logger.getLogger(Server.class);

    public static void main(String[] args) {
        serverLogger.info("Server started");

        VoucherFirm voucherFirm = new VoucherFirm();
        try {
            RemoteVoucherFirm remoteVoucherFirm = (RemoteVoucherFirm) UnicastRemoteObject.exportObject(voucherFirm, 0);
            Registry reg = LocateRegistry.createRegistry(9001);
            reg.bind("VoucherFirm", remoteVoucherFirm);
        } catch (RemoteException | AlreadyBoundException e){
            serverLogger.info(e.getMessage());
        }
    }
}
