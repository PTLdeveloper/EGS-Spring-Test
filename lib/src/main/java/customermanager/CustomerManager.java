package customermanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import datamodel.Customer;

public class CustomerManager {

	private ServiceCommunications service;

	private static final Charset ENCODING = StandardCharsets.UTF_8;

	public CustomerManager() throws Exception {
		service = new ServiceCommunications();
	}

	public boolean parse(File csvFile) {

		try (FileInputStream fis = new FileInputStream(csvFile.getAbsolutePath());
				InputStreamReader isr = new InputStreamReader(fis, ENCODING);
				Reader in = new BufferedReader(isr);
				CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT.builder().setTrim(true).build())) {

			int curLine = 0;

			for (Iterator<CSVRecord> recordIterator = parser.iterator(); recordIterator.hasNext(); curLine++) {

				CSVRecord currentLine = recordIterator.next();

				// Skip the first row with the headers
				if (curLine == 0) {
					continue;
				}

				Customer customer = buildCustomer(currentLine);
				service.sendCustomer(customer);

			}

		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public Customer buildCustomer(CSVRecord row) {

		Customer customer = new Customer();

		customer.setCustomerRef(row.get(0));
		customer.setName(row.get(1));
		customer.setAddressLine1(row.get(2));
		customer.setAddressLine2(row.get(3));
		customer.setTown(row.get(4));
		customer.setCounty(row.get(5));
		customer.setCountry(row.get(6));
		customer.setPostcode(row.get(7));

		return customer;
	}

	private void processCsvFile() {
		System.out.print("Type the CSV full path: ");

		// Uncomment the next three lines if you want to type the location
//		InputStreamReader streamReader = new InputStreamReader(System.in);
//		BufferedReader bufferedReader = new BufferedReader(streamReader);
//		 String csvPath = bufferedReader.readLine();

		String csvPath = "src/main/resources/csvtest.csv"; // Test CSV file

		File csvFile = new File(csvPath);
		if (csvFile.exists()) {
			boolean result = parse(csvFile);

			if (result) {
				System.out.println("File " + csvPath + " processed successfully");
			} else {
				System.out.println("There was an error processing the file " + csvPath);
			}
		} else {
			System.out.println("The file " + csvPath + " doesn't exist");
		}
	}

	private void retrieveCustomer() throws IOException {
		System.out.print("Type the customer reference: ");

		InputStreamReader streamReader = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(streamReader);

		String customerRef = bufferedReader.readLine();
		Customer customerRetrieved = service.retrieveCustomer(customerRef);

		if (customerRetrieved != null) {
			System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
			System.out.println("Customer Ref.: " + customerRetrieved.getCustomerRef());
			System.out.println("- Name................ " + customerRetrieved.getName());
			System.out.println("- Address Line 1...... " + customerRetrieved.getAddressLine1());
			System.out.println("- Address Line 2...... " + customerRetrieved.getAddressLine2());
			System.out.println("- Town................ " + customerRetrieved.getTown());
			System.out.println("- County.............. " + customerRetrieved.getCounty());
			System.out.println("- Country............. " + customerRetrieved.getCountry());
			System.out.println("- Postcode............ " + customerRetrieved.getPostcode());
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		}
	}

	public static void main(String[] args) throws Exception {

		CustomerManager customerManager = new CustomerManager();

		boolean exit = false;

		while (!exit) {

			System.out.println("\n");
			System.out.println(" -------------------------------------");
			System.out.println(" | 1. Process CSV file               | ");
			System.out.println(" | 2. Retrieve customer by reference | ");
			System.out.println(" | 3. Exit                           | ");
			System.out.println(" -------------------------------------");
			System.out.print("   ==> Enter your option: ");

			InputStreamReader streamReader = new InputStreamReader(System.in);
			BufferedReader bufferedReader = new BufferedReader(streamReader);
			String option = bufferedReader.readLine();

			switch (option) {
			case "1":
				customerManager.processCsvFile();
				break;
			case "2":
				customerManager.retrieveCustomer();
				break;
			case "3":
				System.out.println("Bye!");
				exit = true;
				break;
			default:
				System.out.println("Invalid option. Please try again.");
			}

		}

	}

}
