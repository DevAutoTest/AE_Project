package api.extencions;

import api.tokens.GetUserToken;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class BeforeAll implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext extensionContext)  {

        GetUserToken.token();

    }
}
