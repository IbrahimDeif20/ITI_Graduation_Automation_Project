package Pages;

import Utilities.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P05_BooksPage {
    WebDriver driver;

    private final By displayDropDown = By.id("products-pagesize");
    private final By sortByDropDown = By.id("products-orderby");
    private final By viewAsDropDown = By.id("products-viewmode");
    private final By addToCartButton = By.xpath("//input[@value = 'Add to cart']");
    private final By textOnCartIcon = By.xpath("//span[@class= 'cart-qty']");

    public P05_BooksPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectDisplayPerPage(String value) {
        Utilities.selectingFromDropDown(driver,displayDropDown, value);
    }

    public void selectSortBy(String value) {
        Utilities.selectingFromDropDown(driver,sortByDropDown,value);
    }

    public void selectViewAs(String value) {
        Utilities.selectingFromDropDown(driver,viewAsDropDown,value);
    }

    public P05_BooksPage clickAddToCartButton(){
        Utilities.clickOnElement(driver,addToCartButton);
        return this;
    }

    public String getNumberOnCartIcon(){
        return Utilities.getElementText(driver, textOnCartIcon);
    }






}
