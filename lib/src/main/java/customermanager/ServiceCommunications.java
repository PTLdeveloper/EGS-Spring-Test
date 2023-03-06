package customermanager;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import datamodel.Customer;

public class ServiceCommunications {

	static final String GET_URL = "http://localhost:8080/getCustomer/{customerRef}";
	static final String POST_URL = "http://localhost:8080/insertCustomer";

	private static final Logger logger = LoggerFactory.getLogger(CustomerManager.class);

	public ServiceCommunications() {
	}

	boolean sendCustomer(Customer customer) {

		logger.info("Starting sending the request to {}", POST_URL);

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Converting the Object to JSONString
		ObjectMapper mapper = new ObjectMapper();
		try {
			String customerJsonString = mapper.writeValueAsString(customer);

			HttpEntity<String> request = new HttpEntity<String>(customerJsonString, headers);

			// Calling the service
			restTemplate.postForObject(POST_URL, request, String.class);

		} catch (JsonProcessingException e) {
			logger.error("There was an error sending the service request", e);
			return false;
		}

		logger.info("Finished sending the request to {} successfully", POST_URL);
		return true;
	}

	Customer retrieveCustomer(String customerRef) {

		logger.info("Starting sending the request to {}", GET_URL);

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Converting the Object to JSONString
		ObjectMapper mapper = new ObjectMapper();
		try {

			String result = restTemplate.getForObject(GET_URL, String.class, Map.of("customerRef", customerRef));

			logger.info("Finished sending the request to {} successfully", GET_URL);

			// Mapping the result
			if (result == null) {
				System.out.println("There is no customer with reference " + customerRef + " in the database.");
				return null;
			} else {
				JsonNode root = mapper.readTree(result);
				return mapper.readValue(root.toString(), Customer.class);
			}

		} catch (JsonProcessingException e) {
			logger.error("There was an error sending the service request", e);
			return null;
		}

	}
}
