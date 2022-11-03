/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etlcustomerordertosalesorder;

import etlcustomerordertosalesorder.models.CustomerModel;
import etlcustomerordertosalesorder.models.InvoiceTransformModel;
import etlcustomerordertosalesorder.models.ItemInvoiceCOModel;
import etlcustomerordertosalesorder.models.ItemTransformModel;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Koneksi;
import utils.Repository;

/**
 *
 * @author Yusuf Fahruddin
 */
public class Transformation {
    
    private Koneksi mKoneksi;
    private char IS_LINKED = '1';
        
    public Transformation() {
        mKoneksi = new Koneksi();
    }
    
    // STEP 1: Cek is customer linked
    public void getCustomerInvoiceLinked() {
        if (Repository.getListInvoiceTransform() == null)
            return;
        
        if (Repository.getListInvoiceTransform().isEmpty())
            return;
        
        int total = Repository.getListInvoiceTransform().parallelStream().reduce(0, (value, inv) -> {
            
            Map<String, Object> returns = new LinkedHashMap<>();
            returns = getUnitCustomerLinked(inv.getIdBuyerCustomerOrder());
            
            String isLinked = returns.get("isLinked").toString();
            
            inv.setIsCustomerLinked(isLinked.charAt(0));
            if (isLinked.charAt(0) == IS_LINKED) {
                CustomerModel instance = new CustomerModel();

                instance.setKodeGudang(returns.get("kode_gudang") == null ? "" : returns.get("kode_gudang").toString());                
                instance.setKodeSales(returns.get("kode_sales") == null ? "" : returns.get("kode_sales").toString());
                instance.setNamaCustomer(returns.get("nama_customer") == null ? "" : returns.get("nama_customer").toString());
                instance.setKodeKelompok(returns.get("kode_kelompok") == null ? "" : returns.get("kode_kelompok").toString());
                instance.setKodeCustomer(returns.get("kode_customer") == null ? "" : returns.get("kode_customer").toString());
                inv.setCustomer(instance);
            }
            
                                    
            return value + 1; 
            
        }, Integer :: sum);
        
        
        // System.out.println("Total: " + total);
    }
    
    
    private Map<String, Object> getUnitCustomerLinked(String idBuyer) {
        int linked = 0;
        
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("isLinked", linked);
        
        if (idBuyer == null)
            return params;
        
        if (idBuyer.isEmpty())
            return params;
        
        String queryCustomerLinked = "SELECT c.kdkel,c.kdcust,c.nmcust,sp.KdGd,sp.KdSales from tblcustomer c \n"
                + "LEFT JOIN tblSalesPerson sp on sp.KdSales = c.KdSales\n"
                + "WHERE BuyerID2 = '" + idBuyer + "' AND NonAktif='0'";
        
        // System.out.println("query: " + queryCustomerLinked);        
        try {            
            if (mKoneksi == null) 
                mKoneksi = new Koneksi();            
            Statement stCustomerLinked = mKoneksi.Koneksi().createStatement();
            ResultSet rsCustomerLinked = stCustomerLinked.executeQuery(queryCustomerLinked);
            
            int size_result = rsCustomerLinked.getFetchSize();
            
            if (size_result > 0) {
                while (rsCustomerLinked.next()) {                    
                    params.put("isLinked", 1);
                    params.put("kode_gudang", rsCustomerLinked.getString("KdGd"));
                    params.put("kode_sales", rsCustomerLinked.getString("KdSales"));
                    params.put("kode_customer", rsCustomerLinked.getString("kdcust"));
                    params.put("kode_kelompok", rsCustomerLinked.getString("kdkel"));
                    params.put("nama_customer", rsCustomerLinked.getString("nmcust"));
                    break;
                }
            }
            
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
                
        return params;
    }
    
    
    public void getIsItemsLinked() {
        if (Repository.getListInvoiceTransform() == null)
            return;
        
        if (Repository.getListInvoiceTransform().isEmpty())
            return;
        
        int totalInvoiceProcessed = Repository.getListInvoiceTransform().parallelStream().reduce(0, (value, inv) -> {            
            getUnitInvoiceItemsLinked(inv);                                                
            return value + 1;             
        }, Integer :: sum);
        
    }

    private void getUnitInvoiceItemsLinked(InvoiceTransformModel invoice) {
        if (invoice == null)
            return;
               
        if (invoice.getNomorOrder().isEmpty() || invoice.getListItem().isEmpty()) 
            return;
                
        // map reduce
        int totalItemProcessed = invoice.getListItem().parallelStream().reduce(0, (value, item) -> {            
            String kodeBarangCO = item.getKodeBarangCustomerOrder();
            String satuan = item.getSatuanCloud();
                        
            Map<String, Object> _ = getItemStatus(kodeBarangCO, satuan);
                        
            char isLinked = _.get("isLinked").toString().charAt(0);            
            item.setIsLinked(isLinked);
                      
            if (isLinked == IS_LINKED) {

                ItemTransformModel instance = new ItemTransformModel();
                instance.setKodeBarangLocal(_.get("kode_barang").toString());
                instance.setNamaBarangLocal(_.get("nama_barang").toString());
                instance.setMeterKubikLocal(Double.valueOf(_.get("meter_kubik").toString()));
                instance.setQtyRequest(item.getQtyOrderCloud());
                instance.setHargaPokokLocal(Double.valueOf(_.get("harga_pokok").toString()));
                instance.setHargaJualMinLocal(Double.valueOf(_.get("harga_jual_min").toString()));
                instance.setSatuanLocal(satuan);
                
                item.setLinkedItem(instance);
            }
            
            return value + 1;            
        }, Integer :: sum);
        
    }
        
    // CEK STATUS BARANG: IS LINKED AND AVAILABLE 
    private Map<String, Object> getItemStatus(String kodeBarangCO, String satuan) {
        
        Map<String, Object> resultMap = new LinkedHashMap();
        resultMap.put("isLinked", 0);
            
        if (kodeBarangCO == null)
            return resultMap;
        
        if (satuan == null)
            return resultMap;
        
        if (kodeBarangCO.isEmpty() || satuan.isEmpty())
            return resultMap;
        
        String queryGetStatus = "SELECT TOP 1\n" +
            "	tblIvMst.KdBrg,\n" +
            "	tblIvMst.NmBrg,\n" +
            "	tblIvMst.Satuan,\n" +
            "	tblIvMst.Satuan2,\n" +
            "	tblIvMst.Satuan3,\n" +
            "	tblIvMst.MKubik1,\n" +
            "	tblIvMst.MKubik2,\n" +
            "	tblIvMst.MKubik3,\n" +
            "	tblIvMst.HrgJualMin,\n" +
            "	tblHrgPokok.HrgPokok\n" +
            " FROM tblIvMst\n" +
            " LEFT JOIN tblHrgPokok ON tblHrgPokok.KdBrg = tblIvMst.KdBrg\n" +
            " WHERE tblIvMst.KdBrg IN (\n" +
            "	SELECT KdBrg \n" +
            "	FROM tblIvMst \n" +
            "	WHERE tblIvMst.KdBrgCO = '" + kodeBarangCO + "'\n" +
            " ) AND tblIvMst.NonAktif = 0 \n" +
            " ORDER BY tblHrgPokok.Periode DESC";
        
        if (mKoneksi == null) 
            mKoneksi = new Koneksi();         
        try {
            Statement stGetStatus = mKoneksi.Koneksi().createStatement();
            ResultSet rsGetStatus = stGetStatus.executeQuery(queryGetStatus);
            
            if (rsGetStatus.getFetchSize() > 0) {
                
                while (rsGetStatus.next()) {
                    
                    resultMap.put("isLinked", 1);
                    resultMap.put("kode_barang", rsGetStatus.getString("KdBrg"));
                    resultMap.put("nama_barang", rsGetStatus.getString("nmbrg"));
                    
                    if (satuan.equalsIgnoreCase(rsGetStatus.getString("Satuan"))) 
                        resultMap.put("meter_kubik", rsGetStatus.getString("MKubik1"));
                    else if (satuan.equalsIgnoreCase(rsGetStatus.getString("Satuan2"))) 
                        resultMap.put("meter_kubik", rsGetStatus.getString("MKubik2"));
                    else if (satuan.equalsIgnoreCase(rsGetStatus.getString("Satuan3"))) 
                        resultMap.put("meter_kubik", rsGetStatus.getString("MKubik3"));                                                         
                    
                    resultMap.put("harga_jual_min", rsGetStatus.getDouble("HrgJualMin"));
                    resultMap.put("harga_pokok", rsGetStatus.getDouble("HrgPokok"));
                    
                    break;
                }
                
            } 
            
        } catch (SQLException ex) {
            // Logger.getLogger(Transformation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERR: " + ex.getMessage());
        }
        
        return resultMap;        
    }
    
    
    // GET PAJAK ITEM PER INVOICE
    public void getPajakItem() {
        if (Repository.getListInvoiceTransform() == null)
            return;
        
        if (Repository.getListInvoiceTransform().isEmpty()) 
            return;
        
        // process invoice dengan semua item sudah linked
        int total = Repository.getListInvoiceTransform().parallelStream().reduce(0, (value, inv) -> {
            if (isAllItemLinked(inv.getListItem()) == 1) {
                String jenisPajak = getJenisPajakItem(inv.getListItem().get(0).getLinkedItem().getKodeBarangLocal());
                if (jenisPajak != null) {
                    if (jenisPajak.isEmpty() == false) 
                        inv.setJenisPajak(jenisPajak);                    
                }
            }
            
            return value + 1;
        }, Integer :: sum);
    }

    private String getJenisPajakItem(String kodeBarang) {
        
        String jenisPajak = "";
        
        if (kodeBarang == null) 
            return null;
        
        if (kodeBarang.isEmpty())
            return null;
        
        String queryGetPajak = "{call GetIvStock(?)}";
        CallableStatement stGetPajak;
        
        try {
            stGetPajak = mKoneksi.Koneksi().prepareCall(queryGetPajak, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stGetPajak.setString(1, kodeBarang);
            ResultSet rsGetPajak = stGetPajak.executeQuery();
            rsGetPajak.last();

            int rsSize = rsGetPajak.getRow();
            rsGetPajak.beforeFirst();

            if (rsSize > 0) {                
                while (rsGetPajak.next()) {
                    jenisPajak = rsGetPajak.getString("JnsPPN");
                }
            }

        } catch (SQLException ex) {
            System.out.println("ERR getJenisPajakItem: " + ex.getMessage());
        }
        
        return jenisPajak;
    }
        
    
    private int isAllItemLinked(ArrayList<ItemInvoiceCOModel> listItem) {
        int result = 1;
        
        for (ItemInvoiceCOModel instance: listItem) {
            if (instance.getIsLinked() == 0 || instance.getIsLinked() == '0') {
                result = 0;
                break;
            }                
        }
        
        return result;
    }
    
    
    public void getPriceDiscItems() {
        if (Repository.getListInvoiceTransform() == null)
            return;        
        if (Repository.getListInvoiceTransform().isEmpty()) 
            return;
        
        // process invoice dengan semua item sudah linked
        int total = Repository.getListInvoiceTransform().parallelStream().reduce(0, (value, inv) -> {
            if (isAllItemLinked(inv.getListItem()) == 1) 
                getInvoiceItemDiscPrice(inv);            
            
            return value + 1;
        }, Integer :: sum);
    }
       
    // Per invoice
    private void getInvoiceItemDiscPrice(InvoiceTransformModel invTrans) {
        
        if (invTrans.getListItem().isEmpty())
            return;
        
        int totalProcessed = invTrans.getListItem().parallelStream().reduce(0, (value, item) -> {
            Boolean isCustomerLinked = invTrans.getIsCustomerLinked() == IS_LINKED;            
            
            double price = 0.0, discount1 = 0.0, discount2 = 0.0, discount3 = 0.0;
            Map<String, Object> unitPriceDisc = new LinkedHashMap();
            
            if (isCustomerLinked) {
                unitPriceDisc = getUnitItemDiscPrice(item.getLinkedItem().getKodeBarangLocal(), invTrans.getCustomer().getKodeCustomer());
                // System.out.println("unit price disc " + unitPriceDisc);
                
                if (unitPriceDisc.containsKey("pricelist_khusus")) {
                    price = Double.valueOf(unitPriceDisc.get("pricelist_khusus") == null ? "0" : unitPriceDisc.get("pricelist_khusus").toString());
                    discount1 = Double.valueOf(unitPriceDisc.get("discount_1") == null ? "0" : unitPriceDisc.get("discount_1").toString());
                    discount2 = Double.valueOf(unitPriceDisc.get("discount_2") == null ? "0" : unitPriceDisc.get("discount_2").toString());
                    discount3 = Double.valueOf(unitPriceDisc.get("discount_3") == null ? "0" : unitPriceDisc.get("discount_3").toString());
                }
            }
                                
            if (price == 0.0) 
                price = item.getHargaCloud();
                        
            item.getLinkedItem().setHargaLocal(price);
            item.getLinkedItem().setDiscount1Local(discount1);
            item.getLinkedItem().setDiscount2Local(discount2);
            item.getLinkedItem().setDiscount3Local(discount3);
            
            return value + 1;
        }, Integer :: sum);
    }
    
    // Per item 
    private Map<String, Object> getUnitItemDiscPrice(String kodeBarang, String kodeCustomer) {
        
        Map<String, Object> resultMap = new LinkedHashMap();
                                            
        if (kodeBarang == null)
            return resultMap;        
        if (kodeCustomer == null)
            return resultMap;
        
        if (kodeBarang.isEmpty() || kodeCustomer.isEmpty())
            return resultMap;
       
        
        String queryGetPrice = "SELECT TOP 1\n" +
            "	tblHrgCustDtl.KdCust,\n" +
            "	tblHrgCustDtl.KdBrg,\n" +
            "	tblHrgCustDtl.Hrg,\n" +
            "	tblHrgCustDtl.Tgl,\n" +
            "	tblHrgCustDtl.Satuan,\n" +
            "	tblHrgCustDtl.PrsDisc1,\n" +
            "	tblHrgCustDtl.PrsDisc2,\n" +
            "	tblHrgCustDtl.PrsDisc3\n" +
            " FROM tblHrgCustDtl\n" +
            " WHERE tblHrgCustDtl.KdBrg = '" + kodeBarang + "' AND tblHrgCustDtl.KdCust = '" + kodeCustomer + "'\n" +
            " ORDER BY tblHrgCustDtl.Tgl DESC";
        
        if (mKoneksi == null) 
            mKoneksi = new Koneksi();        
        
        try {
            Statement stGetPrice = mKoneksi.Koneksi().createStatement();
            ResultSet rsGetPrice = stGetPrice.executeQuery(queryGetPrice);
                                    
            if (rsGetPrice.getFetchSize() > 0) {               
                while (rsGetPrice.next()) {
                    resultMap.put("pricelist_khusus", rsGetPrice.getDouble("Hrg"));
                    resultMap.put("discount_1", rsGetPrice.getDouble("PrsDisc1"));
                    resultMap.put("discount_2", rsGetPrice.getDouble("PrsDisc2"));
                    resultMap.put("discount_3", rsGetPrice.getDouble("PrsDisc3"));                    
                }
            }
            
        } catch (Exception e) {
            System.out.println("Exception getUnitItemPrice " + e.getMessage());
        }
        
        
        return resultMap;
        
    }
        
    public void getIsStockAvailable() {
        if (Repository.getListInvoiceTransform() == null)
            return;
        
        if (Repository.getListInvoiceTransform().isEmpty())
            return;
        
        // map reduce
        int totalProcessed = Repository.getListInvoiceTransform().parallelStream().reduce(0, (value, inv) -> {
            isStockAvailable(inv);            
            return value + 1;
        }, Integer :: sum);
        
    }
    
    // Check stock > 0 for linked item   
    // Per invoice
    private int isStockAvailable(InvoiceTransformModel invoice) {
        int available = 1;
        
        if (invoice.getIsCustomerLinked() == 0 || invoice.getIsCustomerLinked() == '0')
            return 0;
        
        if (invoice.getCustomer().getKodeGudang() == null)
            return 0;
        
        if (invoice.getCustomer().getKodeGudang().isEmpty() || invoice.getListItem().isEmpty())
            return 0;
        
        if (isAllItemLinked(invoice.getListItem()) != 1) 
            return 0;
        
        String listKodeBarang = "(";        
        for (ItemInvoiceCOModel item : invoice.getListItem()) 
            listKodeBarang = listKodeBarang + "'" + item.getLinkedItem().getKodeBarangLocal() + "',";
        
        listKodeBarang = listKodeBarang.substring(0, listKodeBarang.length() - 1);
        listKodeBarang = listKodeBarang + ")";
       
        String queryCekStock = "SELECT \n" +
            "	tblIvGStk.KdBrg,\n" +
            "	tblIvGStk.KdGd,\n" +
            "	tblIvGStk.Qty\n" +
            " FROM tblIvGStk\n" +
            " WHERE tblIvGStk.KdGd = '" + invoice.getCustomer().getKodeGudang() + "' AND tblIvGStk.KdBrg IN " + listKodeBarang;
                
        try {            
            if (mKoneksi == null)
                mKoneksi = new Koneksi();
            
            Statement stCekStock = mKoneksi.Koneksi().createStatement();
            ResultSet rsCekStock = stCekStock.executeQuery(queryCekStock);
            
            if (rsCekStock.getFetchSize() > 0) {
                while (rsCekStock.next()) {
                    if (rsCekStock.getDouble("Qty") < 1) {
                        available = 0;
                        break;
                    }                         
                }
            }                         
        } catch (Exception e) {
            System.out.println("ERR Exception " + e.getMessage());
        }
        
        return available;
    }
    
    public void getCustomerValidation() {
        if (Repository.getListInvoiceTransform() == null)
            return;
        if (Repository.getListInvoiceTransform().isEmpty())
            return;

        int totalInvoice = Repository.getListInvoiceTransform().parallelStream().reduce(0, (value, inv) -> {
            if (inv.getIsCustomerLinked() == IS_LINKED || inv.getIsCustomerLinked() == '1') 
                inv.getCustomer().setIsCustomerValid(isCustomerValid(inv));                       
            return value + 1;
        }, Integer :: sum);
    }
    
    // per customer in each invoice
    private int isCustomerValid(InvoiceTransformModel invTrans) {
        int result = 1;
        
        if (invTrans.getCustomer() == null)
            return 0;        
        if (invTrans.getCustomer().getKodeCustomer().isEmpty())
            return 0;       
                
        try {
            String queryCekLimit = "SELECT KreditLimit, Saldo, ProtekLimit, Whitelist, TglAktifProteksi, TF FROM tblCustomer c\n"
                + "WHERE c.KdCust = '" + invTrans.getCustomer().getKodeKelompok() + "' AND NonAktif='0' ORDER BY c.KdCust";

            if (mKoneksi == null)
                mKoneksi = new Koneksi();
            
            Statement stCekLimit = mKoneksi.Koneksi().createStatement();
            ResultSet rsCekLimit = stCekLimit.executeQuery(queryCekLimit);

            if (rsCekLimit.getFetchSize() < 1) {
                System.out.println("Customer " + invTrans.getCustomer().getKodeCustomer() + " tidak ditemukan ...");
                result = 0;
            }
            
            Boolean isLimit = false;
            Boolean isProtekLimit = false;
            Boolean isWhitelist = false;
            Date tanggalAktifProteksi = new Date();
            int freqTF = 0;
            
            while (rsCekLimit.next()) {
                isLimit = rsCekLimit.getDouble("KreditLimit") - rsCekLimit.getDouble("Saldo") <= 0.0;
                isProtekLimit = rsCekLimit.getBoolean("ProtekLimit");
                isWhitelist = rsCekLimit.getBoolean("Whitelist");
                
                tanggalAktifProteksi = rsCekLimit.getDate("TglAktifProteksi");                
                if (rsCekLimit.getString("TF").length() > 0) 
                    freqTF = Integer.valueOf(rsCekLimit.getString("TF"));      
            }
            
            if (isLimit || isProtekLimit) {
                System.out.println("Customer " + invTrans.getCustomer().getKodeCustomer() + " validation: isLimit " + isLimit + " isProtekLimit " + isProtekLimit);
                result = 0;
            }
            
            if (isWhitelist == false) {
                SimpleDateFormat formatQuery = new SimpleDateFormat("yyyy-MM-dd");
                Date now = formatQuery.parse(formatQuery.format(new Date()));
                
                if (tanggalAktifProteksi == null || tanggalAktifProteksi.compareTo(now) <= 0) {
                    try {
                        
                        String queryGetNotPaidInv = "SELECT TOP 1\n" +
                            "	tblFak.NoBukti, tblFak.Tgl, tblFak.Total, tblFak.Bayar\n" +
                            " FROM tblFak\n" +
                            " WHERE tblFak.KdKel = '" + invTrans.getCustomer().getKodeKelompok() + "'\n" +
                            "	AND tblFak.Whitelist = 0\n" +
                            "	AND (tblFak.Total - tblFak.Bayar) > 0";
                        
                        if (mKoneksi == null)
                            mKoneksi = new Koneksi();
                        
                        PreparedStatement pstGetNotPaidInv = mKoneksi.Koneksi().prepareStatement(queryGetNotPaidInv, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        ResultSet rsGetNotPaidInv = pstGetNotPaidInv.executeQuery();
                        rsGetNotPaidInv.last();
                        
                        int rowsInvNotPaid = rsGetNotPaidInv.getRow();
                        rsGetNotPaidInv.beforeFirst();
                        
                        if (rowsInvNotPaid > 0) {
                            while (rsGetNotPaidInv.next()) {
                                int freqInterval = 30 / freqTF;
                                // System.out.println("freqInterval " + freqInterval);
                                // System.out.println("freqTF " + freqTF);
                                
                                // 
                                // .....
                                // ......
                            }
                        } 
                        
                    } catch (Exception e) {
                        
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println("ERR isCustomerValid " + e.getMessage());
        }

        System.out.println("Validasi customer " + invTrans.getCustomer().getKodeCustomer() + " result " + result);
        return result;
    }
    

    
    
    // per item 
    private void setUnitItemAuthorize(ItemTransformModel item) {
        if (item == null)
            return;
        if (item.getKodeBarangLocal() == null)
            return;
        if (item.getKodeBarangLocal().isEmpty())
            return;
        
        item.setIsOtoHarga(getIsOtoHarga(item.getHargaJualMinLocal(), item.getHargaLocal(), item.getDiscount1Local(), item.getDiscount2Local(), item.getDiscount3Local()));
        item.setIsOtoHPP(getIsOtoHarga(item.getHargaPokokLocal(), item.getHargaLocal(), item.getDiscount1Local(), item.getDiscount2Local(), item.getDiscount3Local()));
    }
    
    private int getIsOtoHarga(double hargaJualMin, double harga, double discount1, double discount2, double discount3) {
        int result = 0;        
        double hargaJualBarang = harga * (1 - discount1/100) * (1 - discount2/100) * (1 - discount3/100);
        if (hargaJualBarang < hargaJualMin)
            result = 1;
        
        return result;        
    }
    
    private int getIsOtoHPP(double hargaPokok, double harga, double discount1, double discount2, double discount3) {
        int result = 0;
        
        double hargaJualBarang = harga * (1 - discount1/100) * (1 - discount2/100) * (1 - discount3/100);
        if (hargaJualBarang < hargaPokok)
            result = 1;
        
        return result;
    }
    
}
