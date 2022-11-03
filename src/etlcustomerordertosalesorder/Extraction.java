/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etlcustomerordertosalesorder;

import etlcustomerordertosalesorder.models.InvoiceCOModel;
import etlcustomerordertosalesorder.models.ItemInvoiceCOModel;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Repository;

/**
 *
 * @author Yusuf Fahruddin
 */
public class Extraction {
    
    private String idSeller;
    private String urlAWS;
    private final SimpleDateFormat formatQuery = new SimpleDateFormat("yyyy-MM-dd");
        
    private int LIMIT_MAX_INVOICE = 30;
    private ArrayList<InvoiceCOModel> listInvoice = new ArrayList();
    
    public void setUrlAWS(String url) {
        if (url == null)
            return;
        
        if (url.isEmpty())
            return;
        
        this.urlAWS = url;
    }
    
    public String getUrlAWS() { 
        return urlAWS; 
    }

    public String getIdSeller() {
        return idSeller;
    }

    public void setIdSeller(String idSeller) {
        this.idSeller = idSeller;
    }

    public ArrayList<InvoiceCOModel> getListInvoice() {
        return listInvoice;
    }

    public void setListInvoice(ArrayList<InvoiceCOModel> listInvoice) {
        this.listInvoice = listInvoice;
    }
        
    public ArrayList<InvoiceCOModel> getInvoiceHeader() throws ParseException {

        ArrayList<InvoiceCOModel> list = new ArrayList();
        
        if (this.idSeller == null)
            return list;

        if (this.idSeller.isEmpty())
            return list;
                    
        try {
            URL url = new URL(this.urlAWS + "sinkronasi/get_all_trax_header.php");            
            Map<String, Object> params = new LinkedHashMap<>();
            
            params.put("sellerid2", this.idSeller);            
            params.put("jenis", "OPEN");
            params.put("tglAwal", formatQuery.format(Calendar.getInstance().getFirstDayOfWeek()));
            params.put("tglAkhir", formatQuery.format(Calendar.getInstance().getTime()));
                        
            StringBuilder postData = new StringBuilder();
            
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData
                        .append(URLEncoder.encode(param.getKey(), "UTF-8"))
                        .append('=')
                        .append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            int msTimeout = 300; 
            
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            
            conn.setConnectTimeout(msTimeout);
            conn.setReadTimeout(msTimeout);
            
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);
            
            if (conn.getResponseCode() == 200) {
                
                StringBuilder informationstring = new StringBuilder();
                Scanner scanner = new Scanner(conn.getInputStream());
                while (scanner.hasNext()) {
                    informationstring.append(scanner.nextLine());
                }
                
                scanner.close();
                // System.out.println(informationstring);
                JSONObject jsonObject = new JSONObject(informationstring.toString());
                JSONArray array = jsonObject.getJSONArray("message");
                
                // System.out.println("message result " + array);
                // System.out.println("message result size: " + array.length());
                
                if (!array.isEmpty() && jsonObject.getInt("success") == 1) {
                    
                    for (int i=0; i<array.length(); i++) {
                        InvoiceCOModel model = new InvoiceCOModel();
                        
                        model.setNoBukti(array.getJSONObject(i).getString("NoBukti"));
                        model.setIdBuyer(array.getJSONObject(i).getString("BuyerID"));
                        model.setIdSeller(this.idSeller);

                        model.setBusinessName(array.getJSONObject(i).getString("BusinessName"));                        
                        model.setAddress(array.getJSONObject(i).getString("Alm1"));
                        model.setPhoneNumber(array.getJSONObject(i).getString("Alm3"));
                        model.setKodePos(array.getJSONObject(i).getString("PostalCode"));
                        
                        model.setPpn(Double.parseDouble(array.getJSONObject(i).getString("Ppn")));
                        model.setTotal(Double.parseDouble(array.getJSONObject(i).getString("Total")));
                        model.setSubTotal(Double.parseDouble(array.getJSONObject(i).getString("Subtotal")));
                        
                        Date date1=new SimpleDateFormat("dd-MM-yyyy").parse(array.getJSONObject(i).getString("Tgl"));
                        model.setTanggal(date1);
                        
                        list.add(model);      
                    } // end for
                }

                
                // System.out.println("List invoice header: " + list);                
            }
            
        } catch (MalformedURLException ex){
            System.out.println("message: " + ex.getMessage());
            return null;

        } catch (UnsupportedEncodingException ex) {
            System.out.println("message: " + ex.getMessage());
            return null;
        } catch (ProtocolException ex) {
            System.out.println("message: " + ex.getMessage());
            return null;
        } catch (IOException ex) {
            System.out.println("message: " + ex.getMessage());
            return null;
        }
        
        return list;        
    }
    
    public void getInvoiceDetails() {
        if (Repository.getListInvoiceCustomerOrder() == null)
            return;        
        if (Repository.getListInvoiceCustomerOrder().isEmpty()) 
            return;
        
        // MAP REDUCE: https://dhruv-saksena.medium.com/map-reduce-in-java-8-37f42dd8cca3
        int total = Repository.getListInvoiceCustomerOrder().parallelStream().reduce(0, (value, inv) -> {            
            ArrayList<ItemInvoiceCOModel> listItem = new ArrayList();            
            while (true) {
                listItem = getUnitInvoiceDetails(inv.getNoBukti());
                if (listItem != null)
                    break;
            }
            inv.setListItem(listItem);            
            return value + 1;             
        }, Integer :: sum);
    }
    
    public static String getReplacedComma(String raw) {
        return raw.replace(',', '.');
    }
    
    private ArrayList<ItemInvoiceCOModel> getUnitInvoiceDetails(String invoice) {
        
        ArrayList<ItemInvoiceCOModel> listItem = new ArrayList();
        
        if (invoice == null)
            return listItem;
        
        if (this.idSeller == null)
            return listItem;
        
        if (invoice.isEmpty() || this.idSeller.isEmpty())
            return listItem;
        
        try {
            URL url = new URL(this.urlAWS + "sinkronasi/get_detail_trax.php");
            
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("sellerid2", this.idSeller);
            params.put("noinvoice", invoice);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData
                        .append(URLEncoder.encode(param.getKey(), "UTF-8"))
                        .append('=')
                        .append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            int msTimeout = 300; // milliseconds
            
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            
            conn.setConnectTimeout(msTimeout);
            conn.setReadTimeout(msTimeout);
            
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);
                        
            if (conn.getResponseCode() == 200) {
                StringBuilder informationstring = new StringBuilder();
                Scanner scanner = new Scanner(conn.getInputStream());
                while (scanner.hasNext()) {
                    informationstring.append(scanner.nextLine());
                }
                scanner.close();

                JSONObject jsonObject = new JSONObject(informationstring.toString());
                JSONArray array = jsonObject.getJSONArray("message");

                if (jsonObject.getInt("success") == 1 && !array.isEmpty()) {
                    
                    for (int i=0; i<array.length(); i++) {
                        ItemInvoiceCOModel instance = new ItemInvoiceCOModel();
                        
                        instance.setNoBukti(invoice);
                        instance.setKodeBarangCustomerOrder(array.getJSONObject(i).getString("Kdbrg"));
                        instance.setIdBuyer(array.getJSONObject(i).getString("BuyerID2"));                        
                        instance.setLine(array.getJSONObject(i).getString("Line"));                        
                        instance.setIdSeller(array.getJSONObject(i).getString("SellerID2"));                        
                        instance.setSubTotal(Double.parseDouble(array.getJSONObject(i).getString("Subtotal")));
                        instance.setPpn(Double.parseDouble(array.getJSONObject(i).getString("Ppn")));                        
                        instance.setTotal(Double.parseDouble(array.getJSONObject(i).getString("Total")));                        
                        instance.setHargaCloud(Double.parseDouble(array.getJSONObject(i).getString("Hrg")));                        
                        instance.setNote(array.getJSONObject(i).getString("Note"));
                        instance.setSatuanCloud(array.getJSONObject(i).getString("Satuan"));
                        instance.setQtyOrderCloud(Double.parseDouble(array.getJSONObject(i).getString("Qty")));
                        
                        listItem.add(instance);
                    } // end for
                    
                }
            }
            
        } catch (MalformedURLException ex) {
            // System.out.println("ERR getUnitInvoiceDetails: " + ex.getMessage());
            return null;
        } catch (UnsupportedEncodingException ex) {
            // System.out.println("ERR getUnitInvoiceDetails: " + ex.getMessage());
            return null;
        } catch (IOException ex) {
            // System.out.println("ERR getUnitInvoiceDetails: " + ex.getMessage());
            return null;
        }
        
        return listItem;
    }
    
    
    
}
