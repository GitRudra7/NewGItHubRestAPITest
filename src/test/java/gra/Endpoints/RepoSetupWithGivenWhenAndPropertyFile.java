package gra.Endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import gra.Payloads.Repo;
import io.restassured.response.Response;

public class RepoSetupWithGivenWhenAndPropertyFile
{
	public static String BearerToken = "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";
	public static String BearerTokenAdmin = "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";	
	
	// method created for getting URL's from properties file
			static ResourceBundle getURL()
			{
				ResourceBundle routes= ResourceBundle.getBundle("UrlRoutes"); // Load properties file  // name of the properties file
				return routes;
			}
	
	public static Response CreateRepo(Repo payload) // payload is a variable with the type Repo class , also return type of this mthd is Response, as we r returning response
													// will pass payload (generated in test class using Repo class of payload) to this mthd .
	{
			String Post_url=getURL().getString("post_url");//*************chngs
			
	Response resp =	given()
		  .contentType("application/json")
		  .header("Authorization","Bearer "+BearerToken)
		  .body(payload)
		
		 .when()
		  
		   .post(Post_url);	//*************chngs
		
	    return resp; // this content of resp will be used in test class to continue with then() or Assert to do test validations 
				  	
	}
	
	
	public static Response GetRepo(String Reponame) // Value of Reponame varb. will be passed to this method from Testdata excel,
														//which in turn will be passed to the pathParam.
	{
		String Get_url=getURL().getString("get_url");
		
		Response resp =	given()
				     	  .header("Authorization","Bearer "+BearerToken)
				     	  .pathParam("repoName", Reponame)
				 				
				     	  .when()
				  
				     	  	.get(Get_url); //As get_url= base_url+"/GitRudra7/{repoName}", so here the repoName will 
														// be taken from pathParam specified above
				
			    return resp;
	}
	
	
	public static Response UpdateRepo(Repo payload, String Reponame) 
		{
		String Update_url=getURL().getString("update_url");
		
		Response resp =	given()
							.contentType("application/json")
							.header("Authorization","Bearer "+BearerToken)
							.body(payload)
							.pathParam("repoName", Reponame)
							
							.when()
							
								.patch(Update_url);
		
		return resp; // this content of resp will be used in test class to continue with then() or Assert to do test validations 
		
		}
	
	
	public static Response DeleteRepoNoAdmin(String Reponame)
	{
		String Delete_url=getURL().getString("delete_url");
		Response resp =	given()
				     	  .header("Authorization","Bearer "+BearerToken)
				     	  .pathParam("repoName", Reponame)
				 				
				     	  .when()
				  
				     	  	.delete(Delete_url); 
				
			    return resp;
	}
	
	public static Response DeleteRepoWithAdmin(String Reponame)
	{
		String Delete_url=getURL().getString("delete_url");
		Response resp =	given()
				     	  .header("Authorization","Bearer "+BearerTokenAdmin)
				     	  .pathParam("repoName", Reponame)
				 				
				     	  .when()
				  
				     	  	.delete(Delete_url); 
				
			    return resp;
	}
	
	

}
