package api.extensions;

import api.tokens.GetUserToken;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Общий Extension, который сначала греет Akamai, потом получает guest-token.
 */
public final class BeforeAll implements BeforeAllCallback {

    private final AkamaiWarmUpExtension warmUp = new AkamaiWarmUpExtension();

    @Override
    public void beforeAll(ExtensionContext ctx) throws Exception {
        warmUp.beforeAll(ctx);      // ① прогрев
        GetUserToken.token();       // ② lazy-кеш guest-token
    }
}