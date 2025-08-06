package api.extensions;

import api.tokens.GetUserToken;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Common Extension, in first step warms Akamai, than gets guest-token.
 */
public final class BeforeAll implements BeforeAllCallback {

    private final AkamaiWarmUpExtension warmUp = new AkamaiWarmUpExtension();

    @Override
    public void beforeAll(ExtensionContext ctx) throws Exception {
        warmUp.beforeAll(ctx);
        GetUserToken.token();
    }
}