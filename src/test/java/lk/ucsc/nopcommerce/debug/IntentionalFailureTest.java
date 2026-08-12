package lk.ucsc.nopcommerce.debug;

import org.testng.Assert;
import org.testng.annotations.Test;

/** Regression test retained after the assignment's controlled debugging exercise. */
public final class IntentionalFailureTest {
    @Test
    public void loginErrorContractIsRecognised() {
        String capturedLoginError = "Login was unsuccessful. Please correct the errors and try again.";

        Assert.assertTrue(capturedLoginError.contains("Login was unsuccessful"),
                "The generic unsuccessful-login contract should be recognised");
    }
}
