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
public class ItemInvoiceCOModel {
    private String noBukti;
    private String kodeBarangCustomerOrder;
    
    private String namaBarangCloud;
    private String line;
    
    private String idBuyer;
    private String idSeller;
    
    private double qtyOrderCloud;
    private double hargaCloud;
    
    private double subTotal;
    private double ppn;
    
    private double total;
    private String note;
    private String satuanCloud;
    private double meterKubik;

    private char isLinked;
    private ItemTransformModel linkedItem;

    public ItemInvoiceCOModel() {
        this.isLinked = 0;
    }
    
    public ItemTransformModel getLinkedItem() {
        return linkedItem;
    }

    public void setLinkedItem(ItemTransformModel linkedItem) {
        this.linkedItem = linkedItem;
    }
       
    public char getIsLinked() {
        return isLinked;
    }

    public void setIsLinked(char isLinked) {
        this.isLinked = isLinked;
    }    
        
    public double getMeterKubik() {
        return meterKubik;
    }

    public void setMeterKubik(double meterKubik) {
        this.meterKubik = meterKubik;
    }
        
    public String getNoBukti() {
        return noBukti;
    }

    public void setNoBukti(String noBukti) {
        this.noBukti = noBukti;
    }

    public String getKodeBarangCustomerOrder() {
        return kodeBarangCustomerOrder;
    }

    public void setKodeBarangCustomerOrder(String kodeBarangCustomerOrder) {
        this.kodeBarangCustomerOrder = kodeBarangCustomerOrder;
    }

    public String getNamaBarangCloud() {
        return namaBarangCloud;
    }

    public void setNamaBarangCloud(String namaBarang) {
        this.namaBarangCloud = namaBarang;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
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

    public double getQtyOrderCloud() {
        return qtyOrderCloud;
    }

    public void setQtyOrderCloud(double qtyOrderCloud) {
        this.qtyOrderCloud = qtyOrderCloud;
    }    

    public double getHargaCloud() {
        return hargaCloud;
    }

    public void setHargaCloud(double hargaCloud) {
        this.hargaCloud = hargaCloud;
    }    

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getPpn() {
        return ppn;
    }

    public void setPpn(double ppn) {
        this.ppn = ppn;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSatuanCloud() {
        return satuanCloud;
    }

    public void setSatuanCloud(String satuanCloud) {
        this.satuanCloud = satuanCloud;
    }    
    
}
