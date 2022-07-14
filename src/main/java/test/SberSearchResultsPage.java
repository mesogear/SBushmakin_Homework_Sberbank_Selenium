package test;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/** Класс страницы Сбербанк-АСТ с результатами поиска */

public class SberSearchResultsPage {

    /** Поле веб-драйвера */
    protected WebDriver webDriver;

    /** Кнопка выбора количества результатов поиска на странице */
    protected WebElement resultsPerPage;

    /** Опция количества результатов поиска на странице */
    protected WebElement optionResultsPerPage;

    /** Количество страниц поиска */
    protected int pagesAmount;

    /** Список названий заказов */
    protected List<String> names = new ArrayList<>();

    /** Список номеров заказов */
    protected List<String> numbers = new ArrayList<>();

    /** Список стоимости заказов */
    protected List<String> prices = new ArrayList<>();

    /** Список типов заказов */
    protected List<String> types = new ArrayList<>();

    /**
     * Конструктор класса, принимает веб-драйвер и создает объект
     *
     * @param webDriver веб-драйвер
     * @author SBushmakin
     */
    public SberSearchResultsPage(WebDriver webDriver, String amount) {
        this.webDriver = webDriver;
        this.resultsPerPage = webDriver.findElement(By.xpath("//select[@id='headerPagerSelect']"));
        this.optionResultsPerPage = webDriver.findElement(By.xpath("//select[@id='headerPagerSelect']/option[contains( . , '" + amount + "')]"));
    }

    /**
     * Метод принимает веб-элемент и кликает по нему
     *
     * @param webElement веб-элемент
     * @author SBushmakin
     */
    public void webElementClick(WebElement webElement) {
        webElement.click();
    }

    /**
     * Метод принимает два веб-элемента, кликает по первому и вызывает
     * метод ожидания доступности клика для второго
     *
     * @param clickNow веб-элемент, по которому нужно кликнуть сейчас
     * @param clickAfter веб-элемент, по которому нужно кликнуть потом
     * @author SBushmakin
     */
    public void webElementClick(WebElement clickNow, WebElement clickAfter) {
        clickNow.click();
        waitClickableElement(clickAfter);
    }

    /**
     * Метод возвращает количество результатов на страницу в виде целого числа
     *
     * @author SBushmakin
     * @return возвращает количество результатов на страницу в виде целого числа
     */
    public int getResultsPerPageAmount() {
        return parseInt(this.optionResultsPerPage.getText());
    }

    /**
     * Метод принимает значение с количеством результатов, которые необходимо
     * проверить, значение с количеством результатов на странице и
     * находит число страниц
     *
     * @param allResults количество результатов для проверки
     * @param resultsPerPage количество результатов на странице
     * @author SBushmakin
     */
    public void setPagesAmount(int allResults, int resultsPerPage) {
        this.pagesAmount = allResults / resultsPerPage;
    }

    /**
     * Метод принимает веб-элемент и ожидает, когда он будет доступен для клика
     *
     * @param webElement веб-элемент
     * @author SBushmakin
     */
    public void waitClickableElement(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='loading']")));
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    /**
     * Метод принимает количество страниц для проверки и заполняет списки с
     * данными результатов поиска
     *
     * @param pagesAmount количество страниц для проверки
     * @author SBushmakin
     */
    public void setTenders(int pagesAmount) {
        for (int i = 0; i < pagesAmount; i++) {
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='loading']")));
            webDriver.findElements(By.xpath("//div[@class='es-el-type-name']")).forEach(x -> names.add(x.getText()));
            webDriver.findElements(By.xpath("//span[@class='es-el-code-term']")).forEach(x -> numbers.add(x.getText()));
            webDriver.findElements(By.xpath("//span[@class='es-el-source-term']")).forEach(x -> types.add(x.getText()));
            webDriver.findElements(By.xpath("//span[@class='es-el-amount']")).forEach(x -> prices.add(x.getText().replace(" ", "")));
            webDriver.findElement(By.xpath("//div[@id='footerPager']//span[@id='pageButton']/span[text()='>']")).click();
        }
    }

    /**
     * Метод принимает количество заказов, которые нужно вывести, их тип и миниальную стоимость,
     * затем выводит название, номер и цену подходящих заказов в консоль, Allure-отчет
     * и добавляет в Allure-отчет json c найденными данными
     *
     * @param filteredResultsAmount количество заказов для вывода
     * @param price минимальная стоимость
     * @param type тип заказа
     * @author SBushmakin
     */
    public void getFilteredTenders(int filteredResultsAmount, int price, String type) {
        StringBuilder formattedResult = new StringBuilder();
        for (int i = 0; i < prices.size(); i++) {
            if (!prices.get(i).isEmpty() && (parseDouble(prices.get(i)) > price)
            && types.get(i).equals(type)) {
                String filteredResult = "Название: " + names.get(i) + " Номер: " + numbers.get(i) + " Цена: " + prices.get(i);
                System.out.println(filteredResult);
                Allure.addAttachment("Информация о заказе", filteredResult);
                formattedResult.append("{\"Название\": \"").append(names.get(i)).append("\",\n\"Номер\": \"").append(numbers.get(i)).append("\",\n\"Цена\": ").append(prices.get(i)).append("}\n");
                filteredResultsAmount--;
                if (filteredResultsAmount == 0) {
                    break;
                }
            }
        }
        Allure.addAttachment("Информация о заказах в формате json", "application/json", formattedResult.toString());
    }

    /**
     * Метод возвращает веб-элемент кнопки выбора количества результатов поиска на странице
     *
     * @author SBushmakin
     * @return возвращает веб-элемент кнопки выбора количества результатов поиска на странице
     */
    public WebElement getResultsPerPage() {
        return resultsPerPage;
    }

    /**
     * Метод возвращает веб-элемент количества результатов поиска на странице
     *
     * @author SBushmakin
     * @return возвращает веб-элемент количества результатов поиска на странице
     */
    public WebElement getOptionResultsPerPage() {
        return optionResultsPerPage;
    }

    /**
     * Метод возвращает количество страниц, которые надо проверить
     *
     * @author SBushmakin
     * @return возвращает количество страниц, которые надо проверить
     */
    public int getPagesAmount() {
        return pagesAmount;
    }
}