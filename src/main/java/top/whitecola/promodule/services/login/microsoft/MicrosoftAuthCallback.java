package top.whitecola.promodule.services.login.microsoft;

import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;


public class MicrosoftAuthCallback implements Closeable {
//    public static final String MICROSOFT_AUTH_URL = "https://login.live.com/oauth20_authorize.srf?client_id=54fd49e4-2103-4044-9603-2b028c814ec3&response_type=code&scope=XboxLive.signin%20XboxLive.offline_access&redirect_uri=http://localhost:59125&prompt=select_account";
//    private HttpServer server;
//
//
//    public CompletableFuture<MicrosoftAccount> start(BiConsumer<String, Object[]> progressHandler, String done) {
//        CompletableFuture<MicrosoftAccount> cf = new CompletableFuture<MicrosoftAccount>();
//        try {
//            this.server = HttpServer.create(new InetSocketAddress("localhost", 59125), 0);
//            this.server.createContext("/", ex -> {
//                System.out.println("Microsoft authentication callback request: " + ex.getRemoteAddress());
//                try (BufferedReader in = new BufferedReader(new InputStreamReader(MicrosoftAuthCallback.class
//                        .getResourceAsStream("/authPage.html"), StandardCharsets.UTF_8))) {
//                    progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "preparing" });
//                    byte[] b = in.lines().collect(Collectors.joining("\n")).replace("%message%", done).getBytes(StandardCharsets.UTF_8);
//                    ex.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
//                    ex.sendResponseHeaders(307, b.length);
//                    try (OutputStream os = ex.getResponseBody()) {
//                        os.write(b);
//                    }
//                    close();
//                    MicrosoftLogin.EXECUTOR.execute(());
//
//
//
//
//
//
//                }
//                catch (Throwable t) {
//                    SharedIAS.LOG.error("Unable to process request on Microsoft authentication callback server.", t);
//                    close();
//                    cf.completeExceptionally(t);
//                }
//            });
//            this.server.start();
//            SharedIAS.LOG.info("Started Microsoft authentication callback server.");
//        } catch (Throwable t) {
//            SharedIAS.LOG.error("Unable to run the Microsoft authentication callback server.", t);
//            close();
//            cf.completeExceptionally(t);
//        }
//        return cf;
//    }
//
//    
//    private MicrosoftAccount auth( BiConsumer<String, Object[]> progressHandler,  String query) throws Exception {
//        SharedIAS.LOG.info("Authenticating...");
//        if (query == null) throw new NullPointerException("query=null");
//        if (query.equals("error=access_denied&error_description=The user has denied access to the scope requested by the client application.")) return null;
//        if (!query.startsWith("code=")) throw new IllegalStateException("query=" + query);
//        SharedIAS.LOG.info("Step: codeToToken.");
//        progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "codeToToken" });
//        Map.Entry<String, String> authRefreshTokens = Auth.codeToToken(query.replace("code=", ""));
//        String refreshToken = (String)authRefreshTokens.getValue();
//        SharedIAS.LOG.info("Step: authXBL.");
//        progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "authXBL" });
//        String xblToken = Auth.authXBL((String)authRefreshTokens.getKey());
//        SharedIAS.LOG.info("Step: authXSTS.");
//        progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "authXSTS" });
//        Map.Entry<String, String> xstsTokenUserhash = Auth.authXSTS(xblToken);
//        SharedIAS.LOG.info("Step: authMinecraft.");
//        progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "authMinecraft" });
//        String accessToken = Auth.authMinecraft((String)xstsTokenUserhash.getValue(), (String)xstsTokenUserhash.getKey());
//        SharedIAS.LOG.info("Step: getProfile.");
//        progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "getProfile" });
//        Map.Entry<UUID, String> profile = Auth.getProfile(accessToken);
//        SharedIAS.LOG.info("Authenticated.");
//        return new MicrosoftAccount((String)profile.getValue(), accessToken, refreshToken, (UUID)profile.getKey());
//    }
//
//
//    public void close() {
//        try {
//            if (this.server != null) {
//                this.server.stop(0);
//                SharedIAS.LOG.info("Stopped Microsoft authentication callback server.");
//            }
//        } catch (Throwable t) {
//            SharedIAS.LOG.error("Unable to stop the Microsoft authentication callback server.", t);
//        }
//    }

    public static final String MICROSOFT_AUTH_URL = "https://login.live.com/oauth20_authorize.srf?client_id=54fd49e4-2103-4044-9603-2b028c814ec3&response_type=code&scope=XboxLive.signin%20XboxLive.offline_access&redirect_uri=http://localhost:59125&prompt=select_account";
    private HttpServer server;

    
    public CompletableFuture<MicrosoftAccount> start( final BiConsumer<String, Object[]> progressHandler,  final String done) {
        CompletableFuture<MicrosoftAccount> cf = new CompletableFuture<MicrosoftAccount>();
        try {

            this.server = HttpServer.create(new InetSocketAddress("localhost", 59125), 0);
            this.server.createContext("/", ex -> {
                System.out.println("Microsoft authentication callback request: " + ex.getRemoteAddress());
                try (BufferedReader in = new BufferedReader(new InputStreamReader(MicrosoftAuthCallback.class
                        .getResourceAsStream("/authPage.html"), StandardCharsets.UTF_8))) {
                    progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "preparing" });
                    byte[] b = ((String)in.lines().collect(Collectors.joining("\n"))).replace("%message%", done).getBytes(StandardCharsets.UTF_8);
                    ex.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
                    ex.sendResponseHeaders(307, b.length);
                    try (OutputStream os = ex.getResponseBody()) {
                        os.write(b);
                    }
                    close();

                    LoginScreen.EXECUTOR.execute(() -> {

                        try {
                            cf.complete(this.auth(progressHandler, ex.getRequestURI().getQuery()));
                        }
                        catch (Throwable t) {
                            System.out.println("Unable to authenticate via Microsoft.");
                            cf.completeExceptionally(t);
                        }
                        return;
                    });






                }
                catch (Throwable t) {
                    System.out.println("Unable to process request on Microsoft authentication callback server.");
                    close();
                    cf.completeExceptionally(t);
                }
            });
            this.server.start();
            System.out.println("Started Microsoft authentication callback server.");
        } catch (Throwable t) {
            System.out.println("Unable to run the Microsoft authentication callback server.");
            close();
            cf.completeExceptionally(t);
        }
        return cf;
    }

    private MicrosoftAccount auth( final BiConsumer<String, Object[]> progressHandler, final String query) throws Exception {
        System.out.println("Authenticating...");
        if (query == null) {
            throw new NullPointerException("query=null");
        }
        if (query.equals("error=access_denied&error_description=The user has denied access to the scope requested by the client application.")) {
            return null;
        }
        if (!query.startsWith("code=")) {
            throw new IllegalStateException("query=" + query);
        }
        System.out.println("Step: codeToToken.");

        progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "codeToToken" });
        final Map.Entry<String, String> authRefreshTokens = Auth.codeToToken(query.replace("code=", ""));
        final String refreshToken = authRefreshTokens.getValue();
        System.out.println("Step: authXBL.");
        progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "authXBL" });
        final String xblToken = Auth.authXBL(authRefreshTokens.getKey());
        System.out.println("Step: authXSTS.");
        progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "authXSTS" });
        final Map.Entry<String, String> xstsTokenUserhash = Auth.authXSTS(xblToken);
        System.out.println("Step: authMinecraft.");
        progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "authMinecraft" });
        final String accessToken = Auth.authMinecraft(xstsTokenUserhash.getValue(), xstsTokenUserhash.getKey());
        System.out.println("Step: getProfile.");
        progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "getProfile" });
        final Map.Entry<UUID, String> profile = Auth.getProfile(accessToken);
        System.out.println("Authenticated.");
        return new MicrosoftAccount(profile.getValue(), accessToken, refreshToken, profile.getKey());
    }

    @Override
    public void close() {
        try {
            if (this.server != null) {
                this.server.stop(0);
                System.out.println("Stopped Microsoft authentication callback server.");
            }
        }
        catch (Throwable t) {
            System.out.println("Unable to stop the Microsoft authentication callback server.");
        }
    }
    
}
