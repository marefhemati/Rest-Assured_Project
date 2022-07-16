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

public class DeleteOneProduct {
	String firstProductId;
	
	HashMap<String, String> deletePlayload;
	public Map<String, String> deletePayloadMap(){
		
		deletePlayload = new HashMap<String, String>();
		deletePlayload.put("id", "4804");
		 
		return deletePlayload;
	}
	
	 @Test(priority=1)//here we use TestNG frame work and add @Test
	public void deleteOneProduct() { 
		 Response response =
		 
		 given() 
		 	.baseUri("https://techfios.com/api-prod/api/product")
		 	.header("Content-Type", "application/json; charset=UTF-8")
//		 	.header("","")
//		 	.body(new File("src\\main\\resources\\data\\CreateProductPayload.json")) //here in body method we selec file therefore we create a json file in src/main/resources folder and we copied the path here
		 	.body(deletePayloadMap())
		 	.relaxedHTTPSValidation(). //relaxHTTPSValidation is for if our baseUri is http or https it takes both
		
		 when()
		 	.delete("/delete.php").
		 then()
		 	.extract().response();
		int actualStatusCode = response.getStatusCode(); //here we save getStatusCode() method in variable and return type is int we save it in int
		Assert.assertEquals(actualStatusCode, 200); // here we assert
		String actualHeader = response.getHeader("Content-Type");
		Assert.assertEquals(actualHeader, "application/json; charset=UTF-8");
		
		
		String actualReponseBody = response.getBody().asString(); // here we assert or validate the body and getBody() return type was ResponseBody and because we didn't have ResponseBody variable therefore we change at last asString()
		System.out.println("Actual Response Body "+actualReponseBody);
		
		//Because responseBody language is Json therefore first we need to change to json like below. that why we have dependencies in pom.xml
		JsonPath jp = new JsonPath(actualReponseBody);
		
		String productMessage = jp.get("message"); //here validate Message of product
		System.out.println("Product Message "+productMessage);
		Assert.assertEquals(productMessage, "Product was deleted.");
	}
	 
	 @Test(priority=2)
	 public void validateProductDeleteDetails() { 
		 Response response =  //we added Response interface and it has all methods and the space  Response until semi-column is one. spaces ignore by java
		 
		 given() //in given section we automate according line 10 as below and we seperate because in one line we will be to long
		 	.baseUri("https://techfios.com/api-prod/api/product")
		 	.header("Content-Type", "application/json")
//		 	.header("","")
		 	.queryParam("id", deletePayloadMap().get("id"))
		 	.relaxedHTTPSValidation(). //relaxHTTPSValidation is if our baseUri is http or https it takes both
		
		 when()
		 	.get("read_one.php").
		 then()
		 	.extract().response();
		int actualStatusCode = response.getStatusCode(); //here we save getStatusCode() method in variable and return type is int we save it in int
		Assert.assertEquals(actualStatusCode, 404); // here we assert
		
		String actualReponseBody = response.getBody().asString(); // here we assert or validate the body and getBody() return type was ResponseBody and because we didn't have ResponseBody variable therefore we change at last asString()
		System.out.println("Actual Response Body "+actualReponseBody);
		
		//Because responseBody language is Json therefore first we need to change to json like below. that why we have dependencies in pom.xml
		JsonPath jp = new JsonPath(actualReponseBody);
		
		String actualProductMessage = jp.get("message"); //here validate name of product
		System.out.println("Actual Product Message "+actualProductMessage);
		Assert.assertEquals(actualProductMessage, "Product does not exist.");
		

	}
	

}
