package extra03endless;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RedditDM {

	static HashSet<String> set = null;
	static WebDriver drive = null;

	public static void main(String[] args) throws InterruptedException {
		populateSet();
		bootUp();
		sendMessage();
	}
	
	/**
	 * Helper method to send the messages and delay accordingly
	 * 
	 * @throws InterruptedException
	 */
	private static void sendMessage() throws InterruptedException {
		Thread.sleep(3000);
		drive.get("https://old.reddit.com/message/compose/");
		Thread.sleep(2000);
		for (String s : set) {
			drive.findElement(By.name("to")).sendKeys(s);
			message();
			System.out.println("Message sent to: " + s);
		}
		drive.get(
				"https://www.google.com/search?q=DONE&source=lnms&tbm=isch&sa=X&ved=0ahUKEwjxgpjhqpDcAhVn9YMKHbS8D0YQ_AUICygC&biw=1200&bih=719#imgrc=OI5rTTCkehYYcM:");
		System.out.println("DONE!");
		drive.close();
		
	}

	/**
	 * Helper method to start the driver and log in to the site
	 * 
	 * @throws InterruptedException
	 */
	private static void bootUp() throws InterruptedException {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("mac")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "/src/extra03catering/chromedriver");
		} else {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\src\\extra03catering\\chromedriver.exe");
		}
		drive = new ChromeDriver();
		drive.get("https://old.reddit.com/login?dest=https%3A%2F%2Fold.reddit.com%2Fmessage%2Fcompose%2F");
		drive.findElement(By.xpath("//*[@id=\"user_login\"]")).sendKeys("USER"); // TODO: CHANGE USER
		drive.findElement(By.xpath("//*[@id=\"passwd_login\"]")).sendKeys("PASS"); // TODO: CHANGE PASS
		drive.findElement(By.xpath("//*[@id=\"login-form\"]/div[5]/button")).click();
	}

	/**
	 * A helper method to populate the set with the data from previous.txt
	 */
	private static void populateSet() {
		set = new HashSet<String>();
		File file = new File("src/extra03endless/restock.txt");
		try (Scanner scan = new Scanner(file)) {
			while (scan.hasNextLine()) {
				set.add(scan.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * A helper method to write the predesignated message
	 * 
	 * @throws InterruptedException
	 */
	private static void message() throws InterruptedException {
		drive.findElement(By.name("subject")).sendKeys("SUBJECT"); // TODO: ADD SUBJECT
		drive.findElement(By.xpath("//*[@id=\"compose-message\"]/div[6]/div/div/div[2]/div/div[1]/textarea")).sendKeys(
				"MESSAGE"); // TODO: ADD MESSAGE
		drive.findElement(By.xpath("//*[@id=\"send\"]")).click();
		WebDriverWait wait = new WebDriverWait(drive, 1000);
		wait.until((ExpectedCondition<Boolean>) drive -> drive.findElement(By.name("subject")).getAttribute("value")
				.isEmpty());
	}

}
