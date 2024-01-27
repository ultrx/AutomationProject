package org.test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class TestClass {

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.yavlena.com/broker");
        driver.findElement(By.xpath("//a[text()='Зареди още']")).click();
        List<WebElement> brokerNames = driver.findElements(By.xpath("//*[@class='name']"));
        for (int i = 0; i < brokerNames.size(); i++) {
            WebElement brokerElement = brokerNames.get(i);
            try {
                String brokerName = brokerElement.getText().trim();
                searchBroker(driver, brokerName);
                verifySearchResult(driver, brokerName);
                clearSearchField(driver);
                Thread.sleep(4000);

            } catch (StaleElementReferenceException e) {
                brokerNames = driver.findElements(By.xpath("//*[@class='name']"));
                i--;
            }
        }
    }

    public static void searchBroker(WebDriver driver, String brokerName) {
        WebElement searchField = driver.findElement(By.className("input-search"));
        searchField.clear();
        searchField.sendKeys(brokerName);
    }

    public static void clearSearchField(WebDriver driver) {

        WebElement clearBtn = driver.findElement(By.xpath("//button[@class='clear-all-dropdowns clear-btn']"));
        clearBtn.click();
    }

    public static void verifySearchResult(WebDriver driver, String brokerName) {
        // Verify that the searched broker is the only one displayed
        WebElement brokerNameElement = driver.findElement(By.xpath("//h3[@class='name']"));
        String displayedBrokerName = brokerNameElement.getText().trim();
        WebElement propertiesNumber = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/section/div[3]/div/div/article/div/div/div[2]/a"));
        WebElement address = driver.findElement(By.xpath("//div[@class='office']"));
        List<WebElement> telNumber = driver.findElements(By.xpath("//span[@class='tel']"));
        if (!displayedBrokerName.equals(brokerName)) {
            System.out.println("Error: Searched broker is not displayed or multiple brokers are displayed.");
        } else {
            System.out.println("Searched broker: " + brokerName + " is displayed");
        }
        if (!propertiesNumber.isDisplayed()) {
            System.out.println("Error: Properties number isn't displayed.");
        } else {
            System.out.println("Number of properties for broker: " + brokerName + " is displayed");
        }
        if (!telNumber.get(0).isDisplayed() && !telNumber.get(1).isDisplayed()) {
            System.out.println("Error: One or Both Telephone numbers are missing.");
        } else {
            System.out.println("Telephone numbers of broker: " + brokerName + " are displayed");
        }
        if (!address.isDisplayed()) {
            System.out.println("Error: Address isn't displayed.");
        } else {
            System.out.println("Address of broker: " + brokerName + " is displayed");
        }
    }
}




