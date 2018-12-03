package gs.service;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import io.github.bonigarcia.wdm.WebDriverManager;

@Service
public class IndexCrawler {

	public JSONObject healthCrawler() {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		WebElement elem;
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		// JSONArray for return object
		JSONObject resultObject = new JSONObject();

		// Data input
		// 1.
		driver.get("http://www.weather.go.kr/weather/lifenindustry/jisudaymap_D05.jsp");
		elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"mapoff\"]")));

		if (elem.getAttribute("style").equals("")) {
			elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"provide\"]")));
		} else {
			elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"tittext\"]")));
		}

		resultObject.put("감기가능지수", elem.getText());	

		// 2.
		driver.get("http://www.weather.go.kr/weather/lifenindustry/jisudaymap_D01.jsp");
		elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"tittext\"]")));
		resultObject.put("천식폐질환가능지수", elem.getText());

		// 3.
		driver.get("http://www.weather.go.kr/weather/lifenindustry/jisudaymap_D02.jsp");
		elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"tittext\"]")));
		resultObject.put("뇌졸중가능지수", elem.getText());
		
		// 4.
		driver.get("http://www.weather.go.kr/weather/lifenindustry/jisudaymap_D04.jsp");
		elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"tittext\"]")));
		resultObject.put("피부질환가능지수", elem.getText());
		
		// 5.
		driver.get("http://www.weather.go.kr/weather/lifenindustry/jisudaymap_D06.jsp");
		elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"mapoff\"]")));
		
		if (elem.getAttribute("style").equals("")) {
			elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"provide\"]")));
		} else {
			elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"tittext\"]")));
		}
		
		resultObject.put("꽃가루농도위험지수", elem.getText());
		
		return resultObject;
	}
	
	public JSONObject lifeCrawler() {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		WebElement elem;
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		// JSONArray for return object
		JSONObject resultObject = new JSONObject();
		JSONObject subObject;

		// Data input
		// 1.
		subObject = new JSONObject();
		driver.get("http://www.weather.go.kr/weather/lifenindustry/jisudaymap_A01_2.jsp");

		elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"tittext\"]")));
		subObject.put("stage", elem.getText());
		
		driver.get("http://www.weather.go.kr/weather/lifenindustry/li_asset/popup/imgdata_popup.jsp?CODE=A01&point=1");
		
		elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/table/tbody/tr[1]/td[1]")));
		subObject.put("point", elem.getText());
		
		resultObject.put("식중독지수", subObject);

		// 2.
		subObject = new JSONObject();
		driver.get("http://www.weather.go.kr/weather/lifenindustry/jisutimemap_A06.jsp");
		elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"mapoff\"]")));
		
		if (elem.getAttribute("style").equals("")) {
			elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"provide\"]")));
			subObject.put("stage", elem.getText());
			subObject.put("point", elem.getText());
		} else {
			elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"tittext\"]")));
			subObject.put("stage", elem.getText());
//			elem = driver.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[1]"));
//			driver.get(""); // wait til next summer...
			subObject.put("point", elem.getText());
		}

		resultObject.put("불쾌지수", subObject);

		// 3.
		subObject = new JSONObject();
		driver.get("http://www.weather.go.kr/weather/lifenindustry/jisutimemap_A03.jsp");
		elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"mapoff\"]")));
		
		if (elem.getAttribute("style").equals("")) {
			elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"provide\"]")));
			subObject.put("stage", elem.getText());
			subObject.put("point", elem.getText());
		} else {
			elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"tittext\"]")));
			subObject.put("stage", elem.getText());
			driver.get("http://www.weather.go.kr/weather/lifenindustry/li_asset/popup/timedata_popup.jsp?CODE=A03&point=0");
			elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/table/tbody/tr[1]/td[1]")));
			subObject.put("point", elem.getText());
		}
		
		resultObject.put("체감온도", subObject);
		
		// 4.
		subObject = new JSONObject();
		driver.get("http://www.weather.go.kr/weather/lifenindustry/jisutimemap_A08.jsp");
		elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"mapoff\"]")));
		
		if (elem.getAttribute("style").equals("")) {
			elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"provide\"]")));
			subObject.put("stage", elem.getText());
			subObject.put("point", elem.getText());
		} else {
			elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"tittext\"]")));
			subObject.put("stage", elem.getText());
			subObject.put("point", elem.getText());
		}
		
		resultObject.put("동파가능지수", subObject);
		
		// 5.
		subObject = new JSONObject();
		driver.get("http://www.weather.go.kr/weather/lifenindustry/jisudaymap_A07_1.jsp");
		elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"mapoff\"]")));
		
		if (elem.getAttribute("style").equals("")) {
			elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"provide\"]")));
			subObject.put("stage", elem.getText());
			subObject.put("point", elem.getText());
		} else {
			elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"tittext\"]")));
			subObject.put("stage", elem.getText());
//			elem = driver.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[1]"));
//			driver.get(""); // wait til next summer...
			subObject.put("point", elem.getText());
		}

		resultObject.put("자외선지수", subObject);
		
		return resultObject;
	}
}
