package top.whitecola.promodule.services.login.microsoft;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class MicrosoftAccount implements Account{
    private String name;
    private String accessToken;
    private String refreshToken;
    private UUID uuid;

    public MicrosoftAccount(String name, String accessToken, String refreshToken, UUID uuid) {
        this.name = name;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.uuid = uuid;
    }


    
    public UUID uuid() { return this.uuid; }

    public String name() { return this.name; }

    public String accessToken() { return this.accessToken; }



    public String refreshToken() { return this.refreshToken; }

    public CompletableFuture<AuthData> login(BiConsumer<String, Object[]> progressHandler) {
        CompletableFuture<Account.AuthData> cf = new CompletableFuture<Account.AuthData>();
        LoginScreen.EXECUTOR.execute(() -> {
            try {
                refresh(progressHandler);
                cf.complete(new Account.AuthData(this.name, this.uuid, this.accessToken, "msa"));
            } catch (Throwable t) {
                System.out.println("Unable to login/refresh Microsoft account.");
                cf.completeExceptionally(t);
            }
        });
        return cf;
    }






    private void refresh( BiConsumer<String, Object[]> progressHandler) throws Exception {
        try {
            System.out.println("Refreshing...");
            progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "getProfile" });
            Map.Entry<UUID, String> profile = Auth.getProfile(this.accessToken);
            System.out.println("Access token is valid.");
            this.uuid = (UUID)profile.getKey();
            this.name = (String)profile.getValue();
        } catch (Exception e) {
            try {
                System.out.println("Step: refreshToken.");
                progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "refreshToken" });
                Map.Entry<String, String> authRefreshTokens = Auth.refreshToken(this.refreshToken);
                String refreshToken = (String)authRefreshTokens.getValue();
                System.out.println("Step: authXBL.");

                progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "authXBL" });
                String xblToken = Auth.authXBL((String)authRefreshTokens.getKey());
                System.out.println("Step: authXSTS.");

                progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "authXSTS" });
                Map.Entry<String, String> xstsTokenUserhash = Auth.authXSTS(xblToken);

                System.out.println("Step: authMinecraft.");

                progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "authMinecraft" });
                String accessToken = Auth.authMinecraft((String)xstsTokenUserhash.getValue(), (String)xstsTokenUserhash.getKey());
                System.out.println("Step: getProfile.");
                progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "getProfile" });
                Map.Entry<UUID, String> profile = Auth.getProfile(accessToken);
                System.out.println("Refreshed.");
                this.uuid = (UUID)profile.getKey();
                this.name = (String)profile.getValue();
                this.accessToken = accessToken;
                this.refreshToken = refreshToken;
            } catch (Exception ex) {
                ex.addSuppressed(e);
                throw ex;
            }
        }
    }
}
