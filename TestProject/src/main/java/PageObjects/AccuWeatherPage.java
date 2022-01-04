package PageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;

public class AccuWeatherPage {

	WebDriver driver;
	@FindBy(how=How.XPATH, using="//input[@class='search-input']")
	private WebElement searchTextBox;

	@FindBy(how=How.XPATH, using="//div[@class='locations-list content-module']/child::a")
	private List<WebElement> locations;

	@FindBy(how=How.CSS, using="div[id='dismiss-button']")
	private List<WebElement> dismissButton;

	@FindBy(how=How.XPATH, using="(//div[@class='temp'])[1]")
	private WebElement currentWeather;

	@FindBy(how=How.XPATH, using="(//div[@class='temp'])[1]//span")
	private WebElement unit;

	WebDriverWait wait;

	public AccuWeatherPage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver,10);
		PageFactory.initElements(driver, this);
	}

	public void findTemp(String location) {
		searchTextBox.sendKeys(location);
		searchTextBox.sendKeys(Keys.ENTER);
	}

	public String getCurrentWeatherUI() {
		wait.until(ExpectedConditions.visibilityOf(currentWeather));
		return currentWeather.getText();
	}

	public Response getCurrentWeatherAPI(String baseURI, String cityName, String apiKey) {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.when()
				.get(baseURI + cityName + "&appid=" + apiKey)
				.then()
				.extract().response();
		return response;
	}
}
