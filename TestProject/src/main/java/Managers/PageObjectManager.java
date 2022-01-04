package Managers;

import PageObjects.AccuWeatherPage;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class PageObjectManager {

	WebDriver driver;
	AccuWeatherPage accuWeatherPage;
	public SoftAssert softAssert = new SoftAssert();

	public PageObjectManager(WebDriver driver) {
		this.driver = driver;
	}
	
	public AccuWeatherPage getAccuWeatherPage() {
		return (accuWeatherPage == null) ? accuWeatherPage = new AccuWeatherPage(driver) : accuWeatherPage;
	}

}
