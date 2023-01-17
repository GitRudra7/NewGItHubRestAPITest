package gra.Tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import gra.Endpoints.RepoSetupWithGivenWhen;
import gra.Payloads.Repo;
import io.restassured.response.Response;

public class GraTestWithFaker 

{
	 Repo repoObj;
	 Faker fakerObj;
	 String myReponame;
	
	
	@BeforeClass
	public void TestSetup()
	{
		repoObj = new Repo(); // Just as faker is a class, similarly Repo is our user defined payload class
		fakerObj = new Faker();
		myReponame = fakerObj.name().firstName();
		
		repoObj.setName(myReponame);
		repoObj.setDescription("Repo is"+myReponame);
		repoObj.setHomepage("https://github.com");
		repoObj.setIs_template(true);
		
	}
	
	@Test(priority = 1)
	public void CreateRepoAPITest()
	{
		
		Response testResp = RepoSetupWithGivenWhen.CreateRepo(repoObj);
		testResp.then().log().body();
		Assert.assertEquals(testResp.getStatusCode(),201);
	}
	
	@Test(priority = 2)
	public void GetRepoAPITest()
	{
		
		Response testResp = RepoSetupWithGivenWhen.GetRepo(repoObj.getName());
		testResp.then().log().body();
		Assert.assertEquals(testResp.getStatusCode(),200);
				
	}
	
	@Test(priority = 3)
	public void UpdateRepoAPITest()
	{
		
		repoObj.setIs_template(false);
		repoObj.setDescription("This is ur updated Repo");
		repoObj.setHas_issues(false);
		repoObj.setHas_projects(false);
		repoObj.setHas_wiki(false);
		
		Response testResp = RepoSetupWithGivenWhen.UpdateRepo(repoObj,repoObj.getName());
		testResp.then().log().all();
	    Assert.assertEquals(testResp.getStatusCode(),200);
				
	}
	
	@Test(priority = 4)
	public void DeleteRepoNoAdminAPITest()
	{
		
		Response testResp = RepoSetupWithGivenWhen.DeleteRepoNoAdmin(repoObj.getName());
		testResp.then().log().body();
		Assert.assertEquals(testResp.getStatusCode(),403);
				
	}
	
	@Test(priority = 5)
	public void DeleteRepoWithAdminAPITest()
	{
		
		Response testResp = RepoSetupWithGivenWhen.DeleteRepoWithAdmin(repoObj.getName());
		testResp.then().log().body();
		Assert.assertEquals(testResp.getStatusCode(),204);
				
	}
	
	@Test(priority = 6,dependsOnMethods= {"DeleteRepoWithAdminAPITest"})
	public void GetRepoAPITestAfterDelete()
	{
		
		Response testResp = RepoSetupWithGivenWhen.GetRepo(repoObj.getName());
		testResp.then().log().body();
		Assert.assertEquals(testResp.getStatusCode(),404);
				
	}

}
