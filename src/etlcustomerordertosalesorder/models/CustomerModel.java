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
public class CustomerModel {
    private String kodeCustomer;
    private String namaCustomer;
    private String kodeKelompok;
    private String alamatPertama;
    private String alamatKedua;
    private String alamatKetiga;
    private String kodeSales;
    private String kodeGudang;    

    private int isCustomerValid;

    public CustomerModel() {
        this.isCustomerValid = 0;
    }
    
    public int getIsCustomerValid() {
        return isCustomerValid;
    }

    public void setIsCustomerValid(int isCustomerValid) {
        this.isCustomerValid = isCustomerValid;
    }
        
    public String getKodeCustomer() {
        return kodeCustomer;
    }

    public void setKodeCustomer(String kodeCustomer) {
        this.kodeCustomer = kodeCustomer;
    }

    public String getNamaCustomer() {
        return namaCustomer;
    }

    public void setNamaCustomer(String namaCustomer) {
        this.namaCustomer = namaCustomer;
    }

    public String getKodeKelompok() {
        return kodeKelompok;
    }

    public void setKodeKelompok(String kodeKelompok) {
        this.kodeKelompok = kodeKelompok;
    }

    public String getAlamatPertama() {
        return alamatPertama;
    }

    public void setAlamatPertama(String alamatPertama) {
        this.alamatPertama = alamatPertama;
    }

    public String getAlamatKedua() {
        return alamatKedua;
    }

    public void setAlamatKedua(String alamatKedua) {
        this.alamatKedua = alamatKedua;
    }

    public String getAlamatKetiga() {
        return alamatKetiga;
    }

    public void setAlamatKetiga(String alamatKetiga) {
        this.alamatKetiga = alamatKetiga;
    }

    public String getKodeSales() {
        return kodeSales;
    }

    public void setKodeSales(String kodeSales) {
        this.kodeSales = kodeSales;
    }

    public String getKodeGudang() {
        return kodeGudang;
    }

    public void setKodeGudang(String kodeGudang) {
        
        this.kodeGudang = kodeGudang;
    }
    
    
    
}
