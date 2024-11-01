package config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@org.aeonbits.owner.Config.Sources({
        "system:properties",
        "classpath:api-config.properties"
})
public interface Config extends org.aeonbits.owner.Config {

    @Key("baseURI")
    String baseURI();

    @Key("basePath")
    String basePath();
}