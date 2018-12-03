package gs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import io.github.bonigarcia.wdm.WebDriverManager;

@Service
public class AlarmCrawler {

	public JSONArray accidentCrawler() {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		
		driver.get("http://safecity.seoul.go.kr/acdnt/roadIndex.do");
		
		List<WebElement> elemList = driver.findElements(By.xpath("//*[@id=\"roadTab\"]/div[1]/div"));

		// JSONArray for return object
		JSONArray resultArray = new JSONArray();
		
		// Pattern and Matcher to use regular expressing
		Pattern pattern;
		Matcher matcher;
		
		// Geocoder by proj4j
		Geocoder geocoder = new Geocoder();
		
		// Data input
		for (int i = 0; i < elemList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			String elem_stringified = (elemList.get(i).getAttribute("innerHTML"));
			
			System.out.println(elem_stringified);
			
			pattern = Pattern.compile("(?<=\\d{10}\"\\>).*(?=\\</a\\>\\</span\\>\\<span\\sclass\\=\"add)");
			matcher = pattern.matcher(elem_stringified);
			
			while (matcher.find()) {
				jsonObject.put("사고명세", matcher.group(0));
				System.out.println(matcher.group(0));
			}
			
//			pattern = Pattern.compile("(?<=noti\\d{2}\"\\>).*(?=\\</span\\>\\<span\\sclass\\=\"day)");
//			matcher = pattern.matcher(elem_stringified);
//			
//			while (matcher.find()) {
//				jsonObject.put("처리상황", matcher.group(0));
//			}
//			
//			pattern = Pattern.compile("(?<=mapByXY\\().*(?=,\\s10)");
//			matcher = pattern.matcher(elem_stringified);
//			
//			while (matcher.find()) {
//				String[] fromPoint = matcher.group(0).split(",");
//				JSONObject jsonObject_temp = new JSONObject();
//				
//				double[] toPoint = geocoder.proj4(fromPoint);
//				
//				jsonObject_temp.put("lng", toPoint[0]);
//				jsonObject_temp.put("lat", toPoint[1]);
//				jsonObject.put("좌표", jsonObject_temp);
//			}
//			
//			pattern = Pattern.compile("(?<=위치\\s\\:\\s).*(?=\\<\\/a\\>\\<\\/li\\>)");
//			matcher = pattern.matcher(elem_stringified);
//			
//			while (matcher.find()) {
//				jsonObject.put("사고위치", matcher.group(0));
//			}
//			
//			resultArray.add(jsonObject);
		}
		
		return resultArray;
	}

//	public JSONArray accidentCrawler() {
//		
//		WebDriverManager.chromedriver().setup();
//		WebDriver driver = new ChromeDriver();
//		
//		driver.get("http://its.go.kr/traffic/accident.do");
//		
//		WebElement elem = driver.findElement(By.xpath("//*[@id=\"searchForm\"]/fieldset/div[1]/div[1]/div[2]/b"));
//				
//		elem.click();
//		elem = driver.findElement(By.xpath("//*[@id=\"searchForm\"]/fieldset/div[1]/div[1]/div[3]/div/ul/li[2]"));
//		elem.click();
//		elem = driver.findElement(By.xpath("//*[@id=\"searchBtn\"]"));
//		elem.click();
//		
//		List<WebElement> elemList_1 = driver.findElements(By.xpath("//*[@id=\"skip\"]/section/div[2]/table/tbody/tr/td[2]"));
//		List<WebElement> elemList_2 = driver.findElements(By.xpath("//*[@id=\"skip\"]/section/div[2]/table/tbody/tr/td[3]"));
//
//		JSONArray resultArray1 = new JSONArray();
//		
//		// Pattern and Matcher to use regular expressing
//		Pattern pattern;
//		Matcher matcher;
//		
//		// Geocoder by proj4j
//		Geocoder geocoder = new Geocoder();
//		
//		// Data input
//		for (int i = 0; i < elemList_1.size(); i++) {
//			JSONObject jsonObject = new JSONObject();
//			String elem_stringified = (elemList_1.get(i).getAttribute("innerHTML"));
//			
//			pattern = Pattern.compile("(?<=화재사고-).*(?=\\<span\\sclass\\=\"trot)");
//			matcher = pattern.matcher(elem_stringified);
//			
//			while (matcher.find()) {
//				jsonObject.put("사고종류", matcher.group(0));
//			}
//			
//			pattern = Pattern.compile("(?<=noti\\d{2}\"\\>).*(?=\\</span\\>\\<span\\sclass\\=\"day)");
//			matcher = pattern.matcher(elem_stringified);
//			
//			while (matcher.find()) {
//				jsonObject.put("처리상황", matcher.group(0));
//			}
//			
//			pattern = Pattern.compile("(?<=mapByXY\\().*(?=,\\s10)");
//			matcher = pattern.matcher(elem_stringified);
//			
//			while (matcher.find()) {
//				String[] fromPoint = matcher.group(0).split(",");
//				JSONObject jsonObject_temp = new JSONObject();
//				
//				double[] toPoint = geocoder.proj4(fromPoint);
//				
//				jsonObject_temp.put("lng", toPoint[0]);
//				jsonObject_temp.put("lat", toPoint[1]);
//				jsonObject.put("좌표", jsonObject_temp);
//			}
//			
//			pattern = Pattern.compile("(?<=위치\\s\\:\\s).*(?=\\<\\/a\\>\\<\\/li\\>)");
//			matcher = pattern.matcher(elem_stringified);
//			
//			while (matcher.find()) {
//				jsonObject.put("사고위치", matcher.group(0));
//			}
//			
//			resultArray1.add(jsonObject);
//		}
//		
//		return resultArray1;
//		
//	}
	
	
	
	
	
	
	public JSONArray fireCrawler() throws Exception {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		
		driver.get("http://safecity.seoul.go.kr/acdnt/fireIndex.do");
		
		List<WebElement> elemList = driver.findElements(By.xpath("//*[@id=\"content_go\"]/div[2]/div/div[2]/ul/li"));
		
		// JSONArray for return object
		JSONArray resultArray = new JSONArray();
		
		// Pattern and Matcher to use regular expressing
		Pattern pattern;
		Matcher matcher;
		
		// Geocoder by proj4j
		Geocoder geocoder = new Geocoder();
		
		// Data input
		for (int i = 0; i < elemList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			String elem_stringified = (elemList.get(i).getAttribute("innerHTML"));
			
			pattern = Pattern.compile("(?<=화재사고-).*(?=\\<span\\sclass\\=\"trot)");
			matcher = pattern.matcher(elem_stringified);
			
			while (matcher.find()) {
				jsonObject.put("사고종류", matcher.group(0));
			}
			
			pattern = Pattern.compile("(?<=noti\\d{2}\"\\>).*(?=\\</span\\>\\<span\\sclass\\=\"day)");
			matcher = pattern.matcher(elem_stringified);
			
			while (matcher.find()) {
				jsonObject.put("처리상황", matcher.group(0));
			}
			
			pattern = Pattern.compile("(?<=mapByXY\\().*(?=,\\s10)");
			matcher = pattern.matcher(elem_stringified);
			
			while (matcher.find()) {
				String[] fromPoint = matcher.group(0).split(",");
				JSONObject jsonObject_temp = new JSONObject();
				
				double[] toPoint = geocoder.proj4(fromPoint);
				
				jsonObject_temp.put("lng", toPoint[0]);
				jsonObject_temp.put("lat", toPoint[1]);
				jsonObject.put("좌표", jsonObject_temp);
			}
			
			pattern = Pattern.compile("(?<=위치\\s\\:\\s).*(?=\\<\\/a\\>\\<\\/li\\>)");
			matcher = pattern.matcher(elem_stringified);
			
			while (matcher.find()) {
				jsonObject.put("사고위치", matcher.group(0));
			}
			
			resultArray.add(jsonObject);
		}
		
		return resultArray;
	}
}
