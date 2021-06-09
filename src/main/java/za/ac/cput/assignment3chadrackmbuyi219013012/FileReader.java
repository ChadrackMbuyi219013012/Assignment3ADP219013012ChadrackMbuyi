/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput.assignment3chadrackmbuyi219013012;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Chadrack Mbuyi 219013012
 */
public class FileReader {

    ObjectInputStream output;
    ArrayList<Customer> customerList = new ArrayList<Customer>();
    ArrayList<Supplier> supplierList = new ArrayList<Supplier>();
    PrintWriter writerObject;

    public void openFile() {
        try {
            output = new ObjectInputStream(new FileInputStream("stakeholder.ser"));
        } catch (IOException ioException) {
            System.out.println("Failed to open the ser file: " + ioException.getMessage());
        }
    }

    public void readFile() {
        for (int o = 0; o < 11; ++o) {
            try {
                Object object = output.readObject();
                if (object.getClass().equals(Customer.class)) {
                    customerList.add((Customer) object);
                } else {
                    supplierList.add((Supplier) object);
                }
            } catch (IOException a) {
                System.out.println("Failed to read from file: " + a.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("An Error Occurred: " + e.getMessage());
            }
        }
        System.out.println("Customer before sorting");
        System.out.println("=======================");
        
        for (Customer customer : customerList) {
            System.out.println(customer);
        }
        sortCust();
        System.out.println("Customer " + customerList.get(0).getFirstName() + "'s age is " + getCustAge(customerList.get(0)));
        System.out.println("Customer " + customerList.get(0).getFirstName() + "'s Date of birth " + formatDOB(customerList.get(0)));

        System.out.println("Customer after sorting");
        System.out.println("=======================");
        for (Customer customer : customerList) {
            System.out.println(customer);
        }
        System.out.println("Supplier before sorting");
        System.out.println("=======================");
        for (Supplier supplier : supplierList) {
            System.out.println(supplier);
        }
        sortSupp();
        //System.out.println("The Supplier" + supplierList.get(0).getName() + "'s product type is " + supplierList.get(0).getProductType());
        //System.out.println("The Supplier" + supplierList.get(0).getName() + "'s product description is " + getProductDescription(supplierList.get(0)));

        System.out.println("Supplier after sorting");
        System.out.println("=======================");
        for (Supplier supplier : supplierList) {
            System.out.println(supplier);
        }

    }

    public void sortCust() {
        Customer tempCust;
        Customer[] customers = new Customer[customerList.size()];
        for (int k = 0; k < customerList.size(); k++) {
            customers[k] = customerList.get(k);
        }
        for (int i = 0; i < customers.length - 1; i++) {
            for (int j = i + 1; j < customers.length; j++) {
                if (customers[i].getStHolderId().compareTo(customers[j].getStHolderId()) > 0) {
                    tempCust = customers[i];
                    customers[i] = customers[j];
                    customers[j] = tempCust;
                }
            }
        }
        customerList.clear();
        customerList.addAll(Arrays.asList(customers));
    }

    public void sortSupp() {
        Supplier tempSupp;
        Supplier[] suppliers = new Supplier[supplierList.size()];
        for (int a = 0; a < supplierList.size(); a++) {
            suppliers[a] = supplierList.get(a);
        }
        for (int i = 0; i < suppliers.length - 1; i++) {
            for (int j = i + 1; j < suppliers.length; j++) {
                if (suppliers[i].getName().compareTo(suppliers[j].getName()) > 0) {
                    tempSupp = suppliers[i];
                    suppliers[i] = suppliers[j];
                    suppliers[j] = tempSupp;
                }
            }
        }
        supplierList.clear();
        supplierList.addAll(Arrays.asList(suppliers));
    }

    public int getCustAge(Customer customer) {
        LocalDate dateOfBirth = LocalDate.parse(customer.getDateOfBirth());
        LocalDate currDate = LocalDate.now();
        int custAge = Period.between(dateOfBirth, currDate).getYears();
        return custAge;

    }

    public String formatDOB(Customer customer) {
        LocalDate localDate = LocalDate.parse(customer.getDateOfBirth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String stringDate = localDate.format(formatter);
        return stringDate;
    }

    public void closeFile() {
        try {
            output.close();
        } catch (IOException ioException) {
            System.out.println("Failed to close the file: " + ioException.getMessage());
        }
    }

    public void openFileToWriteTo() {
        try {
            writerObject = new PrintWriter(new FileOutputStream("customerOutFile.txt"));
            System.out.println("The file has been opened and written successfully");
        } catch (FileNotFoundException e) {
            System.out.println("Failed to write to the file: " + e.getMessage());
        }
    }

    public void writeCustomerToFile() {
        int count = 0;
        int cannotRentCount = 0;

        writerObject.println("====================CUSTOMERS====================" + '\n'
                + "ID  \t Name  \t Surname  \tDate of Birth \tAge" + '\n'
                + "=================================================");
        for (int i = 0; i < customerList.size(); ++i) {
            writerObject.print(
                    customerList.get(i).getStHolderId() + '\t'
                    + customerList.get(i).getFirstName() + '\t'
                    + customerList.get(i).getSurName() + '\t'
                    + formatDOB(customerList.get(i)) + '\t'
                    + getCustAge(customerList.get(i)) + '\n' + '\n'
            );
            if (customerList.get(i).getCanRent()) {
                count = count + 1;
            } else {
                cannotRentCount = cannotRentCount + 1;
            }

        }

        writerObject.print("Number of customers who can rent:" + count + '\n');
        writerObject.print("Number of customers who cannot rent:" + cannotRentCount + '\n');
    }

    public void closeWriter() {

        writerObject.close();
    }

    public void openFileToWriteToAgain() {
        try {
            writerObject = new PrintWriter(new FileOutputStream("supplierOutFile.txt"));
            System.out.println("The file supplier has been opened and written successfully");
        } catch (FileNotFoundException e) {
            System.out.println("Failed to write to the file: " + e.getMessage());
        }
    }

    public void writeSupplierToFile() {

        writerObject.println("=======================SUPPLIERS======================" + '\n'
                + "ID   \t Name     \t \t Prod Type     \tDescription" + '\n'
                + "======================================================");
        for (int i = 0; i < supplierList.size(); ++i) {
            writerObject.print(
                    supplierList.get(i).getStHolderId() + '\t'
                    + supplierList.get(i).getName() + '\t' + '\t'
                    + supplierList.get(i).getProductType() + '\t' + '\t'
                    + supplierList.get(i).getProductDescription() + '\n'
            );
        }
    }

    public void closeWriterAgain() {

        writerObject.close();
    }

}
