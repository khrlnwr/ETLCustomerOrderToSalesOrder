/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etlcustomerordertosalesorder;

import etlcustomerordertosalesorder.models.InvoiceCOModel;
import etlcustomerordertosalesorder.models.InvoiceTransformModel;
import etlcustomerordertosalesorder.models.ItemInvoiceCOModel;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.SwingWorker;
import utils.Repository;

/**
 *
 * @author Yusuf Fahruddin
 */
public class ETLCustomerOrderToSalesOrder {

    private Extraction extraction;
    private Transformation transformation;

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("START ETLCustomerOrderToSalesOrder ...");
        final long startTime = System.currentTimeMillis();
        
        ETLCustomerOrderToSalesOrder instance = new ETLCustomerOrderToSalesOrder();
        
        try {
            instance.getInvoices();
            instance.setInvoiceTransformInstance();
            instance.transformStep1();
            instance.transformStep2();
            instance.transformStep3();
            instance.saveLogHeader();

        } catch (ParseException ex) {
            System.out.println("ERR getInvoiceHeader() " + ex.getMessage());
        }
        
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + " ms");        
    }
    
    public ETLCustomerOrderToSalesOrder() {
        extraction = new Extraction();
        transformation = new Transformation();
                
        extraction.setIdSeller("e377a4d7a784");
        extraction.setUrlAWS("http://masuya1.freeddns.org:91/cobaapp2/customer_order_aws/");
    }
    
    private void getInvoices() throws ParseException {        

        System.out.println("get header invoice ...");
        while (true) {
            Repository.setListInvoiceCustomerOrder(extraction.getInvoiceHeader());
            if (Repository.getListInvoiceCustomerOrder() != null)
                break;
        }

        System.out.println("get detail invoice ...");
        extraction.getInvoiceDetails();
    }
    
    
    private void setInvoiceTransformInstance() {
        
        if (Repository.getListInvoiceCustomerOrder() == null)
            return;        
        if (Repository.getListInvoiceCustomerOrder().isEmpty())
            return;
        
        ArrayList<InvoiceTransformModel> listInvoiceTransform = new ArrayList();
        
        for (InvoiceCOModel inv : Repository.getListInvoiceCustomerOrder()) {            
            InvoiceTransformModel instance = new InvoiceTransformModel();
                        
            instance.setNomorOrder(inv.getNoBukti());
            instance.setIdBuyerCustomerOrder(inv.getIdBuyer());            
            instance.setPpnTotal(inv.getPpn());
            instance.setSubTotal(inv.getSubTotal());            
            instance.setTotal(inv.getTotal());
            instance.setListItem(inv.getListItem());
                                    
            listInvoiceTransform.add(instance);
        }
        
        Repository.setListInvoiceTransform(listInvoiceTransform);
    }
    
    
    private void transformStep1() {
        
        System.out.println("TRANSFORMATION STEP 1 ");        
        if (Repository.getListInvoiceTransform() == null)
            return;        
        if (Repository.getListInvoiceTransform().isEmpty()) 
            return;
        
        transformation.getCustomerInvoiceLinked();
        transformation.getIsItemsLinked();
        
    }
    
    private void transformStep2() {
        System.out.println("TRANSFORMATION STEP 2 ");        
        if (Repository.getListInvoiceTransform() == null)
            return;        
        if (Repository.getListInvoiceTransform().isEmpty()) 
            return;

        transformation.getPajakItem();
        transformation.getPriceDiscItems();
    }
     
    
    private void transformStep3() {
        System.out.println("TRANSFORMATION STEP 3 ");        
        if (Repository.getListInvoiceTransform() == null)
            return;        
        if (Repository.getListInvoiceTransform().isEmpty()) 
            return;
                
        transformation.getCustomerValidation();
        transformation.getIsStockAvailable();
    }
    
    private void transformStep4() {
        
        System.out.println("TRANSFORMATION STEP 4 ");
        if (Repository.getListInvoiceTransform() == null)
            return;        
        if (Repository.getListInvoiceTransform().isEmpty()) 
            return;
        
        
    }
    
    private void saveLogHeader() {
        
        if (Repository.getListInvoiceTransform() == null)
            return;        
        if (Repository.getListInvoiceTransform().isEmpty()) 
            return;
        
        try {                      
            FileWriter fw = new FileWriter("logs/logs_24.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            
            for (InvoiceTransformModel inv : Repository.getListInvoiceTransform()) {
                
                String nomor = inv.getNomorOrder();
                String idBuyerCO = inv.getIdBuyerCustomerOrder();
                int jumlahItem = inv.getListItem().size();
                char isLinked = inv.getIsCustomerLinked();
                                        
                String kodeCustomer = "", kodeGudang = "", kodeKelompok = "", kodeSales = "";
                
                if (isLinked == 1 || isLinked == '1') {                
                    kodeCustomer = inv.getCustomer().getNamaCustomer();
                    kodeGudang = inv.getCustomer().getKodeGudang();
                    kodeKelompok = inv.getCustomer().getKodeKelompok();
                    kodeSales = inv.getCustomer().getKodeSales();                    
                }
                pw.println();
                pw.println(nomor + "," + idBuyerCO + "," + jumlahItem + "," + kodeCustomer + "," + kodeGudang + "," + kodeKelompok + "," + kodeSales);
                pw.println("Kode barang,nama barang,satuan,hargaCloud,ppn,is_item_linked,harga_jual_min,harga_local,harga_pokok,meter_kubik");

                for (ItemInvoiceCOModel item : inv.getListItem()) {
                    String kodeBarangCO = item.getKodeBarangCustomerOrder();
                    String namaBarangLocal = "";
                    String satuan = item.getSatuanCloud();
                    double hargaCloud = item.getHargaCloud();
                    double ppn = item.getPpn();
                    char isItemLinked = item.getIsLinked();
                    
                    double hargaJualMin = 0.0, harga = 0.0, hargaPokok = 0.0, meterKubik = 0.0;
                    
                    if (isItemLinked == 1 || isItemLinked == '1') {
                        namaBarangLocal = item.getLinkedItem().getNamaBarangLocal();
                        hargaJualMin = item.getLinkedItem().getHargaJualMinLocal();
                        harga = item.getLinkedItem().getHargaLocal();
                        hargaPokok = item.getLinkedItem().getHargaPokokLocal();
                        meterKubik = item.getLinkedItem().getMeterKubikLocal();
                    }
                    pw.println(kodeBarangCO + "," + Extraction.getReplacedComma(namaBarangLocal) + "," + satuan + "," + hargaCloud + "," + ppn + "," + isItemLinked + "," + hargaJualMin + "," + harga + "," + hargaPokok + "," + meterKubik);
                }
            }
            
            pw.flush();
            pw.close();

            System.out.println("SUCCESS SAVING LOGS");

        } catch (Exception e) {
            System.out.println("ERR SAVING LOGS: " + e.getMessage());
        }
    }
}
