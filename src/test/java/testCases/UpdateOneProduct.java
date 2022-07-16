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

public class UpdateOneProduct {
	String firstProductId;
	
	HashMap<String, String> updatePlayload;
	public Map<String, String> updatePayloadMap(){
		
		updatePlayload = new HashMap<String, String>();
		updatePlayload.put("id", "4815");
		updatePlayload.put("name", "Amazing Pillow 4.0 By Aref Hemat");
		updatePlayload.put("price", "300");
		updatePlayload.put("description", "The Top pillow for amazing programmers.");
		updatePlayload.put("category_id", "2");
		updatePlayload.put("category_name", "Eletronics");
		 
		return updatePlayload;
	}
	
	 @Test(priority=1)//here we use TestNG frame work and add @Test
	public void updateOneProduct() { 
		 Response response =
		 
		 given() 
		 	.baseUri("https://techfios.com/api-prod/api/product")
		 	.header("Content-Type", "application/json; charset=UTF-8")
//		 	.header("","")
//		 	.body(new File("src\\main\\resources\\data\\CreateProductPayload.json")) //here in body method we selec file therefore we create a json file in src/main/resources folder and we copied the path here
		 	.body(updatePayloadMap())
		 	.relaxedHTTPSValidation(). //relaxHTTPSValidation is for if our baseUri is http or https it takes both
		
		 when()
		 	.put("/update.php").
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
		Assert.assertEquals(productMessage, "Product was created.");
	}
	 
	 @Test(priority=2)
	 public void validateProductUpdateDetails() { 
		 Response response =  //we added Response interface and it has all methods and the space  Response until semi-column is one. spaces ignore by java
		 
		 given() //in given section we automate according line 10 as below and we seperate because in one line we will be to long
		 	.baseUri("https://techfios.com/api-prod/api/product")
		 	.header("Content-Type", "application/json")
//		 	.header("","")
		 	.queryParam("id", updatePayloadMap().get("id"))
		 	.relaxedHTTPSValidation(). //relaxHTTPSValidation is if our baseUri is http or https it takes both
		
		 when()
		 	.get("read_one.php").
		 then()
		 	.extract().response();
		int actualStatusCode = response.getStatusCode(); //here we save getStatusCode() method in variable and return type is int we save it in int
		Assert.assertEquals(actualStatusCode, 200); // here we assert
		
		String actualReponseBody = response.getBody().asString(); // here we assert or validate the body and getBody() return type was ResponseBody and because we didn't have ResponseBody variable therefore we change at last asString()
		System.out.println("Actual Response Body "+actualReponseBody);
		
		//Because responseBody language is Json therefore first we need to change to json like below. that why we have dependencies in pom.xml
		JsonPath jp = new JsonPath(actualReponseBody);
		
		String expectedProductName = updatePayloadMap().get("name");
		String expectedProductPrice = updatePayloadMap().get("price");
		
		String actualProductName = jp.get("name"); //here validate name of product
		System.out.println("Actual Product Name "+actualProductName);
		Assert.assertEquals(actualProductName, expectedProductName);
		
		String actualProductPrice = jp.get("price"); //here validate price of product
		System.out.println("Actual Product Price "+actualProductPrice);
		Assert.assertEquals(actualProductPrice, expectedProductPrice);

	}
	

}
