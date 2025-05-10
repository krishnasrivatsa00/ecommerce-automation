import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GoogleTest {

    @Test
    public void openGoogle() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\krish\\Desktop\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setHeadless(false); // force visible browser
        options.addArguments("--remote-allow-origins=*"); // helps with connection issues

        WebDriver driver = new ChromeDriver(options);

        System.out.println("Launching Chrome...");
        driver.get("https://www.google.com");
        System.out.println("Navigated to Google.");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String title = driver.getTitle();
        System.out.println("Title fetched: " + title);
        Assert.assertEquals(title, "Google");

        driver.quit();
}
}
