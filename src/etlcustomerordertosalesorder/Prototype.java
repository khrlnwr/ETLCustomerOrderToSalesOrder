/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etlcustomerordertosalesorder;

/**
 *
 * @author Yusuf Fahruddin
 */
public class Prototype {
    
}



//public class MyClass {
//    public static void main(String args[]) {
//      int x=10;
//      int y=25;
//      int z=x+y;
//
//      System.out.println("Sum of x+y = " + z);
//    }
//    
//    
//    
//    // STEP 1: GET INFO CUST TO GET BUYER ID2, KDKEL, KDCUST, and others
//    public String getNmCust(String Buyerid2, String businessname){
//        
//        String NmCust = "";
//        String sql = "SELECT * FROM tblCustomer WHERE BuyerID2='"+Buyerid2+"'";
//        
//        try {
//            conn = new Koneksi();
//            Statement st = conn.koneksi().createStatement();
//            ResultSet rs = st.executeQuery(sql);
//            
//            while (rs.next()) {
//                NmCust = rs.getString("NmCust");
//                System.out.println("---NMCUST----"+NmCust);
//            }
//            if( NmCust.equals("")){
//                NmCust = businessname;
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ListCOInvControl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return NmCust;
//    }
//
//    
//    public void getinfocust(CustomerOrder tampung) {
//        String sql = "SELECT c.kdkel,c.kdcust,c.nmcust,sp.KdGd,sp.KdSales from tblcustomer c \n"
//                + "LEFT JOIN tblSalesPerson sp on sp.KdSales = c.KdSales\n"
//                + "WHERE BuyerID2 = '" + tampung.getBuyerid2() + "'";
//        System.out.println(sql);
//        try {
//            konek = new Koneksi();
//            Statement st = konek.koneksi().createStatement();
//            ResultSet rs = st.executeQuery(sql);
//            System.out.println("size" + rs.getFetchSize());
//            while (rs.next()) {
//                kdgdco = rs.getString("KdGd");
//                kdsalesco = rs.getString("KdSales");
//                nmcustco = rs.getString("nmcust");
//                kdcustco = rs.getString("kdcust");
//                kdkelco = rs.getString("kdkel");
//                countInfoCust++;
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ListCOInvControl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    public void getInfoAlamatDariServerLocal(String kodeCustomer) {
//        // get informasi alamat dari server local
//    }
//    
//    
//    public void getInfoKodeBarangLocal() {
//        // get informasi barang berdasarkan info kode barang CO dari cloud
//        
//    }
//    
//    public void getInformasiJenisTax(String nomorInvoice) {
//        // get jenis pajak dari substring nomor invoice
//    }
//    
//    
//    public void getInformasiSales(String kodeCustomer) {
//        // get from kode customer or buyer_id2
//    }
//    
//    public void getInformasiGudang(String kodeCustomer) {
//        // get from kode customer or buyer_id2
//    }
//    
//    public void getInformasiKodeKel(String kodeCustomer) {
//        // get from kode customer or buyer id2
//    }
//    
//    
//    // STEP 2: Linking items in both of DB
//    public void isItemInInvoiceExist(String nomorInvoice) {
//        // iterate to check if all items in invoice exist on server local
//    }
//    
//    
//    // STEP 3: Validasi data customer 
//    public void SelectCustomerSO() {
//        // full example in FakturPenjualanCOControl.java
//        
//    }
//    
//    
//    
//}