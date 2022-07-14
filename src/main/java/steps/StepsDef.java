package steps;

import cucumber.api.java.ru.*;
import steps.Steps;
import test.SberMainPage;
import test.SberSearchResultsPage;

import static java.lang.Integer.parseInt;

public class StepsDef extends Steps {

    private static SberMainPage mainPage;
    private static SberSearchResultsPage searchPage;

    @Пусть("пользователь запускает Google Chrome")
    public void пользователь_запускает_Google_Chrome() {
        открытьХром();
    }

    @Пусть("открывает сайт {string}")
    public void открывает_сайт(String url) {
        webDriver.get(url);
    }

    @Затем("вводит в поле поиска {string}")
    public void вводит_в_поле_поиска(String searchQuery) {
        mainPage = new SberMainPage(webDriver);
        mainPage.webElementClick(mainPage.getSearchField());
        mainPage.sendKeys(searchQuery);
    }

    @Затем("нажимает поиск")
    public void нажимает_поиск() {
        mainPage.webElementClick(mainPage.getSearchButton());
    }

    @Тогда("задаем количество результатов поиска на страницу {string}")
    public void задаемКоличествоРезультатовПоискаНаСтраницу(String resultsPerPage) {
        searchPage = new SberSearchResultsPage(webDriver, resultsPerPage);
        searchPage.webElementClick(searchPage.getResultsPerPage(), searchPage.getOptionResultsPerPage());
        searchPage.webElementClick(searchPage.getOptionResultsPerPage());
    }

    @Также("проверяем первые {string} результатов, смотрим название, цену и номер")
    public void проверяемПервыеРезультатовСмотримНазваниеЦенуИНомерПервыхРезультатовСЦенойВыше(String allResultsAmount) {
        searchPage.setPagesAmount(parseInt(allResultsAmount), searchPage.getResultsPerPageAmount());
        searchPage.setTenders(searchPage.getPagesAmount());
    }

    @Тогда("выводим в Allure и консоль название, цену и номер первых {string} результатов с ценой выше {string} и типом {string}")
    public void выводим_информацию_в_Allure_и_консоль(String filteredResultsAmount, String price, String type) {
        searchPage.getFilteredTenders(parseInt(filteredResultsAmount), parseInt(price), type);
    }

    @Тогда("закрываем Google Chrome")
    public void закрываем_Google_Chrome() {
        закрытьХром();
    }

}
