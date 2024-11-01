package config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@org.aeonbits.owner.Config.Sources({
        "classpath:base.properties",
        "classpath:auth.properties",
        "classpath:remote.properties"
})
public interface Config extends org.aeonbits.owner.Config {

    @Key("baseUrl")
    @DefaultValue("https://demoqa.com")
    String getBaseUrl();

    @Key("login")
    String login();

    @Key("password")
    String password();

    @Key("browser")
    @DefaultValue("chrome")
    String browserName();

    @Key("browserVersion")
    @DefaultValue("latest")
    String browserVersion();

    @Key("browserSize")
    @DefaultValue("1280x1024")
    String browserSize();

    @Key("isRemote")
    @DefaultValue("false")
    Boolean isRemote();

    @Key("selenoidUser")
    String selenoidUser();

    @Key("selenoidPassword")
    String selenoidPassword();

    @Key("remoteUrl")
    String getRemoteUrl();
}