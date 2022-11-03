/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etlcustomerordertosalesorder.models;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Yusuf Fahruddin
 */
public class InvoiceCOModel {
    private String noBukti;
    private Date tanggal;
    private String kodePajak;
    
    private String idBuyer;
    private String idSeller;
    
    private String businessName;
    private String address;
    
    private double subTotal;
    private String persentasePPN;
    
    private double ppn;
    private String ppnRp;
    
    private double total;
    private String lunas;
    
    private String statusOrder;
    
    private String lunasTime;    
    private String tanggalKirim;
    
    private char isNewOrder;
    private String dateNewOrder;
    
    private char isInProcess;
    private String dateInProcess;
    
    private char isReadyToSend;
    private String dateReadyToSend;
    
    private char isInDelivery;
    private String dateInDelivery;
    
    private char isComplained;
    private String dateComplained;
    
    private char isFinished;
    private String dateFinished;
    
    private char isCancelRequest;
    private String dateCancelRequest;
    
    private char isCanceled;
    private String dateCanceled;
    
    private String createBy;
    private String phoneNumber;
    
    private String kecamatan;
    private String kota;
    private String kodePos;    
    private String provinsi;
    private String kelurahan;
    
    private ArrayList<ItemInvoiceCOModel> listItem;

       
    public String getNoBukti() {
        return noBukti;
    }

    public void setNoBukti(String noBukti) {
        this.noBukti = noBukti;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getKodePajak() {
        return kodePajak;
    }

    public void setKodePajak(String kodePajak) {
        this.kodePajak = kodePajak;
    }

    public String getIdBuyer() {
        return idBuyer;
    }

    public void setIdBuyer(String idBuyer) {
        this.idBuyer = idBuyer;
    }

    public String getIdSeller() {
        return idSeller;
    }

    public void setIdSeller(String idSeller) {
        this.idSeller = idSeller;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public String getPersentasePPN() {
        return persentasePPN;
    }

    public void setPersentasePPN(String persentasePPN) {
        this.persentasePPN = persentasePPN;
    }

    public double getPpn() {
        return ppn;
    }

    public void setPpn(double ppn) {
        this.ppn = ppn;
    }

    public String getPpnRp() {
        return ppnRp;
    }

    public void setPpnRp(String ppnRp) {
        this.ppnRp = ppnRp;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getLunas() {
        return lunas;
    }

    public void setLunas(String lunas) {
        this.lunas = lunas;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public String getLunasTime() {
        return lunasTime;
    }

    public void setLunasTime(String lunasTime) {
        this.lunasTime = lunasTime;
    }

    public String getTanggalKirim() {
        return tanggalKirim;
    }

    public void setTanggalKirim(String tanggalKirim) {
        this.tanggalKirim = tanggalKirim;
    }

    public char getIsNewOrder() {
        return isNewOrder;
    }

    public void setIsNewOrder(char isNewOrder) {
        this.isNewOrder = isNewOrder;
    }

    public String getDateNewOrder() {
        return dateNewOrder;
    }

    public void setDateNewOrder(String dateNewOrder) {
        this.dateNewOrder = dateNewOrder;
    }

    public char getIsInProcess() {
        return isInProcess;
    }

    public void setIsInProcess(char isInProcess) {
        this.isInProcess = isInProcess;
    }

    public String getDateInProcess() {
        return dateInProcess;
    }

    public void setDateInProcess(String dateInProcess) {
        this.dateInProcess = dateInProcess;
    }

    public char getIsReadyToSend() {
        return isReadyToSend;
    }

    public void setIsReadyToSend(char isReadyToSend) {
        this.isReadyToSend = isReadyToSend;
    }

    public String getDateReadyToSend() {
        return dateReadyToSend;
    }

    public void setDateReadyToSend(String dateReadyToSend) {
        this.dateReadyToSend = dateReadyToSend;
    }

    public char getIsInDelivery() {
        return isInDelivery;
    }

    public void setIsInDelivery(char isInDelivery) {
        this.isInDelivery = isInDelivery;
    }

    public String getDateInDelivery() {
        return dateInDelivery;
    }

    public void setDateInDelivery(String dateInDelivery) {
        this.dateInDelivery = dateInDelivery;
    }

    public char getIsComplained() {
        return isComplained;
    }

    public void setIsComplained(char isComplained) {
        this.isComplained = isComplained;
    }

    public String getDateComplained() {
        return dateComplained;
    }

    public void setDateComplained(String dateComplained) {
        this.dateComplained = dateComplained;
    }

    public char getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(char isFinished) {
        this.isFinished = isFinished;
    }

    public String getDateFinished() {
        return dateFinished;
    }

    public void setDateFinished(String dateFinished) {
        this.dateFinished = dateFinished;
    }

    public char getIsCancelRequest() {
        return isCancelRequest;
    }

    public void setIsCancelRequest(char isCancelRequest) {
        this.isCancelRequest = isCancelRequest;
    }

    public String getDateCancelRequest() {
        return dateCancelRequest;
    }

    public void setDateCancelRequest(String dateCancelRequest) {
        this.dateCancelRequest = dateCancelRequest;
    }

    public char getIsCanceled() {
        return isCanceled;
    }

    public void setIsCanceled(char isCanceled) {
        this.isCanceled = isCanceled;
    }

    public String getDateCanceled() {
        return dateCanceled;
    }

    public void setDateCanceled(String dateCanceled) {
        this.dateCanceled = dateCanceled;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKodePos() {
        return kodePos;
    }

    public void setKodePos(String kodePos) {
        this.kodePos = kodePos;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public ArrayList<ItemInvoiceCOModel> getListItem() {
        return listItem;
    }

    public void setListItem(ArrayList<ItemInvoiceCOModel> listItem) {
        this.listItem = listItem;
    }
    
    
    
}
