package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*; // in order to get access to to methods we made in static and by star you can access to all give, when and etc.

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CreateOneProducts2 {
	String firstProductId;
	
	HashMap<String, String> createPlayload;
	public Map<String, String> createPayloadMap(){
		
		createPlayload = new HashMap<String, String>();
		createPlayload.put("name", "Amazing Pillow 2.0 By Aref Hemati");
		createPlayload.put("price", "199");
		createPlayload.put("description", "The Top pillow for amazing programmers.");
		createPlayload.put("category_id", "2");
		createPlayload.put("category_name", "Eletronics");
		 
		return createPlayload;
	}
	
	/*
  03.CreateOneProduct
HTTP Method: POST
EndpointUrl: https://techfios.com/api-prod/api/product/create.php
Authorization:
Basic Auth/ Bearer Token
Header:
"Content-Type" : "application/json; charset=UTF-8"
Status Code: 201
Payload/Body: 
{
    "name" : "Amazing Pillow 2.0 By Aref Hemati",
    "price" : "199",
    "description" : "The Top pillow for amazing programmers.",
    "category_id" : "2",
    "category_name"  : "Eletronics"

} 
*/
	
	 @Test(priority=1)//here we use TestNG frame work and add @Test
	public void createOneProducts() { 
		 
			 
		 Response response =
		 
		 given() 
		 	.baseUri("https://techfios.com/api-prod/api/product")
		 	.header("Content-Type", "application/json; charset=UTF-8")
//		 	.header("","")
//		 	.body(new File("src\\main\\resources\\data\\CreateProductPayload.json")) //here in body method we selec file therefore we create a json file in src/main/resources folder and we copied the path here
		 	.body(createPayloadMap())
		 	.relaxedHTTPSValidation(). //relaxHTTPSValidation is for if our baseUri is http or https it takes both
		
		 when()
		 	.post("create.php").
		 then()
		 	.extract().response();
		int actualStatusCode = response.getStatusCode(); //here we save getStatusCode() method in variable and return type is int we save it in int
		Assert.assertEquals(actualStatusCode, 201); // here we assert
		String actualHeader = response.getHeader("Content-Type");
		Assert.assertEquals(actualHeader, "application/json; charset=UTF-8");
		
		
		String actualReponseBody = response.getBody().asString(); // here we assert or validate the body and getBody() return type was ResponseBody and because we didn't have ResponseBody variable therefore we change at last asString()
		System.out.println("Actual Response Body "+actualReponseBody);
		
		//Because responseBody language is Json therefore first we need to change to json like below. that why we have dependencies in pom.xml
		JsonPath jp = new JsonPath(actualReponseBody);
		
		String productMessage = jp.get("message"); //here validate Message of product
		System.out.println("Product Message "+productMessage);
		Assert.assertEquals(productMessage, "Product was created.");
		
	}
	 @Test(priority=2)
	 public void getFirstProductId() { 
		 Response response =  //we added Response interface and it has all methods and the space  Response until semi-column is one. spaces ignore by java
		 
		 given() //in given section we automate according line 10 as below and we seperate because in one line we will be to long
		 	.baseUri("https://techfios.com/api-prod/api/product")
		 	.header("Content-Type", "application/json; charset=UTF-8")
		 	.auth().preemptive().basic("demo@techfios.com", "abc123")
		 	.relaxedHTTPSValidation(). //relaxHTTPSValidation is if our baseUri is http or https it takes both
		 when()
		 	.post("/read.php").
		 then()
		 	.extract().response();
		int actualStatusCode = response.getStatusCode(); //here we save getStatusCode() method in variable and return type is int we save it in int
		Assert.assertEquals(actualStatusCode, 200); // here we assert
		String actualReponseBody = response.getBody().asString();
		System.out.println("Actual Response Body "+actualStatusCode);
		//Because responseBody language is Json therefore first we need to change to json like below. that why we have dependencies in pom.xml
		JsonPath jp = new JsonPath(actualReponseBody);
		String firstProductId = jp.get("records[0].id");
		System.out.println("First Product ID "+firstProductId);

	}
	 @Test(priority=3)
	 public void validateProductDetails() { 
		 Response response =  //we added Response interface and it has all methods and the space  Response until semi-column is one. spaces ignore by java
		 
		 given() //in given section we automate according line 10 as below and we seperate because in one line we will be to long
		 	.baseUri("https://techfios.com/api-prod/api/product")
		 	.header("Content-Type", "application/json")
//		 	.header("","")
		 	.queryParam("id", "firstProductId")
		 	.relaxedHTTPSValidation(). //relaxHTTPSValidation is if our baseUri is http or https it takes both
		
		 when()
		 	.get("create.php").
		 then()
		 	.extract().response();
		int actualStatusCode = response.getStatusCode(); //here we save getStatusCode() method in variable and return type is int we save it in int
		Assert.assertEquals(actualStatusCode, 200); // here we assert
		
		
		String actualReponseBody = response.getBody().asString(); // here we assert or validate the body and getBody() return type was ResponseBody and because we didn't have ResponseBody variable therefore we change at last asString()
		System.out.println("Actual Response Body "+actualReponseBody);
		
		//Because responseBody language is Json therefore first we need to change to json like below. that why we have dependencies in pom.xml
		JsonPath jp = new JsonPath(actualReponseBody);
		
		String expectedProductName = createPayloadMap().get("name");
		String expectedProductDescription = createPayloadMap().get("description");
		String expectedProductPrice = createPayloadMap().get("price");
		
		String actualProductName = jp.get("name"); //here validate name of product
		System.out.println("Actual Product Name "+actualProductName);
		Assert.assertEquals(actualProductName, expectedProductName);
		
		String actualProductDescription = jp.get("description"); //here validate description of product
		System.out.println("Actual Product Description "+actualProductDescription);
		Assert.assertEquals(actualProductDescription, expectedProductDescription);
		
		String actualProductPrice = jp.get("price"); //here validate price of product
		System.out.println("Actual Product Price "+actualProductPrice);
		Assert.assertEquals(actualProductPrice, expectedProductPrice);

	}
	

}
