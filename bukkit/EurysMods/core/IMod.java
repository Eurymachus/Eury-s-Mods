package EurysMods.core;

public interface IMod
{
    void initialize();

    void load();

    void addItems();

    void addNames();

    void addRecipes();

    int configurationProperties();
}
