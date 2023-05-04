package com.qaconsultants;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java8.En;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.List;
import java.util.Random;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.*;

public class TestSteps implements En {
    public TestSteps() {
        Given("^I open login page$", () -> {
            System.out.println("I open login page");
            //Configuration.browser = "firefox";
            //Configuration.browserBinary = "C:/Program Files/Mozilla Firefox/firefox.exe";
            //System.setProperty("webdriver.gecko.driver", "E:/!!!!/geckodriver.exe");
            System.setProperty("selenide.browser", "Chrome");
            System.setProperty("webdriver.chrome.driver", "/Users/panjiang/Development/testFrameworkTemplate/chromedriver_mac64/chromedriver");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("disable-infobars");
            chromeOptions.addArguments("--test-type");
            chromeOptions.addArguments("--disable-extensions"); //to disable browser extension popup
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--disable-popup-blocking");
            chromeOptions.addArguments("--disable-web-security");
            chromeOptions.addArguments("--allow-running-insecure-content");
            chromeOptions.addArguments("--remote-allow-origins=*");
            chromeOptions.addArguments("incognito");
            chromeOptions.addArguments("--ignore-certificate-errors");
            WebDriverRunner.setWebDriver(new ChromeDriver(chromeOptions));
            open("https://www.automationexercise.com");
            sleep(1000);
        });

        When("^I am in the landing page$", () -> {
            String XPathOfHeaderIMG = "//img[@src='/static/images/home/logo.png']";
            $(By.xpath(XPathOfHeaderIMG)).should(exist);
        });

        Then("^I login into website$", () -> {
            String loginEmail = "pjiang@qac.com";
            String loginPassword = "qacfortest";
            String XPathOfLoginLink = "//a[@href='/login']";
            String XPathOfLoginEmailInput = "//form[@action='/login']//input[@name='email']";
            String XPathOfLoginPasswordInput = "//form[@action='/login']//input[@name='password']";
            String XPathOfLoginButton = "//button[text()='Login']";

            $(By.xpath(XPathOfLoginLink)).click();
            $(By.xpath(XPathOfLoginEmailInput)).setValue(loginEmail);
            $(By.xpath(XPathOfLoginPasswordInput)).setValue((loginPassword));
            $(By.xpath(XPathOfLoginButton)).click();
        });

        Then("^I search item in product page$", () -> {
            String XPathOfProductLink = "//a[@href='/products']";
            String idOfAdCloseButton = "dismiss-button";
            String XPathOfMainAdFrame = "//iframe[contains(@name,'aswift') and contains(@style,'visibility: visible')]";
            String XPathOfPopupAdFrame = "//iframe[@id='ad_iframe']";
            String XPathOfProductSearchInput = "//input[@id='search_product']";
            String XPathOfProductsSearchButton = "//button[@id='submit_search']";
            String productType = "tshirts";

            SelenideElement mainAdFrame = $(By.xpath(XPathOfMainAdFrame));
            SelenideElement popupAdFrame = $(By.xpath(XPathOfPopupAdFrame));

            $(By.xpath(XPathOfProductLink)).click();

            if (mainAdFrame.exists() && mainAdFrame.isDisplayed()) {
                Selenide.switchTo().frame(mainAdFrame);
                Selenide.switchTo().frame(popupAdFrame);
                SelenideElement closeButton = $(By.id(idOfAdCloseButton));
                closeButton.click();
                Selenide.switchTo().defaultContent();
            }

            $(By.xpath(XPathOfProductSearchInput)).setValue(productType);
            $(By.xpath((XPathOfProductsSearchButton))).click();
        });

        Then("^I add products to shopping cart$", () -> {
            final int numOfProductToAdd = 2;
            String XPathOfViewProductsLink = "//a[contains(@href, '/product_details')]";
            String XPathOfAddToCartButton = "//button[@class='btn btn-default cart']";
            String XPathOfContinueShoppingButton = "//button[contains(text(), 'Continue Shopping')]";
            List<SelenideElement> viewProducts = $$(By.xpath(XPathOfViewProductsLink));
            SelenideElement addToCardButton = $(By.xpath(XPathOfAddToCartButton));
            SelenideElement continueShoppingButton = $(By.xpath((XPathOfContinueShoppingButton)));

            for (int i = 0; i < numOfProductToAdd; i++) {
                viewProducts.get(i).scrollTo().click();
                addToCardButton.click();
                continueShoppingButton.click();
                back();
            }
        });

        Then("^I verified items in the shopping cart$", () -> {
            final int numOfProductToAdd = 2;
            String XPathOfCartLink = "//a[@href='/view_cart']";
            String XPathOfProductsInCart = "//tr[contains(@id, 'product-')]";
            $(By.xpath(XPathOfCartLink)).click();
            $$(By.xpath(XPathOfProductsInCart)).shouldHave(size(numOfProductToAdd));
        });

        Then("^I remove 1 T-Shirt that I don't like from the cart$", () -> {
            String XPathOfDeleteIcon = "//a[@class='cart_quantity_delete']";

            List<SelenideElement> deleteIcon = $$(By.xpath((XPathOfDeleteIcon)));
            Random random = new Random();
            int randomeInt = random.nextInt(deleteIcon.size());

            deleteIcon.get(randomeInt).click();
        });

        Then("^I proceed to checkout and place my order$", () -> {
            String XPathOfProceedToCheckoutButton = "//a[@class='btn btn-default check_out']";
            String XPathOfPlaceOrderButton = "//a[@class='btn btn-default check_out' and @href='/payment']";
            String XPathOfAddressHeader = "//h2[text()='Address Details']";
            String XPathOfReviewOrderHeader = "//h2[text()='Review Your Order']";

            $(By.xpath(XPathOfProceedToCheckoutButton)).click();
            $(By.xpath(XPathOfAddressHeader)).isDisplayed();
            $(By.xpath(XPathOfReviewOrderHeader)).isDisplayed();
            $(By.xpath(XPathOfPlaceOrderButton)).scrollTo().click();
        });

        Then("^I add card details to proceed payment$", () -> {
            String cardHolder = "PJ YD";
            String cardNumber = "123456789";
            String cardCVC = "123";
            String expMonth = "12";
            String expYear = "2025";

            String XPathOfPaymentHeader = "//h2[text()='Payment']";
            String XPathOfNameOnCardInput = "//input[@name='name_on_card']";
            String XPathOfCardNumberInput = "//input[@name='card_number']";
            String XPathOfCVCInput = "//input[@name='cvc']";
            String XPathOfExpireMonthInput = "//input[@name='expiry_month']";
            String XPathOfExpireYearInput = "//input[@name='expiry_year']";
            String idOfPayAndConfirmOrderButton = "submit";

            $(By.xpath(XPathOfPaymentHeader)).isDisplayed();
            $(By.xpath((XPathOfNameOnCardInput))).setValue(cardHolder);
            $(By.xpath(XPathOfCardNumberInput)).setValue(cardNumber);
            $(By.xpath((XPathOfCVCInput))).setValue(cardCVC);
            $(By.xpath(XPathOfExpireMonthInput)).setValue(expMonth);
            $(By.xpath((XPathOfExpireYearInput))).setValue(expYear);
            $(By.id(idOfPayAndConfirmOrderButton)).click();
        });

        Then("^I download invoice after payment successful$", () -> {
            String XPathOfOrderPlacedMessage = "//p[text()='Congratulations! Your order has been confirmed!']";
            String XPathOfDownloadInvoiceButton = "//a[contains(@href, '/download_invoice/')]";

            $(By.xpath(XPathOfOrderPlacedMessage)).isDisplayed();
            $(By.xpath(XPathOfDownloadInvoiceButton)).click();

            String filePath = System.getProperty("user.home") + "/Downloads/invoice.txt";
            File downloadedFile = new File(filePath);

            try {
                Thread.sleep(2000); // wait for 1 second before checking file existence
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            assert downloadedFile.exists() && downloadedFile.length() > 0;
            assert downloadedFile.isFile() && downloadedFile.getName().endsWith(".txt");
        });
    }
}
