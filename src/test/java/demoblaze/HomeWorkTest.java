package demoblaze;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Objects;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class HomeWorkTest {

    private final Locators locators = new Locators();

    @BeforeAll
    public static void setUp() {
        Configuration.baseUrl = "https://www.demoblaze.com/";
        Configuration.browser = "firefox";
        Configuration.browserCapabilities = new FirefoxOptions().setPageLoadStrategy(PageLoadStrategy.EAGER).addArguments("--headless", "--window-size=1920,1080", "--disable-notifications", "--disable-gpu", "--disable-dev-tools", "--fastSetValue");
    }

    @AfterAll
    public static void tearDown() {Selenide.closeWebDriver();}

    @Test
    public void purchaseTest() {
        String[] products = new String[]{"Nokia lumia 1520", "Iphone 6 32gb", "Sony vaio i7\n"};
        int price = 0;
        open("/");
        for (String product : products) {
            locators.clickLink(product);
            SelenideElement priceElement = element(By.className("price-container"));
            price = price + Integer.parseInt(priceElement.getText().replaceAll("\\D",""));
            locators.clickLink("Add to cart");
            confirm();
            locators.clickLink("Home ");
        }
        locators.clickLink("Cart");
        element(By.id("totalp")).shouldHave(text(price + ""));
        locators.clickLink("Place Order");
        String[] inputFields = new String[]{"name", "country", "city", "card", "month", "year"};
        String[] inputValues = new String[]{"Your name", "Your country", "Your city", "Your card data", "Some month", "Some year"};
        for (int i = 0; i < inputFields.length; i++) {
            element(By.id(inputFields[i])).setValue(inputValues[i]);}
        locators.clickLink("Purchase");
        SelenideElement confirmation = $(".sweet-alert").shouldHave(text("Thank you for your purchase!"));
        $(".sweet-alert").shouldHave(text("Amount: " + price + " USD"));
        String[] output = Objects.requireNonNull($("p.lead.text-muted").getAttribute("innerHTML")).split("<br>");
        System.out.println("Номер заказа: " + output[0].substring(4));
        locators.clickLink("OK");
        confirmation.should(Condition.disappear);
    }

}
