package lk.ucsc.nopcommerce.listeners;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lk.ucsc.nopcommerce.base.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

/** Captures a PNG at the point of failure without hiding the original failure. */
public final class TestFailureListener implements ITestListener {
    private static final DateTimeFormatter TIMESTAMP =
            DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    @Override
    public void onTestFailure(ITestResult result) {
        Object instance = result.getInstance();
        if (!(instance instanceof BaseTest test)) {
            return;
        }

        try {
            byte[] screenshot = ((TakesScreenshot) test.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            Path directory = Path.of("screenshots", "failures");
            Files.createDirectories(directory);
            String filename = result.getMethod().getMethodName() + "-"
                    + LocalDateTime.now().format(TIMESTAMP) + ".png";
            Files.write(directory.resolve(filename), screenshot);
        } catch (IOException | RuntimeException exception) {
            System.err.println("Unable to capture failure screenshot: " + exception.getMessage());
        }
    }
}

