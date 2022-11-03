/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import etlcustomerordertosalesorder.models.InvoiceCOModel;
import etlcustomerordertosalesorder.models.InvoiceTransformModel;
import java.util.ArrayList;

/**
 *
 * @author Yusuf Fahruddin
 */
public class Repository {
    private static ArrayList<InvoiceCOModel> listInvoiceCustomerOrder;
    private static ArrayList<InvoiceTransformModel> listInvoiceTransform; 

    public static ArrayList<InvoiceCOModel> getListInvoiceCustomerOrder() {
        return listInvoiceCustomerOrder;
    }

    public static void setListInvoiceCustomerOrder(ArrayList<InvoiceCOModel> listInvoiceCustomerOrder) {
        Repository.listInvoiceCustomerOrder = listInvoiceCustomerOrder;
    }

    public static ArrayList<InvoiceTransformModel> getListInvoiceTransform() {
        return listInvoiceTransform;
    }

    public static void setListInvoiceTransform(ArrayList<InvoiceTransformModel> listInvoiceTransform) {
        Repository.listInvoiceTransform = listInvoiceTransform;
    }
    
    
    
}
