import aquality.selenium.browser.AqualityServices;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.forms.IdVkPage;
import org.example.forms.LoginPage;
import org.example.forms.MyProfilePage;
import org.example.forms.Sidebar;
import org.example.utils.FilePathHelper;
import org.example.utils.IdToStringHelper;
import org.example.utils.JsonHelper;
import org.example.utils.VkApi;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static org.example.constants.JsonFiles.TESTDATA;
import static org.example.constants.JsonKeys.*;
import static org.example.constants.JsonResponseKeys.POST_ID_KEY;
import static org.example.constants.JsonResponseKeys.UPLOAD_URL_KEY;

public class TestCases extends BaseTest {

    @Test
    public void vkApiTest() {
        LoginPage loginPage = new LoginPage();
        loginPage.enterLoginInfo(TESTDATA.getValue(LOGIN_KEY).toString());
        loginPage.clickSignInButton();
        AqualityServices.getBrowser().waitForPageToLoad();

        IdVkPage idVkPage = new IdVkPage();
        idVkPage.enterPasswordInfo(TESTDATA.getValue(PASSWORD_KEY).toString());
        idVkPage.clickContinueButton();
        AqualityServices.getBrowser().waitForPageToLoad();

        Sidebar sidebar = new Sidebar();
        sidebar.clickMyProfileButton();

        MyProfilePage myProfilePage = new MyProfilePage();
        myProfilePage.waitForPageToLoad();
        int ownerId = Integer.parseInt(TESTDATA.getValue(OWNER_ID_KEY).toString());
        String messageToCreatePost = RandomStringUtils.randomAlphanumeric(Integer.parseInt(TESTDATA.getValue(TEXT_LENGTH_KEY).toString()));
        int createdPostIdInt = (int) JsonHelper.getValueFromResponse(VkApi.createPost(messageToCreatePost), POST_ID_KEY);
        String createdPostIdString = IdToStringHelper.getPostId(ownerId, createdPostIdInt);
        myProfilePage.findNewPostById(createdPostIdString);
        String textFromCreatedPost = myProfilePage.getTextFromNewPost();
        int latestPostAuthorsId = myProfilePage.getNewPostAuthorsId();
        Assert.assertEquals(ownerId, latestPostAuthorsId, "New created post author is not owner of the page.");
        Assert.assertEquals(textFromCreatedPost, messageToCreatePost, "There is no post with given text.");

        String uploadServer = (String) JsonHelper.getValueFromResponse(VkApi.getPhotoUploadServer(), UPLOAD_URL_KEY);
        String photoName = TESTDATA.getValue(PHOTO_NAME_KEY).toString();
        File photo = new File(FilePathHelper.getStringPath(photoName));
        HttpResponse<JsonNode> photoUploadData = VkApi.uploadPhoto(uploadServer, photo);
        HttpResponse<JsonNode> photoSaveData = VkApi.saveUploadedPhoto(photoUploadData);
        String messageToEditPost = RandomStringUtils.randomAlphanumeric(Integer.parseInt(TESTDATA.getValue(TEXT_LENGTH_KEY).toString()));
        int photoId = JsonHelper.getSavedPhotoIdFromResponse(photoSaveData);
        String photoIdString = IdToStringHelper.getPhotoId(ownerId, photoId);
        VkApi.editPost(createdPostIdInt, photoIdString, messageToEditPost);

        Assert.assertEquals(myProfilePage.getTextFromNewPost(), messageToEditPost, "Post's text has not been edited.");
        Assert.assertEquals(myProfilePage.getPostPhotoId(), photoId, "Uploaded photo and edited post's photo are equal.");
        String messageToCreateComment = RandomStringUtils.randomAlphanumeric(Integer.parseInt(TESTDATA.getValue(TEXT_LENGTH_KEY).toString()));
        VkApi.commentPost(createdPostIdInt, messageToCreateComment);
        myProfilePage.scrollToShowNextCommentButton();
        myProfilePage.clickShowNextCommentButton();
        Assert.assertEquals(myProfilePage.getCommentAuthorId(), ownerId, "Author of the comment's and post owner are different users.");

        myProfilePage.clickLikePostButton();
        HttpResponse<JsonNode> postLikes = VkApi.getPostLikes(createdPostIdInt);
        Assert.assertEquals(JsonHelper.getLikedUserIdFromResponse(postLikes), ownerId, "User who liked and post owner are different users.");

        VkApi.deletePost(createdPostIdInt);
        Assert.assertFalse(myProfilePage.isNewPostDisplayed(createdPostIdString), "Created post has not been deleted.");
    }

}
