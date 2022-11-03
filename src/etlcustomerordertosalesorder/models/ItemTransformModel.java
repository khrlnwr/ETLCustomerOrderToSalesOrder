/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etlcustomerordertosalesorder.models;

/**
 *
 * @author Yusuf Fahruddin
 */
public class ItemTransformModel {
    private String kodeBarangLocal;
    private String namaBarangLocal;
    private double meterKubikLocal;
    
    private double qtyRequest;
    private double hargaLocal;
    private double hargaPokokLocal;
    private double hargaJualMinLocal;
    
    private String satuanLocal;
    private String satuan3Local;
    private double subTotal;
    
    private double discount1Local;
    private double discount2Local;
    private double discount3Local;

    private String tanggalExpiredDescLocal;
    private String isDiscountPrincipalLocal;

    private int isOtoHarga;
    private int isOtoHPP;
    
    public ItemTransformModel() {
        this.isOtoHarga = 0;
        this.isOtoHPP = 0;
    }
    
    public int getIsOtoHarga() {
        return isOtoHarga;
    }

    public void setIsOtoHarga(int isOtoHarga) {
        this.isOtoHarga = isOtoHarga;
    }

    public int getIsOtoHPP() {
        return isOtoHPP;
    }

    public void setIsOtoHPP(int isOtoHPP) {
        this.isOtoHPP = isOtoHPP;
    }
       
    public String getTanggalExpiredDescLocal() {
        return tanggalExpiredDescLocal;
    }

    public void setTanggalExpiredDescLocal(String tanggalExpiredDescLocal) {
        this.tanggalExpiredDescLocal = tanggalExpiredDescLocal;
    }
        
    public String getKodeBarangLocal() {
        return kodeBarangLocal;
    }

    public void setKodeBarangLocal(String kodeBarangLocal) {
        this.kodeBarangLocal = kodeBarangLocal;
    }

    public String getNamaBarangLocal() {
        return namaBarangLocal;
    }

    public void setNamaBarangLocal(String namaBarangLocal) {
        this.namaBarangLocal = namaBarangLocal;
    }

    public Double getMeterKubikLocal() {
        return meterKubikLocal;
    }

    public void setMeterKubikLocal(Double meterKubikLocal) {
        this.meterKubikLocal = meterKubikLocal;
    }

    public double getQtyRequest() {
        return qtyRequest;
    }

    public void setQtyRequest(double qtyRequest) {
        this.qtyRequest = qtyRequest;
    }

    public double getHargaLocal() {
        return hargaLocal;
    }

    public void setHargaLocal(double hargaLocal) {
        this.hargaLocal = hargaLocal;
    }

    public double getHargaPokokLocal() {
        return hargaPokokLocal;
    }

    public void setHargaPokokLocal(double hargaPokokLocal) {
        this.hargaPokokLocal = hargaPokokLocal;
    }

    public double getHargaJualMinLocal() {
        return hargaJualMinLocal;
    }

    public void setHargaJualMinLocal(double hargaJualMinLocal) {
        this.hargaJualMinLocal = hargaJualMinLocal;
    }

    public String getSatuanLocal() {
        return satuanLocal;
    }

    public void setSatuanLocal(String satuanLocal) {
        this.satuanLocal = satuanLocal;
    }

    public String getSatuan3Local() {
        return satuan3Local;
    }

    public void setSatuan3Local(String satuan3Local) {
        this.satuan3Local = satuan3Local;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getDiscount1Local() {
        return discount1Local;
    }

    public void setDiscount1Local(double discount1Local) {        
        this.discount1Local = discount1Local;
    }

    public double getDiscount2Local() {
        return discount2Local;
    }

    public void setDiscount2Local(double discount2Local) {
        this.discount2Local = discount2Local;
    }

    public double getDiscount3Local() {
        return discount3Local;
    }

    public void setDiscount3Local(double discount3Local) {
        this.discount3Local = discount3Local;
    }

    public String getIsDiscountPrincipalLocal() {
        return isDiscountPrincipalLocal;
    }

    public void setIsDiscountPrincipalLocal(String isDiscountPrincipalLocal) {
        this.isDiscountPrincipalLocal = isDiscountPrincipalLocal;
    }
    

    
    
    
    
}
