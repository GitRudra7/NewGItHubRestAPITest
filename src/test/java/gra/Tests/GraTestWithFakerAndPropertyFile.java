package gra.Tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import gra.Endpoints.RepoSetupWithGivenWhenAndPropertyFile;
import gra.Payloads.Repo;
import io.restassured.response.Response;

public class GraTestWithFakerAndPropertyFile 

{
	 Repo repoObj;
	 Faker fakerObj;
	 String myReponame;
	 public Logger logger; //***************
	
	
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
		
		logger = LogManager.getLogger(this.getClass()); //*************************
		
	}
	
	@Test(priority = 1)
	public void CreateRepoAPITest()
	{
		logger.info("This is CreateRepoAPITest");
		Response testResp = RepoSetupWithGivenWhenAndPropertyFile.CreateRepo(repoObj);
		testResp.then().log().body();
		Assert.assertEquals(testResp.getStatusCode(),201);
	}
	
	@Test(priority = 2)
	public void GetRepoAPITest()
	{
		logger.info("This is GetRepoAPITest");
		Response testResp = RepoSetupWithGivenWhenAndPropertyFile.GetRepo(repoObj.getName());
		testResp.then().log().body();
		Assert.assertEquals(testResp.getStatusCode(),200);
				
	}
	
	@Test(priority = 3)
	public void UpdateRepoAPITest()
	{
		logger.info("This is UpdateRepoAPITest");
		repoObj.setIs_template(false);
		repoObj.setDescription("This is ur updated Repo");
		repoObj.setHas_issues(false);
		repoObj.setHas_projects(false);
		repoObj.setHas_wiki(false);
		
		Response testResp = RepoSetupWithGivenWhenAndPropertyFile.UpdateRepo(repoObj,repoObj.getName());
		testResp.then().log().all();
	    Assert.assertEquals(testResp.getStatusCode(),200);
				
	}
	
	@Test(priority = 4)
	public void DeleteRepoNoAdminAPITest()
	{
		logger.info("This is DeleteRepoNoAdminAPITest");
		Response testResp = RepoSetupWithGivenWhenAndPropertyFile.DeleteRepoNoAdmin(repoObj.getName());
		testResp.then().log().body();
		Assert.assertEquals(testResp.getStatusCode(),403);
				
	}
	
	@Test(priority = 5)
	public void DeleteRepoWithAdminAPITest()
	{
		logger.info("This is DeleteRepoWithAdminAPITest");
		Response testResp = RepoSetupWithGivenWhenAndPropertyFile.DeleteRepoWithAdmin(repoObj.getName());
		testResp.then().log().body();
		Assert.assertEquals(testResp.getStatusCode(),204);
				
	}
	
	@Test(priority = 6,dependsOnMethods= {"DeleteRepoWithAdminAPITest"}) //this test will be SKIPPED, if dependable mthd/test fails
	public void GetRepoAPITestAfterDelete()
	{
		logger.info("This is GetRepoAPITestAfterDelete");
		Response testResp = RepoSetupWithGivenWhenAndPropertyFile.GetRepo(repoObj.getName());
		testResp.then().log().body();
		Assert.assertEquals(testResp.getStatusCode(),404);
				
	}

}
