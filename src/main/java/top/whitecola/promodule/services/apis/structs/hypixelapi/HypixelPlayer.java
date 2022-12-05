package top.whitecola.promodule.services.apis.structs.hypixelapi;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class HypixelPlayer {

    private Boolean success;
    private PlayerBean player;



    public static class PlayerBean {
        private String _id;
        private String uuid;
        private Long firstLogin;
        private String playername;
        private Long lastLogin;
        private String displayname;
        private List<String> knownAliases;
        private List<String> knownAliasesLower;
        private List<String> achievementsOneTime;
        private Double networkExp;
        private EugeneBean eugene;
        private Integer karma;
        private Boolean rewardConsumed;
        private String mcVersionRp;
        private List<?> friendRequestsUuid;
        private Boolean levelingReward_0;
        private Boolean levelingReward_9;
        private String language;
        private Long lastAdsenseGenerateTime;
        private Boolean levelingReward_1;
        private Boolean levelingReward_2;
        private Boolean levelingReward_3;
        private Boolean levelingReward_4;
        private Boolean levelingReward_5;
        private Boolean levelingReward_6;
        private Boolean levelingReward_7;
        private Boolean levelingReward_8;
        private Boolean levelingReward_10;
        private Boolean levelingReward_11;
        private Boolean levelingReward_12;
        private Boolean levelingReward_13;
        private Boolean levelingReward_14;
        private Boolean levelingReward_15;
        private Boolean levelingReward_16;
        private Boolean levelingReward_17;
        private Boolean levelingReward_18;
        private Boolean levelingReward_19;
        private Boolean levelingReward_20;
        private Boolean levelingReward_21;
        private Boolean levelingReward_22;
        private Boolean levelingReward_23;
        private Boolean levelingReward_24;
        private Boolean levelingReward_25;
        private Boolean levelingReward_26;
        private Boolean levelingReward_27;
        private Long flashingSalePopup;
        private Integer flashingSaleOpens;
        private Integer flashingSalePoppedUp;
        private Integer flashingSaleClicks;
        private Boolean levelingReward_28;
        private Boolean levelingReward_29;
        private Boolean levelingReward_30;
        private Boolean levelingReward_31;
        private Boolean levelingReward_32;
        private Boolean levelingReward_33;
        private Boolean levelingReward_34;
        private Boolean levelingReward_35;
        private Boolean levelingReward_36;
        private Boolean levelingReward_37;
        private Boolean levelingReward_38;
        private String userLanguage;
        private Boolean levelingReward_39;
        private Boolean levelingReward_40;
        private Boolean levelingReward_41;
        private Boolean levelingReward_42;
        private Boolean levelingReward_43;
        private Boolean levelingReward_44;
        private Boolean levelingReward_45;
        private Boolean levelingReward_46;
        private Boolean levelingReward_47;
        private Boolean levelingReward_48;
        private String vanityFavorites;
        private String channel;
        private Boolean levelingReward_49;
        private Long lastClaimedReward;
        private Integer totalRewards;
        private Integer totalDailyRewards;
        private Integer rewardStreak;
        private Integer rewardScore;
        private Integer rewardHighScore;
        private Boolean levelingReward_50;
        private Boolean levelingReward_52;
        private String newPackageRank;
        private Long levelUp_MVP;
        private Boolean levelingReward_51;
        private Boolean levelingReward_53;
        private Boolean levelingReward_54;
        private Boolean levelingReward_55;
        private Boolean levelingReward_56;
        private Boolean levelingReward_57;
        private Boolean levelingReward_58;
        private Boolean levelingReward_59;
        private Boolean levelingReward_60;
        private Boolean levelingReward_61;
        private Integer adsense_tokens;
        private Boolean levelingReward_62;
        private Boolean levelingReward_63;
        private Boolean levelingReward_64;
        private Boolean levelingReward_65;
        private Long lastLogout;
        private Boolean levelingReward_66;
        private String currentPet;
        private Integer fortuneBuff;
        private Boolean levelingReward_67;
        private String network_update_book;
        private List<?> achievementTracking;
        private Boolean levelingReward_68;
        private Integer achievementPoints;
        private Boolean levelingReward_69;
        private Boolean levelingReward_70;
        private Boolean levelingReward_71;
        private Boolean levelingReward_72;
        private String currentCloak;
        private Boolean levelingReward_73;
        private Boolean levelingReward_74;
        private Boolean levelingReward_75;
        private Boolean levelingReward_76;
        private Boolean levelingReward_77;
        private String particlePack;
        private Boolean levelingReward_78;
        private Long levelUp_MVP_PLUS;
        private Boolean levelingReward_79;
        private String rankPlusColor;
        private Boolean levelingReward_80;
        private Integer completed_christmas_quests_2021;
        private String currentClickEffect;
        private Boolean levelingReward_81;
        private Boolean levelingReward_82;
        private String currentGadget;
        private Integer completed_christmas_quests_2022;
        private Boolean levelingReward_83;
        private Boolean levelingReward_84;
        private Boolean levelingReward_85;
        private Boolean levelingReward_86;
        private Boolean main2017Tutorial;
        private Boolean levelingReward_87;
        private String mostRecentGameType;

        public Double getNetworkExp() {
            return networkExp;
        }










        public static class EugeneBean {
            private Long dailyTwoKExp;
        }



























    }

    public PlayerBean getPlayer() {
        return player;
    }
}
