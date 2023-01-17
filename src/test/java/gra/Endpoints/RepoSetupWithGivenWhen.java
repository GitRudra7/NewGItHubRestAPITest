package gra.Endpoints;

import static io.restassured.RestAssured.given;
import gra.Payloads.Repo;
import io.restassured.response.Response;

public class RepoSetupWithGivenWhen
{
	public static String BearerToken = "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";
	public static String BearerTokenAdmin = "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";	
	
	public static Response CreateRepo(Repo payload) // payload is a variable with the type Repo class , also return type of this mthd is Response, as we r returning response
													// will pass payload (generated in test class using Repo class of payload) to this mthd .
	{
		
	Response resp =	given()
		  .contentType("application/json")
		  .header("Authorization","Bearer "+BearerToken)
		  .body(payload)
		
		 .when()
		  
		   .post(UrlRoutes.post_url);
		
	    return resp; // this content of resp will be used in test class to continue with then() or Assert to do test validations 
				  	
	}
	
	
	public static Response GetRepo(String Reponame) // Value of Reponame varb. will be passed to this method from Testdata excel,
														//which in turn will be passed to the pathParam.
	{
		Response resp =	given()
				     	  .header("Authorization","Bearer "+BearerToken)
				     	  .pathParam("repoName", Reponame)
				 				
				     	  .when()
				  
				     	  	.get(UrlRoutes.get_url); //As get_url= base_url+"/GitRudra7/{repoName}", so here the repoName will 
														// be taken from pathParam specified above
				
			    return resp;
	}
	
	
	public static Response UpdateRepo(Repo payload, String Reponame) 
		{
		
		Response resp =	given()
							.contentType("application/json")
							.header("Authorization","Bearer "+BearerToken)
							.body(payload)
							.pathParam("repoName", Reponame)
							
							.when()
							
								.patch(UrlRoutes.update_url);
		
		return resp; // this content of resp will be used in test class to continue with then() or Assert to do test validations 
		
		}
	
	
	public static Response DeleteRepoNoAdmin(String Reponame)
	{
		Response resp =	given()
				     	  .header("Authorization","Bearer "+BearerToken)
				     	  .pathParam("repoName", Reponame)
				 				
				     	  .when()
				  
				     	  	.delete(UrlRoutes.delete_url); 
				
			    return resp;
	}
	
	public static Response DeleteRepoWithAdmin(String Reponame)
	{
		Response resp =	given()
				     	  .header("Authorization","Bearer "+BearerTokenAdmin)
				     	  .pathParam("repoName", Reponame)
				 				
				     	  .when()
				  
				     	  	.delete(UrlRoutes.delete_url); 
				
			    return resp;
	}
	
	

}
