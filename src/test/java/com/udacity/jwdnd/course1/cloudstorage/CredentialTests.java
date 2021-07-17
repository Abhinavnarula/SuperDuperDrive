package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for Credential Creation, Viewing, Editing, and Deletion.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CredentialTests extends CloudStorageApplicationTests {

    public static final String GMAIL_URL = "https://www.gmail.com/";
    public static final String GMAIL_USERNAME = "abhinavnarula98";
    public static final String GMAIL_PASSWORD = "abhinav1998";
    public static final String GMAIL2_URL = "http://www.gmail.com/";
    public static final String GMAIL2_USERNAME = "anilpranav";
    public static final String GMAIL2_PASSWORD = "abhiprabhi";

    /**
     * Test that creates a set of credentials, verifies that they are displayed, and
     * verifies that the displayed password is encrypted.
     */
    @Test
    public void testCredentialCreation() {
        HomePage homePage = signUpAndLogin();
        createAndVerifyCredential(GMAIL_URL, GMAIL_USERNAME, GMAIL_PASSWORD, homePage);
        homePage.deleteCredential();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.logout();
    }

    private void createAndVerifyCredential(String url, String username, String password, HomePage homePage) {
        createCredential(url, username, password, homePage);
        homePage.navToCredentialsTab();
        Credential credential = homePage.getFirstCredential();
        Assertions.assertEquals(url, credential.getUrl());
        Assertions.assertEquals(username, credential.getUserName());
        Assertions.assertNotEquals(password, credential.getPassword());
    }

    private void createCredential(String url, String username, String password, HomePage homePage) {
        homePage.navToCredentialsTab();
        homePage.addNewCredential();
        setCredentialFields(url, username, password, homePage);
        homePage.saveCredentialChanges();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.navToCredentialsTab();
    }

    private void setCredentialFields(String url, String username, String password, HomePage homePage) {
        homePage.setCredentialUrl(url);
        homePage.setCredentialUsername(username);
        homePage.setCredentialPassword(password);
    }

    /**
     * Test that views an existing set of credentials, verifies that the viewable
     * password is unencrypted, edits the credentials, and verifies that the changes
     * are displayed.
     */
    @Test
    public void testCredentialModification() {
        HomePage homePage = signUpAndLogin();
        createAndVerifyCredential(GMAIL_URL, GMAIL_USERNAME, GMAIL_PASSWORD, homePage);
        Credential originalCredential = homePage.getFirstCredential();
        String firstEncryptedPassword = originalCredential.getPassword();
        homePage.editCredential();
        String newUrl = GMAIL2_URL;
        String newCredentialUsername = GMAIL2_USERNAME;
        String newPassword = GMAIL2_PASSWORD;
        setCredentialFields(newUrl, newCredentialUsername, newPassword, homePage);
        homePage.saveCredentialChanges();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.navToCredentialsTab();
        Credential modifiedCredential = homePage.getFirstCredential();
        Assertions.assertEquals(newUrl, modifiedCredential.getUrl());
        Assertions.assertEquals(newCredentialUsername, modifiedCredential.getUserName());
        String modifiedCredentialPassword = modifiedCredential.getPassword();
        Assertions.assertNotEquals(newPassword, modifiedCredentialPassword);
        Assertions.assertNotEquals(firstEncryptedPassword, modifiedCredentialPassword);
        homePage.deleteCredential();
        resultPage.clickOk();
        homePage.logout();
    }

    /**
     * Test that deletes an existing set of credentials and verifies that the
     * credentials are no longer displayed.
     */
    @Test
    public void testDeletion() {
        HomePage homePage = signUpAndLogin();
        createCredential(GMAIL_URL, GMAIL_USERNAME, GMAIL_PASSWORD, homePage);
        createCredential(GMAIL2_URL, GMAIL2_USERNAME, GMAIL2_PASSWORD, homePage);
        createCredential("http://www.facebook.com/", "abhinav", "narula", homePage);
        Assertions.assertFalse(homePage.noCredentials(driver));
        homePage.deleteCredential();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.navToCredentialsTab();
        homePage.deleteCredential();
        resultPage.clickOk();
        homePage.navToCredentialsTab();
        homePage.deleteCredential();
        resultPage.clickOk();
        homePage.navToCredentialsTab();
        Assertions.assertTrue(homePage.noCredentials(driver));
        homePage.logout();
    }
}