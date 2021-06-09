/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput.assignment3chadrackmbuyi219013012;

/**
 *
 * @author Chadrack Mbuyi 219013012
 */
public class Run {
    
    public static void main(String[] args) 
    {
        FileReader read = new FileReader();
        
        read.openFile();
        read.readFile();
        read.closeFile();
        
        //read.sortCust();
        
        read.openFileToWriteTo();
        read.writeCustomerToFile();
        read.closeWriter();
        
        //read.openFileToWriteToAgain();
        //read.writeSupplierToFile();
        //read.closeWriterAgain();
    }
    
}
