package StepDefinitions;

import Managers.PageObjectManager;
import PageObjects.AccuWeatherPage;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.asserts.SoftAssert;

public class Steps {

	WebDriver driver;
	PageObjectManager pageObjectManager;
	AccuWeatherPage accuWeatherPage;
	SoftAssert softAssert;
	String tempUI, tempAPIKelvin, tempAPI;

	@Given("^I launch application url$")
	public void i_launch_application_url() {
		System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		String url = "https://www.accuweather.com/";
		driver.get(url);

		pageObjectManager = new PageObjectManager(driver);
		accuWeatherPage = pageObjectManager.getAccuWeatherPage();
		softAssert = pageObjectManager.softAssert;
	}

	@When("^I search location \"([^\"]*)\"$")
	public void iSearchLocation(String location) {
		accuWeatherPage.findTemp(location);
	}

	@Then("^Get weather information from UI$")
	public void getWeatherInformationFromUI() {
		tempUI = accuWeatherPage.getCurrentWeatherUI();
		System.out.println("Temperature from UI: " + tempUI);
	}

	@Then("^Get weather information from API for the \"([^\"]*)\"$")
	public void getWeatherInformationFromAPI(String location) {
		String baseURI = "https://api.openweathermap.org/data/2.5/weather?q=";
		String APIKey = "7fe67bf08c80ded756e598d6f8fedaea";
		Response response = accuWeatherPage.getCurrentWeatherAPI(baseURI, location, APIKey);
		System.out.println("API Response body:::::" + response.getBody().asString());
		softAssert.assertEquals(200, response.statusCode());
		tempAPIKelvin = response.jsonPath().getString("main.temp");
	}

	@Then("^Compare API response data with UI data$")
	public void compareAPIResponseDataWithUIData() {
		float kelvin = Float.parseFloat(tempAPIKelvin);
		// Kelvin to Degree Celsius Conversion
		float celsius = kelvin - 273.15F;
		System.out.println("Kelvin to Degree Celsius Conversion is: " + Math.round(celsius));
		tempAPI = Math.round(celsius) + "°C";
		System.out.println("Temperature from API: " + tempAPI);
		softAssert.assertEquals(tempUI, tempAPI,
				"FAIL: UI data: " + tempUI + " is not matching with the API response data: " + tempAPI);
	}

	@After
	public void after() {
		driver.close();
		softAssert.assertAll();
	}
}
