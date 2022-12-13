package top.whitecola.promodule.services.login.microsoft;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public interface Account {
    UUID uuid();

    String name();

    CompletableFuture<AuthData> login(BiConsumer<String, Object[]> paramBiConsumer);

    public static class AuthData
    {
        public static final String MSA = "msa";
        public static final String MOJANG = "mojang";
        public static final String LEGACY = "legacy";
        private final String name;
        private final UUID uuid;
        private final String accessToken;
        private final String userType;

        public AuthData(String name, UUID uuid, String accessToken, String userType) {
            this.name = name;
            this.uuid = uuid;
            this.accessToken = accessToken;
            this.userType = userType;
        }

        public String name() { return this.name; }

        public UUID uuid() { return this.uuid; }

        public String accessToken() { return this.accessToken; }

        public String userType() { return this.userType; }
    }
}
