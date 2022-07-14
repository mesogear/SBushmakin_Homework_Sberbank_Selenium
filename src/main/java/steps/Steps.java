package steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class Steps {

    /** Поле веб-драйвера */
    protected WebDriver webDriver;

    /**
     * Метод открывает браузер Хром на полный размер окна и устанавливает ожидание
     * @author SBushmakin
     */
    public void открытьХром(){
        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        webDriver.manage().window().maximize();
    }

    /**
     * Метод закрывает браузер
     * @author SBushmakin
     */
    public void закрытьХром(){
        webDriver.close();
    }

}