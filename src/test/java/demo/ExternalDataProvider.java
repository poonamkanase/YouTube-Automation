package demo;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class ExternalDataProvider {

    @DataProvider(name ="searchOptions")
    public Object [][] qtripData () throws IOException{
        return Dp.dpMethod("Testcase05");

    }
    
}
