import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;



public class BIP_Automation {


    public static void main(String[] args) throws InterruptedException {


        // File file = new File("D:/Dev/ReadData/src/datafile.properties");
        File file = new File("input.properties");
	  
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties prop = new Properties();
		
		//load properties file
		try {
			prop.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}


        var URL = prop.getProperty("URL");

        System.out.println(URL);

        System.setProperty("webdriver.gecko.driver","geckodriver.exe");
        WebDriver driver;


        try {
            driver=create();
            // open(driver);
            open(driver,URL);
        }
        catch (Exception e){

//            Print only the first line of exception
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            String[] lines = writer.toString().split("\n");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < Math.min(lines.length, 1); i++) {
                sb.append(lines[i]).append("\n");
            }

//            this now contains the firstline of the error
//            System.out.println(sb.toString());


            try {

//                write the error in log file
                FileWriter myWriter = new FileWriter(".\\LOG\\LOGFILE.txt");
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
//                System.out.println(dateFormat.format(date));
                myWriter.write(dateFormat.format(date)+"\n"+sb.toString());
                myWriter.close();
                System.out.println("Successfully wrote to the file.");

//            String command_bat="cmd.exe /c cd C:\\\\Users\\\\SADMIN\\\\Desktop\\\\comm & restart_cti.bat";
                String version_mismatch="cmd.exe /c putty -l sadmin -pw siebdev99 -load \"halsey03\" -m version_mismatch.txt";
                Runtime.getRuntime().exec(version_mismatch);


            } catch (IOException ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            }

        }

    }


    public static WebDriver create()
    {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }


    // public static void open(WebDriver driver) throws IOException {
    public static void open(WebDriver driver, String URL) throws IOException {


        try
        {

            // driver.get("https://snja62.sa.state.nj.us:7002/xmlpserver");
            driver.get(URL);
//            driver.get("http://localhost:3000");

            boolean loaded = false;
            String pageName = driver.getTitle();
//            System.out.println(pageName);

            if(pageName.equals("Oracle BI Publisher Enterprise Login")){
                loaded = true;
            }

            if(loaded) {
                send_mail(true);

            }
            else
            {
                send_mail(false);

            }


            driver.close();

            ///// KILL CHROMEDRIVER PROCESS ///
            try {
                killProcess("chromedriver.exe");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
        catch (Exception e) {
            // TODO: handle exception
            System.out.println("couldn't open"+e.getMessage());
            //driver.close();


//            Print only the first line of exception
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            String[] lines = writer.toString().split("\n");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < Math.min(lines.length, 1); i++) {
                sb.append(lines[i]).append("\n");
            }
//            write the error in log file
            FileWriter myWriter = new FileWriter(".\\LOG\\LOGFILE.txt");
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
//                System.out.println(dateFormat.format(date));
            myWriter.write(dateFormat.format(date)+"\n"+sb.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");

//            String command_bat="cmd.exe /c cd C:\\\\Users\\\\SADMIN\\\\Desktop\\\\comm & restart_cti.bat";
            String version_mismatch="cmd.exe /c putty -l sadmin -pw siebdev99 -load \"halsey03\" -m connection_timeout.txt";
            Runtime.getRuntime().exec(version_mismatch);


        }


    }



    public static void send_mail(boolean loaded)
    {
        try {

//            String command_bat="cmd.exe /c cd C:\\\\Users\\\\SADMIN\\\\Desktop\\\\comm & restart_cti.bat";
              String command_bat_loaded="cmd.exe /c putty -l sadmin -pw siebdev99 -load \"halsey03\" -m loaded.txt";
              String command_bat_failed="cmd.exe /c putty -l sadmin -pw siebdev99 -load \"halsey03\" -m failed.txt";


            //System.out.println(command);

            if(loaded)
            {
//                write success in log file
                FileWriter myWriter = new FileWriter(".\\LOG\\LOGFILE.txt");
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
//                System.out.println(dateFormat.format(date));
                myWriter.write(dateFormat.format(date)+"\n"+"BIP URL successfully loaded");
                myWriter.close();
                System.out.println("Successfully wrote to the file.");

                Runtime.getRuntime().exec(command_bat_loaded);
            }

            else
            {
//                write failure in log file
                FileWriter myWriter = new FileWriter(".\\LOG\\LOGFILE.txt");
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
//                System.out.println(dateFormat.format(date));
                myWriter.write(dateFormat.format(date)+"\n"+"BIP URL could not be loaded");
                myWriter.close();
                System.out.println("Successfully wrote to the file.");

                Runtime.getRuntime().exec(command_bat_failed);
            }


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }



    public static void killProcess(String serviceName) throws Exception {


        Runtime.getRuntime().exec("taskkill /F /IM " + serviceName);

    }

}
