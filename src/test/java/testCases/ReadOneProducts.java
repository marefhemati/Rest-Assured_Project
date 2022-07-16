package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*; // in order to get access to to methods we made in static and by star you can access to all give, when and etc.

import java.util.concurrent.TimeUnit;

public class ReadOneProducts {
	/*
	given: all input details(baseURI,Headers,Payload/Body,Authorization, QueryParameters)
	when:  submit api requests(Http method,Endpoint/Resource)
	then:  validate response(status code, Headers, responseTime, Payload/Body)
	*/
//In below we automate the postman manual and we are using above BDD structure for API automation
	
	/*
  02.ReadOneProduct 
	HTTP Method: GET
	EndpointUrl: https://techfios.com/api-prod/api/product/read_one.php
	Authorization:
	Basic Auth/ Bearer Token
	Header:
	"Content-Type" : "application/json"
	QueryParam: 
	"id":"value"
	Status Code: 200
	 */
	
	 @Test//here we use TestNG frame work and add @Test
	public void readOneProducts() { 
		 Response response =  //we added Response interface and it has all methods and the space  Response until semi-column is one. spaces ignore by java
		 
		 given() //in given section we automate according line 10 as below and we seperate because in one line we will be to long
		 	.baseUri("https://techfios.com/api-prod/api/product")
		 	.header("Content-Type", "application/json")
//		 	.header("","")
		 	.queryParam("id", "4635")
		 	.relaxedHTTPSValidation(). //relaxHTTPSValidation is if our baseUri is http or https it takes both
		
		 when()
		 	.get("read_one.php").
		 then()
		 	.extract().response();
		int actualStatusCode = response.getStatusCode(); //here we save getStatusCode() method in variable and return type is int we save it in int
		Assert.assertEquals(actualStatusCode, 200); // here we assert
		String actualHeader = response.getHeader("Content-Type");
		Assert.assertEquals(actualHeader, "application/json");
		
		
		String actualReponseBody = response.getBody().asString(); // here we assert or validate the body and getBody() return type was ResponseBody and because we didn't have ResponseBody variable therefore we change at last asString()
		System.out.println("Actual Response Body "+actualReponseBody);
		
		//Because responseBody language is Json therefore first we need to change to json like below. that why we have dependencies in pom.xml
		JsonPath jp = new JsonPath(actualReponseBody);
		
		String productId = jp.get("id"); //here validate id of product
		System.out.println("Product ID "+productId);
		Assert.assertEquals(productId, "4635");
		
		String productName = jp.get("name"); //here validate name of product
		System.out.println("Product Name "+productName);
		Assert.assertEquals(productName, "Sk456s Amazing Pillow 2.0");
		
		String productDescription = jp.get("description"); //here validate description of product
		System.out.println("Product Description "+productDescription);
		Assert.assertEquals(productDescription, "The best pillow for amazing programmers.");
		
		String productPrice = jp.get("price"); //here validate price of product
		System.out.println("Product Price "+productPrice);
		Assert.assertEquals(productPrice, "199");

	}
	
	
	

}
