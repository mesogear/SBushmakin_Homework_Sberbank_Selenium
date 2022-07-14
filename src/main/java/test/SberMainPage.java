package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/** Класс стартовой страницы Сбербанк-АСТ */

public class SberMainPage {

    /** Поле веб-драйвера */
    protected WebDriver webDriver;

    /** Веб-элемент поля поиска */
    protected WebElement searchField;

    /** Веб-элемент кнопки поиска */
    protected WebElement searchButton;

    /**
     * Конструктор класса, принимает веб-драйвер и создает объект
     * @author SBushmakin
     * @param webDriver принимает веб-драйвер
     */
    public SberMainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.searchField = webDriver.findElement(By.xpath("//input[@id='txtUnitedPurchaseSearch']"));
        this.searchButton = webDriver.findElement(By.xpath("//input[@id='btnUnitedPurchaseSearch']"));
    }

    /**
     * Метод принимает веб-элемент и кликает по нему
     * @author SBushmakin
     * @param webElement веб-элемент
     */
    public void webElementClick(WebElement webElement) {
        webElement.click();
    }

    /**
     * Метод принимает строку с текстом, которую потом печатает веб-драйвер
     * @author SBushmakin
     * @param keys строка с текстом
     */
    public void sendKeys(String keys) {
        Actions action = new Actions(webDriver);
        action.sendKeys(keys).build().perform();
    }

    /**
     * Метод возвращает веб-элемент поля поиска
     *
     * @author SBushmakin
     * @return возвращает веб-элемент поля поиска
     */
    public WebElement getSearchField() {
        return searchField;
    }

    /**
     * Метод возвращает веб-элемент кнопки поиска
     *
     * @author SBushmakin
     * @return возвращает веб-элемент кнопки поиска
     */
    public WebElement getSearchButton() {
        return searchButton;
    }

}
