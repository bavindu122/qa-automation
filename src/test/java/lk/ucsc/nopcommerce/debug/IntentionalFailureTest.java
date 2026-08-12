package lk.ucsc.nopcommerce.debug;

import org.testng.Assert;
import org.testng.annotations.Test;

/** Temporary test used only for the assignment's controlled debugging exercise. */
public final class IntentionalFailureTest {
    @Test
    public void loginErrorContractIsRecognised() {
        String capturedLoginError = "Login was unsuccessful. Please correct the errors and try again.";

        Assert.assertTrue(capturedLoginError.contains("No customer account found"),
                "Intentional failure: the expected message does not match the captured contract");
    }
}
