package gra.Tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import gra.Endpoints.RepoSetupWithGivenWhen;
import gra.Payloads.Repo;
import gra.Utilities.DataProviders;
import io.restassured.response.Response;

public class GraTestWithDataDrivenTest 

{

	@Test(priority = 1, dataProvider = "Data", dataProviderClass=DataProviders.class) //import dp class
	public void CreateRepoAPITest(String RepoName,String RepoDescription, String HomePage) //one by one repo name,descrp, and homepg will be passed 
																							//from dp to this method, which in turn will be passed to internal setMethods 
	{
		Repo repoObj = new Repo();
		repoObj.setName(RepoName);
		repoObj.setDescription(RepoDescription);
		repoObj.setHomepage(HomePage);
		repoObj.setIs_template(true);
		
		Response testResp = RepoSetupWithGivenWhen.CreateRepo(repoObj);
		testResp.then().log().body();
		Assert.assertEquals(testResp.getStatusCode(),201);
	}
	
	@Test(priority = 2, dataProvider = "RepoNames", dataProviderClass=DataProviders.class)
	public void GetRepoAPITest(String RepoName) //one by one repo name will be passed from dp to this method
												//and will be stored in RepoName string varb., which in turn will be 
												//passed to GetRepo method.
	{
		System.out.println(RepoName);
		Response testResp = RepoSetupWithGivenWhen.GetRepo(RepoName);
		testResp.then().log().body();
		Assert.assertEquals(testResp.getStatusCode(),200);
				
	}
	
	@Test(priority = 3, dataProvider = "UpdateData", dataProviderClass=DataProviders.class)
	public void UpdateRepoAPITest(String RepoName, String RepoDescription, String HomePage)
	{
		
		Repo repoObj = new Repo();
		repoObj.setDescription(RepoDescription);
		repoObj.setHomepage(HomePage);
		repoObj.setIs_template(false);
		repoObj.setHas_issues(false);
		repoObj.setHas_projects(false);
		repoObj.setHas_wiki(false);
		
		System.out.println(RepoName);
		System.out.println(RepoDescription);
		System.out.println(HomePage);
		Response testResp = RepoSetupWithGivenWhen.UpdateRepo(repoObj,RepoName);
		testResp.then().log().all();
	    Assert.assertEquals(testResp.getStatusCode(),200);
				
	}
	
	@Test(priority = 4, dataProvider = "RepoNames", dataProviderClass=DataProviders.class)
	public void DeleteRepoNoAdminAPITest(String RepoName)
	{
		
		Response testResp = RepoSetupWithGivenWhen.DeleteRepoNoAdmin(RepoName);
		testResp.then().log().body();
		Assert.assertEquals(testResp.getStatusCode(),403);
				
	}
	
	@Test(priority = 5, dataProvider = "RepoNames", dataProviderClass=DataProviders.class)
	public void DeleteRepoWithAdminAPITest(String RepoName)
	{
		
		Response testResp = RepoSetupWithGivenWhen.DeleteRepoWithAdmin(RepoName);
		testResp.then().log().body();
		Assert.assertEquals(testResp.getStatusCode(),204);
				
	}
	
	@Test(priority = 6,dependsOnMethods= {"DeleteRepoWithAdminAPITest"}, dataProvider = "RepoNames", dataProviderClass=DataProviders.class)
	public void GetRepoAPITestAfterDelete(String RepoName)
	{
		
		Response testResp = RepoSetupWithGivenWhen.GetRepo(RepoName);
		testResp.then().log().body();
		Assert.assertEquals(testResp.getStatusCode(),404);
				
	}

}
