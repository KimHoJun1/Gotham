package gs.service;

import java.util.List;
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
//		Pattern pid;
		Matcher matcher;
//		Matcher mid;
		// Geocoder by proj4_1j
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
			
			pattern = Pattern.compile("(?<=\\</span\\>\\</div\\>\\<div\\sclass\\=\"list_acc_rit\">\\<span>\\처리예상시간\\s:\\s).*(?=\\</span\\>\\</div\\>)");
			matcher = pattern.matcher(elem_stringified);
			
			while (matcher.find()) { 
				jsonObject.put("처리예상시간", matcher.group(0));
				System.out.println(matcher.group(0));
			} 
			
			pattern = Pattern.compile("(?<=T\\|).*(?=\\d{10}\"\\>)");
			matcher = pattern.matcher(elem_stringified);
 
			while (matcher.find()) {
				String[] fromPoint = matcher.group(0).split("\\|"); 
				JSONObject jsonObject_temp = new JSONObject();
				System.out.println(fromPoint[0]); 
				System.out.println(fromPoint[1]);
				double[] toPoint = geocoder.proj4_1(fromPoint);
				jsonObject_temp.put("lng", toPoint[0]);
				jsonObject_temp.put("lat", toPoint[1]);
				jsonObject.put("좌표", jsonObject_temp);
			}
			resultArray.add(jsonObject);
		}
		
		return resultArray;
	}

	
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
