package top.whitecola.promodule.services.login.microsoft;

import java.nio.charset.*;
import java.util.stream.*;
import com.google.gson.*;
import java.net.*;
import java.security.cert.*;
import java.security.*;
import javax.net.ssl.*;
import java.util.*;
import java.io.*;

public class Auth {
    private static final String CLIENT_ID = "54fd49e4-2103-4044-9603-2b028c814ec3";
    private static final String REDIRECT_URI = "http://localhost:59125";
    private static final boolean BLIND_SSL;
    private static final boolean NO_CUSTOM_SSL;
    public static final SSLContext FIXED_CONTEXT;

    public static Map.Entry<String, String> codeToToken(final String code) throws Exception {
        final HttpsURLConnection conn = (HttpsURLConnection)new URL("https://login.live.com/oauth20_token.srf").openConnection();
        if (Auth.FIXED_CONTEXT != null) {
            conn.setSSLSocketFactory(Auth.FIXED_CONTEXT.getSocketFactory());
        }
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(15000);
        conn.setDoOutput(true);
        try (final OutputStream out = conn.getOutputStream()) {
            out.write(("client_id=" + URLEncoder.encode("54fd49e4-2103-4044-9603-2b028c814ec3", "UTF-8") + "&code=" + URLEncoder.encode(code, "UTF-8") + "&grant_type=authorization_code&redirect_uri=" + URLEncoder.encode("http://localhost:59125", "UTF-8") + "&scope=XboxLive.signin%20XboxLive.offline_access").getBytes(StandardCharsets.UTF_8));
            Label_0326: {
                if (conn.getResponseCode() >= 200) {
                    if (conn.getResponseCode() <= 299) {
                        break Label_0326;
                    }
                }
                try (final BufferedReader err = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    throw new IllegalArgumentException("codeToToken response: " + conn.getResponseCode() + ", data: " + err.lines().collect(Collectors.joining("\n")));
                }
                catch (Throwable t) {
                    throw new IllegalArgumentException("codeToToken response: " + conn.getResponseCode(), t);
                }
            }
            try (final BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                final JsonObject resp = (JsonObject)Constant.GSON.fromJson((String)in.lines().collect(Collectors.joining("\n")), (Class)JsonObject.class);
                return new AbstractMap.SimpleImmutableEntry<String, String>(resp.get("access_token").getAsString(), resp.get("refresh_token").getAsString());
            }
        }
    }

    public static Map.Entry<String, String> refreshToken(final String refreshToken) throws Exception {
        final HttpsURLConnection conn = (HttpsURLConnection)new URL("https://login.live.com/oauth20_token.srf").openConnection();
        if (Auth.FIXED_CONTEXT != null) {
            conn.setSSLSocketFactory(Auth.FIXED_CONTEXT.getSocketFactory());
        }
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(15000);
        conn.setDoOutput(true);
        try (final OutputStream out = conn.getOutputStream()) {
            out.write(("client_id=" + URLEncoder.encode("54fd49e4-2103-4044-9603-2b028c814ec3", "UTF-8") + "&refresh_token=" + URLEncoder.encode(refreshToken, "UTF-8") + "&grant_type=refresh_token&redirect_uri=" + URLEncoder.encode("http://localhost:59125", "UTF-8") + "&scope=XboxLive.signin%20XboxLive.offline_access").getBytes(StandardCharsets.UTF_8));
            Label_0328: {
                if (conn.getResponseCode() >= 200) {
                    if (conn.getResponseCode() <= 299) {
                        break Label_0328;
                    }
                }
                try (final BufferedReader err = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    throw new IllegalArgumentException("refreshToken response: " + conn.getResponseCode() + ", data: " + err.lines().collect(Collectors.joining("\n")));
                }
                catch (Throwable t) {
                    throw new IllegalArgumentException("refreshToken response: " + conn.getResponseCode(), t);
                }
            }
            try (final BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                final JsonObject resp = (JsonObject)Constant.GSON.fromJson((String)in.lines().collect(Collectors.joining("\n")), (Class)JsonObject.class);
                return new AbstractMap.SimpleImmutableEntry<String, String>(resp.get("access_token").getAsString(), resp.get("refresh_token").getAsString());
            }
        }
    }

    
    public static String authXBL(final String authToken) throws Exception {
        final HttpsURLConnection conn = (HttpsURLConnection)new URL("https://user.auth.xboxlive.com/user/authenticate").openConnection();
        if (Auth.FIXED_CONTEXT != null) {
            conn.setSSLSocketFactory(Auth.FIXED_CONTEXT.getSocketFactory());
        }
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(15000);
        conn.setDoOutput(true);
        try (final OutputStream out = conn.getOutputStream()) {
            final JsonObject req = new JsonObject();
            final JsonObject reqProps = new JsonObject();
            reqProps.addProperty("AuthMethod", "RPS");
            reqProps.addProperty("SiteName", "user.auth.xboxlive.com");
            reqProps.addProperty("RpsTicket", "d=" + authToken);
            req.add("Properties", (JsonElement)reqProps);
            req.addProperty("RelyingParty", "http://auth.xboxlive.com");
            req.addProperty("TokenType", "JWT");
            out.write(req.toString().getBytes(StandardCharsets.UTF_8));
            Label_0386: {
                if (conn.getResponseCode() >= 200) {
                    if (conn.getResponseCode() <= 299) {
                        break Label_0386;
                    }
                }
                try (final BufferedReader err = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    throw new IllegalArgumentException("authXBL response: " + conn.getResponseCode() + ", data: " + err.lines().collect(Collectors.joining("\n")));
                }
                catch (Throwable t) {
                    throw new IllegalArgumentException("authXBL response: " + conn.getResponseCode(), t);
                }
            }
            try (final BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                final JsonObject resp = (JsonObject)Constant.GSON.fromJson((String)in.lines().collect(Collectors.joining("\n")), (Class)JsonObject.class);
                return resp.get("Token").getAsString();
            }
        }
    }

    public static Map.Entry<String, String> authXSTS(final String xblToken) throws Exception {
        final HttpsURLConnection conn = (HttpsURLConnection)new URL("https://xsts.auth.xboxlive.com/xsts/authorize").openConnection();
        if (Auth.FIXED_CONTEXT != null) {
            conn.setSSLSocketFactory(Auth.FIXED_CONTEXT.getSocketFactory());
        }
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(15000);
        conn.setDoOutput(true);
        try (final OutputStream out = conn.getOutputStream()) {
            final JsonObject req = new JsonObject();
            final JsonObject reqProps = new JsonObject();
            final JsonArray userTokens = new JsonArray();
            userTokens.add((JsonElement)new JsonPrimitive(xblToken));
            reqProps.add("UserTokens", (JsonElement)userTokens);
            reqProps.addProperty("SandboxId", "RETAIL");
            req.add("Properties", (JsonElement)reqProps);
            req.addProperty("RelyingParty", "rp://api.minecraftservices.com/");
            req.addProperty("TokenType", "JWT");
            out.write(req.toString().getBytes(StandardCharsets.UTF_8));
            Label_0379: {
                if (conn.getResponseCode() >= 200) {
                    if (conn.getResponseCode() <= 299) {
                        break Label_0379;
                    }
                }
                try (final BufferedReader err = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    throw new IllegalArgumentException("authXSTS response: " + conn.getResponseCode() + ", data: " + err.lines().collect(Collectors.joining("\n")));
                }
                catch (Throwable t) {
                    throw new IllegalArgumentException("authXSTS response: " + conn.getResponseCode(), t);
                }
            }
            try (final BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                final JsonObject resp = (JsonObject)Constant.GSON.fromJson((String)in.lines().collect(Collectors.joining("\n")), (Class)JsonObject.class);
                return new AbstractMap.SimpleImmutableEntry<String, String>(resp.get("Token").getAsString(), resp.getAsJsonObject("DisplayClaims").getAsJsonArray("xui").get(0).getAsJsonObject().get("uhs").getAsString());
            }
        }
    }

    
    public static String authMinecraft(final String userHash, final String xstsToken) throws Exception {
        final HttpsURLConnection conn = (HttpsURLConnection)new URL("https://api.minecraftservices.com/authentication/login_with_xbox").openConnection();
        if (Auth.FIXED_CONTEXT != null) {
            conn.setSSLSocketFactory(Auth.FIXED_CONTEXT.getSocketFactory());
        }
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(15000);
        conn.setDoOutput(true);
        try (final OutputStream out = conn.getOutputStream()) {
            final JsonObject req = new JsonObject();
            req.addProperty("identityToken", "XBL3.0 x=" + userHash + ";" + xstsToken);
            out.write(req.toString().getBytes(StandardCharsets.UTF_8));
            Label_0334: {
                if (conn.getResponseCode() >= 200) {
                    if (conn.getResponseCode() <= 299) {
                        break Label_0334;
                    }
                }
                try (final BufferedReader err = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    throw new IllegalArgumentException("authMinecraft response: " + conn.getResponseCode() + ", data: " + err.lines().collect(Collectors.joining("\n")));
                }
                catch (Throwable t) {
                    throw new IllegalArgumentException("authMinecraft response: " + conn.getResponseCode(), t);
                }
            }
            try (final BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                final JsonObject resp = (JsonObject)Constant.GSON.fromJson((String)in.lines().collect(Collectors.joining("\n")), (Class)JsonObject.class);
                return resp.get("access_token").getAsString();
            }
        }
    }

    public static Map.Entry<UUID, String> getProfile(final String accessToken) throws Exception {
        final HttpURLConnection conn = (HttpURLConnection)new URL("https://api.minecraftservices.com/minecraft/profile").openConnection();
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(15000);
        Label_0234: {
            if (conn.getResponseCode() >= 200) {
                if (conn.getResponseCode() <= 299) {
                    break Label_0234;
                }
            }
            try (final BufferedReader err = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                throw new IllegalArgumentException("getProfile response: " + conn.getResponseCode() + ", data: " + err.lines().collect(Collectors.joining("\n")));
            }
            catch (Throwable t) {
                throw new IllegalArgumentException("getProfile response: " + conn.getResponseCode(), t);
            }
        }
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            final JsonObject resp = (JsonObject)Constant.GSON.fromJson((String)in.lines().collect(Collectors.joining("\n")), (Class)JsonObject.class);
            return new AbstractMap.SimpleImmutableEntry<UUID, String>(UUID.fromString(resp.get("id").getAsString().replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5")), resp.get("name").getAsString());
        }
    }

    
    public static UUID resolveUUID(final String name) {
        try (final InputStreamReader in = new InputStreamReader(new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream(), StandardCharsets.UTF_8)) {
            final UUID uuid = UUID.fromString(((JsonObject)Constant.GSON.fromJson((Reader)in, (Class)JsonObject.class)).get("id").getAsString().replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
            return uuid;
        }
        catch (Throwable ignored) {
            final UUID uuid2 = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8));
            return uuid2;
        }
    }

    static {
        BLIND_SSL = Boolean.getBoolean("ias.blindSSL");
        NO_CUSTOM_SSL = Boolean.getBoolean("ias.noCustomSSL");
        SSLContext ctx = null;
        try {
            if (Auth.BLIND_SSL) {
                final TrustManager blindManager = new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                };
                ctx = SSLContext.getInstance("TLS");
                ctx.init(null, new TrustManager[] { blindManager }, new SecureRandom());
            }
            else if (!Auth.NO_CUSTOM_SSL) {
                final KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
                try (final InputStream in = Auth.class.getResourceAsStream("/iasjavafix.jks")) {
                    ks.load(in, "iasjavafix".toCharArray());
                }
                final TrustManagerFactory customTmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                customTmf.init(ks);
                final TrustManagerFactory defaultTmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                defaultTmf.init((KeyStore)null);
                final List<X509TrustManager> managers = new ArrayList<X509TrustManager>();
//                managers.addAll(Arrays.stream(customTmf.getTrustManagers()).filter(tm -> tm instanceof X509TrustManager).map(tm -> tm).collect((Collector<? super Object, ?, Collection<? extends X509TrustManager>>)Collectors.toList()));
//                managers.addAll(Arrays.stream(defaultTmf.getTrustManagers()).filter(tm -> tm instanceof X509TrustManager).map(tm -> tm).collect((Collector<? super Object, ?, Collection<? extends X509TrustManager>>)Collectors.toList()));

                managers.addAll((Collection)Arrays.stream(customTmf.getTrustManagers()).filter(tm -> tm instanceof X509TrustManager)
                        .map(tm -> (X509TrustManager)tm).collect(Collectors.toList()));
                managers.addAll((Collection)Arrays.stream(defaultTmf.getTrustManagers()).filter(tm -> tm instanceof X509TrustManager)
                        .map(tm -> (X509TrustManager)tm).collect(Collectors.toList()));

                final TrustManager multiManager = new X509TrustManager() {

                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        CertificateException wrapper = new CertificateException("Unable to validate via any trust manager.");
                        for (X509TrustManager manager : managers) {
                            try {
                                manager.checkClientTrusted(chain, authType);
                                return;
                            } catch (Throwable t) {
                                wrapper.addSuppressed(t);
                            }
                        }
                        throw wrapper;
                    }


                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        CertificateException wrapper = new CertificateException("Unable to validate via any trust manager.");
                        for (X509TrustManager manager : managers) {
                            try {
                                manager.checkServerTrusted(chain, authType);
                                return;
                            } catch (Throwable t) {
                                wrapper.addSuppressed(t);
                            }
                        }
                        throw wrapper;
                    }


                    public X509Certificate[] getAcceptedIssuers() {
                        List<X509Certificate> certificates = new ArrayList<X509Certificate>();
                        for (X509TrustManager manager : managers) {
                            certificates.addAll(Arrays.asList(manager.getAcceptedIssuers()));
                        }
                        return (X509Certificate[])certificates.toArray(new X509Certificate[0]);
                    }
                };
                ctx = SSLContext.getInstance("TLS");
                ctx.init(null, new TrustManager[] { multiManager }, new SecureRandom());
            }
            else {
            }
        }
        catch (Throwable t) {
        }
        FIXED_CONTEXT = ctx;
    }
}
