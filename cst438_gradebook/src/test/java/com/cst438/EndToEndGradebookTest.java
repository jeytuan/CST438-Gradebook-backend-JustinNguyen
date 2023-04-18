package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EndToEndGradebookTest {

    @LocalServerPort
    static int port;

    static WebDriver driver;

    @BeforeAll
    static void setup() {
        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
        driver = new ChromeDriver();
    }

    @AfterAll
    static void closeBrowser() {
        driver.quit();
    }

    @Test
    public void testCreateAssignment() throws InterruptedException {

        // Login as Instructor
        driver.get("http://localhost:" + port + "/login");
        WebElement email = driver.findElement(By.name("email"));
        WebElement password = driver.findElement(By.name("password"));
        email.sendKeys("instructor@example.com");
        password.sendKeys("password");
        password.submit();
        Thread.sleep(2000);

        // Navigate to create assignment page
        driver.get("http://localhost:" + port + "/assignments/new");
        Thread.sleep(2000);

        // Fill out assignment form
        WebElement title = driver.findElement(By.name("title"));
        WebElement description = driver.findElement(By.name("description"));
        WebElement dueDate = driver.findElement(By.name("dueDate"));
        title.sendKeys("New Assignment");
        description.sendKeys("This is a new assignment.");
        dueDate.sendKeys("2021-12-31");
        dueDate.submit();
        Thread.sleep(2000);
        
        // Verify assignment was created
        WebElement message = driver.findElement(By.cssSelector(".alert-success"));
        String expectedMessage = "Assignment created successfully!";
        assertEquals(expectedMessage, message.getText());

    }

}
