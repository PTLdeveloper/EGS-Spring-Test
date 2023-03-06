package csvreaderservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import customermanager.CustomerManager;
import datamodel.Customer;

class RunTests {

	@Test
	void TestCSVMapper() throws Exception {

		CSVRecord row = mock(CSVRecord.class);
		when(row.get(0)).thenReturn("Ref");
		when(row.get(1)).thenReturn("Name");
		when(row.get(2)).thenReturn("Address 1");
		when(row.get(3)).thenReturn("Address 2");
		when(row.get(4)).thenReturn("Town");
		when(row.get(5)).thenReturn("County");
		when(row.get(6)).thenReturn("Country");
		when(row.get(7)).thenReturn("Postcode");

		CustomerManager customerManager = new CustomerManager();
		Customer customer = customerManager.buildCustomer(row);

		assertEquals(customer.getCustomerRef(), "Ref");
		assertEquals(customer.getName(), "Name");
		assertEquals(customer.getAddressLine1(), "Address 1");
		assertEquals(customer.getAddressLine2(), "Address 2");
		assertEquals(customer.getTown(), "Town");
		assertEquals(customer.getCounty(), "County");
		assertEquals(customer.getCountry(), "Country");
		assertEquals(customer.getPostcode(), "Postcode");
	}

	@Test
	void TestCustomerMapping() throws JsonMappingException, JsonProcessingException {

		String json = "{\"customerRef\":\"cust1\",\"name\":\"Sam Sun\",\"addressLine1\":\"1, Main Street\",\"addressLine2\":\"Zebra Building\",\"town\":\"Sanquhar\",\"county\":\"Galloway\",\"country\":\"United Kingdom\",\"postcode\":\"A1 2BC\"}";

		ObjectMapper objectMapper = new ObjectMapper();
		Customer customer = objectMapper.readValue(json, Customer.class);

		assertThat(customer.getName()).isEqualTo("Sam Sun");
		assertThat(customer.getTown()).isEqualTo("Sanquhar");
	}

}
