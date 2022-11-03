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
public class StatusOrderCOModel {
    public char isStatusTrue;
    public String dateStatus;
    
    public StatusOrderCOModel(char isStatusTrue, String date) {
        this.isStatusTrue = isStatusTrue;
        this.dateStatus = date;
    }
}
