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
public class InvoiceTransformModel {

    private String nomorOrder;
    private String idBuyerCustomerOrder;
    
    private CustomerModel customer;
    private String kodePajak;
    
    private Date tanggalCreate;
    private Date tanggalKirim;
    
    private String cetakNote;
    
    private String kodePO;    
    private Date tanggalPO;
    
    private String keterangan;
    private String createBy;
    
    private Date dateTimeCreated;
    private String orderBy;

    private double total;    
    private double subTotal;
    
    private double discountFakPersen;
    private double discountFakTotal;
    
    private double ppnPersen;
    private double ppnTotal;
    
    private double totalM3;
    
    private String signature;
    private String imagePO;
    private String status;

    private ArrayList<ItemInvoiceCOModel> listItem;

    private char isCustomerLinked;
    private String jenisPajak;
        
    public InvoiceTransformModel() {
        this.isCustomerLinked = 0;
    }

    public String getJenisPajak() {
        return jenisPajak;
    }

    public void setJenisPajak(String jenisPajak) {
        this.jenisPajak = jenisPajak;
    }
        
    
    public char getIsCustomerLinked() {
        return isCustomerLinked;
    }
    
    public void setIsCustomerLinked(char isCustomerLinked) {
        this.isCustomerLinked = isCustomerLinked;
    }
        
    
    public ArrayList<ItemInvoiceCOModel> getListItem() {
        return listItem;
    }

    public void setListItem(ArrayList<ItemInvoiceCOModel> listItem) {
        this.listItem = listItem;
    }

    public String getIdBuyerCustomerOrder() {
        return idBuyerCustomerOrder;
    }

    public void setIdBuyerCustomerOrder(String idBuyerCustomerOrder) {
        this.idBuyerCustomerOrder = idBuyerCustomerOrder;
    }
       
    
    public String getNomorOrder() {
        return nomorOrder;
    }

    public void setNomorOrder(String nomorOrder) {
        this.nomorOrder = nomorOrder;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public String getKodePajak() {
        return kodePajak;
    }

    public void setKodePajak(String kodePajak) {
        this.kodePajak = kodePajak;
    }

    public Date getTanggalCreate() {
        return tanggalCreate;
    }

    public void setTanggalCreate(Date tanggalCreate) {
        this.tanggalCreate = tanggalCreate;
    }

    public Date getTanggalKirim() {
        return tanggalKirim;
    }

    public void setTanggalKirim(Date tanggalKirim) {
        this.tanggalKirim = tanggalKirim;
    }

    public String getCetakNote() {
        return cetakNote;
    }

    public void setCetakNote(String cetakNote) {
        this.cetakNote = cetakNote;
    }

    public String getKodePO() {
        return kodePO;
    }

    public void setKodePO(String kodePO) {
        this.kodePO = kodePO;
    }

    public Date getTanggalPO() {
        return tanggalPO;
    }

    public void setTanggalPO(Date tanggalPO) {
        this.tanggalPO = tanggalPO;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(Date dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getDiscountFakPersen() {
        return discountFakPersen;
    }

    public void setDiscountFakPersen(double discountFakPersen) {
        this.discountFakPersen = discountFakPersen;
    }

    public double getDiscountFakTotal() {
        return discountFakTotal;
    }

    public void setDiscountFakTotal(double discountFakTotal) {
        this.discountFakTotal = discountFakTotal;
    }

    public double getPpnPersen() {
        return ppnPersen;
    }

    public void setPpnPersen(double ppnPersen) {
        this.ppnPersen = ppnPersen;
    }

    public double getPpnTotal() {
        return ppnTotal;
    }

    public void setPpnTotal(double ppnTotal) {
        this.ppnTotal = ppnTotal;
    }

    public double getTotalM3() {
        return totalM3;
    }

    public void setTotalM3(double totalM3) {
        this.totalM3 = totalM3;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getImagePO() {
        return imagePO;
    }

    public void setImagePO(String imagePO) {
        this.imagePO = imagePO;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
