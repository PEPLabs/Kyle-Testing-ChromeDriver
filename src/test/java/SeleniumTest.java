
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.time.Duration;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumTest {

    private WebDriver webDriver;
    /**
     * set up selenium web driver.
     * NOTE: the selenium web driver might not be properly configured for your environment.
     * If you get an issue involving being unable to run the exe, a mismatched chromedriver version, or any sort of
     * null driver or inability for the Selenium webdriver to start, disregard any failed tests and continue to test
     * the lab manually via the browser.
     */
    @Before
    public void setUp() {

        //Testing locally on windows... tests pass.
//        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("headless");


        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.setBinary("/usr/bin/chromium-browser");
        options.addArguments("--remote-allow-origins=*");
        options.setBinary("/usr/bin/chromium-browser");
        options.addArguments("--remote-allow-origins=*");
        // Fix the user data directory issue
        options.addArguments("--user-data-dir=/tmp/chrome-" + System.currentTimeMillis());
        // Fix permission/display issues
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");  // Run without GUI
        options.addArguments("--disable-gpu");
        


        webDriver = new ChromeDriver(options);
        
        // Wait up to 10 seconds when looking for any element
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Get file
        File file = new File("src/main/index.html");
        String path = "file:///" + file.getAbsolutePath();//added slash for file pathing issue?

        // Open the HTML file
        webDriver.get(path);
    }

    @Test
    public void testCreateOnBulb() {
        // Wait up to 10 seconds for the element to be present
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement onBulb = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("onBulb")));


        // Click the 'on' bulb button
        WebElement onButton = webDriver.findElement(By.id("onBulb"));
        onButton.click();

        // Find the dynamically created elements
        WebElement title = webDriver.findElement(By.xpath("//h2[text()='Lightbulb 1']"));
        WebElement description = webDriver.findElement(By.xpath("//p[contains(text(),'The bulb is')]"));

        // Assert the elements' text values
        assertEquals("Lightbulb 1", title.getText());
        assertTrue(description.getText().contains("on"));
        assertFalse(description.getText().contains("off"));
    }
    @Test
    public void testCreateOffBulb() {
        // Click the 'on' bulb button
        WebElement onButton = webDriver.findElement(By.id("offBulb"));
        onButton.click();

        // Find the dynamically created elements
        WebElement title = webDriver.findElement(By.xpath("//h2[text()='Lightbulb 1']"));
        WebElement description = webDriver.findElement(By.xpath("//p[contains(text(),'The bulb is')]"));

        // Assert the elements' text values
        assertEquals("Lightbulb 1", title.getText());
        assertTrue(description.getText().contains("off"));
        assertFalse(description.getText().contains("on"));
    }

}
