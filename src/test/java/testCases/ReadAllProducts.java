package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*; // in order to get access to to methods we made in static and by star you can access to all give, when and etc.

import java.util.concurrent.TimeUnit;

public class ReadAllProducts {
	/*
	given: all input details(baseURI,Headers,Payload/Body,QueryParameters,Authorization)
	when:  submit api requests(Http method,Endpoint/Resource)
	then:  validate response(status code, Headers, responseTime, Payload/Body)
	*/
//In below we automate the postman manual and we are using above BDD structure for API automation
	/*
	01. ReadAllProducts
	HTTP Method: GET
	EndpointUrl: https://techfios.com/api-prod/api/product/read.php
	Authorization:
	Basic Auth/ Bearer Token
	Header: "Content-Type" : "application/json; charset=UTF-8"
	Status Code: 200
	*/
	
	 @Test//here we use TestNG frame work and add @Test
	public void readAllProducts() { 
		 Response response =  //we added Response interface and it has all methods and the space  Response until semi-column is one. spaces ignore by java
		 
		 given() //in given section we automate according line 10 as below and we seperate because in one line we will be to long
		 	.baseUri("https://techfios.com/api-prod/api/product")
		 	.header("Content-Type", "application/json; charset=UTF-8")
		 	.auth().preemptive().basic("demo@techfios.com", "abc123")
		 	.relaxedHTTPSValidation(). //relaxHTTPSValidation is if our baseUri is http or https it takes both
		 when()
		 	.get("/read.php").
		 then()
		 	.extract().response();
		int actualStatusCode = response.getStatusCode(); //here we save getStatusCode() method in variable and return type is int we save it in int
		Assert.assertEquals(actualStatusCode, 200); // here we assert
		String actualHeader = response.getHeader("Content-Type");
		Assert.assertEquals(actualHeader, "application/json; charset=UTF-8");
		long actualResponseTime = response.getTimeIn(TimeUnit.MILLISECONDS); //here to verify the time of save it in long variable
		System.out.println("Actual Response Time " + actualResponseTime);
		
		if(actualResponseTime <=2000) { //the time of is different every time therefore we use if els
			System.out.println("Response time is within range");
		}else {
			System.out.println("Respnonse time is not within range");
		}
		
		String actualReponseBody = response.getBody().asString(); // here we assert or validate the body and getBody() return type was ResponseBody and because we didn't have ResponseBody variable therefore we change at last asString()
		
		System.out.println("Actual Response Body "+actualReponseBody);
		
		//Because responseBody language is Json therefore first we need to change to json like below. that why we have dependencies in pom.xml
		JsonPath jp = new JsonPath(actualReponseBody);
		String firstProductId = jp.get("records[0].id");
		System.out.println("First Product ID "+firstProductId);
		if(firstProductId != null) {
			System.out.println("First Product Id is not null");
			
		}else {
			System.out.println("First Product Id is null!!!");
		}
		

	}
	
	
	

}
