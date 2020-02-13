package controller.VoucherProcessing;
/**
 * Class that represents Remote Voucher Firm
 * @autor Alexander Rai
 * @version 1.0
 */
import model.TravelVoucher;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RemoteVoucherFirm extends Remote {
    /**
     * Adds new voucher to list.
     * @param travelVoucher - voucher to add
     * */
    public void addVoucher(TravelVoucher travelVoucher) throws RemoteException;

    /**
     * Removes voucher from list by index
     * @param index - index of voucher to remove
     * @return the element previously at the specified position
     * */
    public TravelVoucher removeVoucher(int index) throws RemoteException;

    /**
     * Removes a specific object from list
     * @param travelVoucher - TravelVoucher object to remove
     * @return true if this list contained the specified element
     * */
    public boolean removeVoucher(TravelVoucher travelVoucher) throws RemoteException;

    /**
     * Returns travel voucher list
     * */
    public List<TravelVoucher> getTravelVouchers() throws RemoteException;

    /**
     * Sorts vouchers by price
     * @param ascending - is ascending
     */
    public List<TravelVoucher> sortVouchersByPrice(boolean ascending) throws RemoteException;

    /**
     * Sorts vouchers by duration
     * @param ascending - is ascending
     */
    public List<TravelVoucher> sortVouchersByDuration(boolean ascending) throws RemoteException;
}
