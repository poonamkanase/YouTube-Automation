package demo;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.BeforeSuite;

public class TestCases {

    ChromeDriver driver;
    
    @BeforeSuite
    public void init()
    {
        System.out.println("Constructor: TestCases");
        WebDriverManager.chromedriver().timeout(30).setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterSuite
    public void endTest()
    {
        System.out.println("End Test: TestCases");
        //driver.close();
        //driver.quit();

    }

    @Test
    public void testCase01() throws InterruptedException
    {
        System.out.println("Start Test case: testCase01");
        driver.get("https://www.youtube.com");

        Thread.sleep(3000);

        if(driver.getCurrentUrl().contains("youtube"))
        {
            System.out.println("Successfully navigated to YouTube");
        }
        WebElement about = driver.findElement(By.xpath("//a[text()='About']"));
        about.click();

        Thread.sleep(3000);

        WebElement aboutMsg = driver.findElement(By.xpath("//section[@class = 'ytabout__content']"));
        System.out.println(aboutMsg.getText());
    }

    @Test
    public void testCase02() throws InterruptedException
    {
        System.out.println("Start Test case: testCase02");
        driver.get("https://www.youtube.com");

        Thread.sleep(3000);
        SoftAssert sa = new SoftAssert();

        WebElement movies = driver.findElement(By.xpath("//yt-formatted-string[text() = 'Movies']"));
        movies.click();

        Thread.sleep(3000);

        WebElement next = driver.findElement(By.xpath("//button[@aria-label ='Next']"));
        while(next.isDisplayed())
        {
            next.click();
            Thread.sleep(2000);
        }

        WebElement lastMovie = driver.findElement(By.xpath("(//*[@id='items']/ytd-grid-movie-renderer)[last()]"));
        WebElement rating = lastMovie.findElement(By.xpath(".//p[text()='A']"));
        String rate = rating.getText();
        sa.assertTrue(rate.contains("A"),"movie is not marked A it is not Mature");

        Thread.sleep(3000);

        WebElement genre = lastMovie.findElement(By.xpath(".//a[@class = 'yt-simple-endpoint style-scope ytd-grid-movie-renderer']/span"));
        String gnrText = genre.getText();
        sa.assertTrue(gnrText.contains("comedy") || gnrText.contains("Animation"), "Genre is neither Comedy nor Animation");

    }

    @Test
    public void testCase03() throws InterruptedException
    {
        System.out.println("Start Test case: testCase03");
        driver.get("https://www.youtube.com");

        Thread.sleep(3000);
        SoftAssert sa = new SoftAssert();

        WebElement music = driver.findElement(By.xpath("//yt-formatted-string[text() = 'Music']"));
        music.click();

        Thread.sleep(3000);
        WebElement next = driver.findElement(By.xpath("//button[@aria-label ='Next']"));
        while(next.isDisplayed())
        {
            next.click();
            Thread.sleep(1000);
        }

        WebElement lastTrackname = driver.findElement(By.xpath("//*[@id='items']/ytd-compact-station-renderer[11]/div/a/h3"));
        String name = lastTrackname.getText();
        System.out.println(name);

        WebElement tracksCount = driver.findElement(By.xpath("(//*[@id='video-count-text'])[11]"));
        String tracksCountText = tracksCount.getText();
        int tracks = Integer.parseInt(tracksCountText.replaceAll("[^0-9]", ""));
        System.out.println("Number of tracks: " + tracks);
        sa.assertTrue(tracks <= 50, "Number of tracks is not less than or equal to 50");


    }

    @Test
    public void testCase04() throws InterruptedException
    {
        System.out.println("Start Test case: testCase04");
        driver.get("https://www.youtube.com");

        Thread.sleep(3000);
        SoftAssert sa = new SoftAssert();

        WebElement news = driver.findElement(By.xpath("//yt-formatted-string[text() = 'News']"));
        news.click();

        Thread.sleep(3000);
        List<WebElement> newsList = driver.findElements(By.xpath("//*[@id='content']/ytd-post-renderer"));
        int totalLikes = 0;
        for(int i = 0; i<Math.min(3, newsList.size()); i++)
        {
            WebElement post = newsList.get(i);
            String title = post.findElement(By.xpath(".//yt-formatted-string[@id ='home-content-text']/span")).getText();
            System.out.println(title);
            String descr = post.findElement(By.xpath(".//yt-formatted-string[@id ='home-content-text']/a")).getText();
            System.out.println(descr);
            String like = post.findElement(By.xpath(".//*[@id='vote-count-middle']")).getText();
            System.out.println(like);
            int likes = 0;
            if (!like.isEmpty()) {
                likes = Integer.parseInt(like.replaceAll("[^0-9]", ""));
            }
            totalLikes = totalLikes + likes;
        }
        System.out.println("Total Likes " +totalLikes);

    }

    @Test(dataProvider = "searchOptions", dataProviderClass = ExternalDataProvider.class)
    public void testCase05(String keywords) throws InterruptedException
    {
        System.out.println("Start Test case: testCase05");
        driver.get("https://www.youtube.com");

        Thread.sleep(3000);
        
        WebElement searchBox = driver.findElement(By.xpath("//input[@id ='search']"));
        searchBox.sendKeys(keywords);
        Thread.sleep(2000);
        WebElement submt = driver.findElement(By.xpath("//button[@id='search-icon-legacy']"));
        submt.click();
        Thread.sleep(3000);

        
    }
    
}
