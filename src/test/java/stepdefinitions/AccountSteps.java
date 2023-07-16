package stepdefinitions;

import com.google.inject.Inject;
import enums.keysforcontext.ContextLibrariesKeys;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import screens.AddLibraryScreen;
import screens.CatalogScreen;
import screens.LibrariesScreen;
import screens.SettingsScreen;
import screens.menubar.MenuBar;
import screens.menubar.MenuBarScreen;

import java.util.ArrayList;
import java.util.List;

public class AccountSteps {

    private final AddLibraryScreen addLibraryScreen;
    private final MenuBarScreen menuBarScreen;
    private final SettingsScreen settingsScreen;
    private final LibrariesScreen librariesScreen;
    private final CatalogScreen catalogScreen;
    private final ScenarioContext context;

    @Inject
    public AccountSteps(ScenarioContext context) {
        this.context = context;
        addLibraryScreen = new AddLibraryScreen();
        menuBarScreen = new MenuBarScreen();
        settingsScreen = new SettingsScreen();
        librariesScreen = new LibrariesScreen();
        catalogScreen = new CatalogScreen();
    }

    @Then("Add library screen is opened")
    public void isAddLibraryScreenIsOpened() {
        Assert.assertTrue("Add Library screen is not opened!", addLibraryScreen.isAddLibraryScreenOpened());
    }

    @When("Add library {string} on Add library screen")
    public void addLibrary(String libraryName) {
        addLibraryScreen.addLibraryViaSearch(libraryName);
    }

    @Then("Library {string} is opened on Libraries screen")
    public void isLibraryPresent(String libraryName) {
        openAccounts();
        Assert.assertTrue(libraryName + " is not present on Libraries screen", librariesScreen.isLibraryPresent(libraryName));
    }

    @When("Type {string} library and save name as {string} on Add library screen")
    public void typeLibraryName(String libraryName, String libraryNameKey) {
        context.add(libraryNameKey, libraryName);
        addLibraryScreen.typeLibraryName(libraryName);
    }

    @Then("Library {string} is displayed on Add library screen")
    public void isLibraryDisplayed(String libraryNameKey) {
        String libraryName = context.get(libraryNameKey);
        Assert.assertTrue(libraryName + " not found", addLibraryScreen.isLibraryDisplayed(libraryName));
    }

    @When("Clear search field on Add library screen")
    public void clickDeleteButton(){
        addLibraryScreen.clearSearchField();
    }

    @Then("Search field is empty on Add library screen")
    public void isSearchFieldEmpty() {
        Assert.assertTrue("Search field is not empty!", addLibraryScreen.isSearchFieldEmpty());
    }

    @When("Type word {} and save as {string} on Add library screen")
    public void typeWord(String word, String wordKey){
        context.add(wordKey, word);
        addLibraryScreen.typeLibraryName(word);
    }

    @Then("Libraries contain word {string} on Add library screen")
    public void isLibraryContainsWord(String wordKey) {
        String word = context.get(wordKey);
        Assert.assertTrue("Search result does not contain libraries with " + word, addLibraryScreen.isLibraryContainsWord(word));
    }

    @Then("Search result is empty on Add library screen")
    public void isSearchResultEmpty() {
        Assert.assertTrue("Search result contains data!", addLibraryScreen.isSearchResultEmpty());
    }

    @When("Add {string} library in Libraries screen")
    public void addLibraryInLibScreen(String libraryName){
        openAccounts();
        librariesScreen.addLibrary();
        addLibraryScreen.addLibraryViaSearch(libraryName);

        if(libraryName.equalsIgnoreCase("LYRASIS Reads")){
            saveLibraryInContext(ContextLibrariesKeys.LOG_OUT.getKey(), libraryName);
        }
    }

    private void saveLibraryInContext(String key, String libraryName) {
        List<String> listOfLibraries = context.containsKey(key)
                ? context.get(key)
                : new ArrayList<>();

        listOfLibraries.add(libraryName);
        context.add(key, listOfLibraries);
    }

    private void openAccounts() {
        menuBarScreen.openBottomMenuTab(MenuBar.SETTINGS);
        menuBarScreen.openBottomMenuTab(MenuBar.SETTINGS);
        settingsScreen.openLibraries();
    }
}
