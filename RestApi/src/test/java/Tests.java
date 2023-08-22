import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.apache.http.HttpStatus;
import org.example.utils.Check;
import org.example.utils.JsonUtils;
import org.example.utils.RandomUtils;
import org.example.utils.Api;
import org.example.dataclasses.PostData;
import org.example.dataclasses.UserData;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.testng.Assert.*;
import static org.example.utils.MyConstants.*;

public class Tests {
    private final ISettingsFile config = new JsonSettingsFile("config.json");
    private final ISettingsFile urlAppenders = new JsonSettingsFile("urlAppenders.json");
    private final ISettingsFile testData = new JsonSettingsFile("testData.json");

    @Test
    public void getAllPosts() {
        String url = config.getValue(URL_KEY).toString() + urlAppenders.getValue(POSTS_URL_APPENDER_KEY).toString();

        assertTrue( JsonUtils.validateJsonFileWithSchema( url, "schemaForPosts.json"), "File in response is not JSON.");
        HttpResponse<JsonNode> response = Api.sendGetRequest( url);
        PostData[] postDataList = JsonUtils.getJavaObjectFromJsonResponse(response, PostData[].class );

        assertEquals( response.getStatus(), HttpStatus.SC_OK, "Status code is not as expected.");
        assertTrue( Check.areIdsAscending( postDataList ), "Posts are not sorted ascending by id.");
    }

    @Test
    public void getPostWithId() {
        String url = config.getValue(URL_KEY).toString()
                + urlAppenders.getValue(POSTS_URL_APPENDER_KEY).toString()
                + urlAppenders.getValue(APPENDER_FOR_GET_POST_WITH_ID_TEST_KEY).toString();

        HttpResponse<JsonNode> response = Api.sendGetRequest( url );
        PostData postData = JsonUtils.getJavaObjectFromJsonResponse(response, PostData.class );

        ISettingsFile expRes = new JsonSettingsFile("expectedResultsForSecondTest.json");

        PostData expectedResult = new PostData(
                Integer.parseInt( expRes.getValue(USER_ID_FOR_GET_POST_WITH_ID_TEST_KEY).toString()),
                Integer.parseInt( expRes.getValue(POST_ID_FOR_GET_POST_WITH_ID_TEST_KEY).toString()),
                        expRes.getValue(POST_TITLE_FOR_GET_POST_WITH_ID_TEST_KEY).toString(),
                        expRes.getValue(POST_BODY_FOR_GET_POST_WITH_ID_TEST_KEY).toString());

        assertEquals( response.getStatus(), HttpStatus.SC_OK, "Status code is not as expected.");
        assertEquals( expectedResult, postData, "Post information is not correct.");
    }

    @Test
    public void get404Post() {
        String url = config.getValue(URL_KEY).toString()
                + urlAppenders.getValue(POSTS_URL_APPENDER_KEY).toString()
                + urlAppenders.getValue(APPENDER_FOR_GET_404_POST_TEST_KEY).toString();

        HttpResponse<JsonNode> response = Api.sendGetRequest( url );

        assertEquals( response.getStatus(), HttpStatus.SC_NOT_FOUND, "Status code is not as expected.");
    }

    @Test
    public void sendPostRequest() {
        String url = config.getValue(URL_KEY).toString() + urlAppenders.getValue(POSTS_URL_APPENDER_KEY).toString();

        ISettingsFile testdata = new JsonSettingsFile("testData.json");

        int userId = Integer.parseInt( testdata.getValue(USER_ID_FOR_SEND_POST_REQUEST_TEST_KEY).toString());
        String randomTitle = RandomUtils.getRandomString();
        String randomBody = RandomUtils.getRandomString();

        PostData postDataToSend = new PostData(userId, randomTitle, randomBody);

        JSONObject jsonObject = JsonUtils.getJsonObjectFromJavaObject(postDataToSend);
        HttpResponse<JsonNode> response = Api.sendPostRequest( jsonObject, url);
        PostData postDataReceived = JsonUtils.getJavaObjectFromJsonResponse(response, PostData.class);

        assertEquals( postDataToSend, postDataReceived, "Post information is not correct.");

        assertEquals( response.getStatus(), HttpStatus.SC_CREATED, "Status code is not as expected." );
    }

    @Test
    public void getAllUsers() {
        String url = config.getValue(URL_KEY).toString() + urlAppenders.getValue(USERS_URL_APPENDER_KEY);
        int userId = Integer.parseInt( testData.getValue(USER_ID_FOR_GET_ALL_USERS_TEST_KEY).toString());
        UserData expRes = JsonUtils.loadJsonFromFile("expectedResultsForFifthTest.json", UserData.class);

        assertTrue( JsonUtils.validateJsonFileWithSchema(url, "schemaForUsers.json"), "File in response is not JSON.");


        HttpResponse<JsonNode> response = Api.sendGetRequest( url );
        UserData[] userDataArray = JsonUtils.getJavaObjectFromJsonResponse(response, UserData[].class );

        assertEquals( response.getStatus(), HttpStatus.SC_OK, "Status code is not as expected." );

        UserData userToTest = Arrays.stream(userDataArray).filter(s -> {return s.getId() == 5;}).findAny().orElse(null);
        assertEquals(userToTest, expRes, "User (id=5) data isn't equal to expected result.");
    }

    @Test
    public void getUserWithId() {
        String url = config.getValue(URL_KEY).toString()
                + urlAppenders.getValue(USERS_URL_APPENDER_KEY).toString()
                + urlAppenders.getValue(APPENDER_FOR_GET_USER_WITH_ID_TEST_KEY).toString();

        UserData expRes = JsonUtils.loadJsonFromFile("expectedResultsForFifthTest.json", UserData.class);

        HttpResponse<JsonNode> response = Api.sendGetRequest( url );
        UserData userData = JsonUtils.getJavaObjectFromJsonResponse(response, UserData.class );

        assertEquals( response.getStatus(), HttpStatus.SC_OK, "Status code is not as expected." );

        assertEquals( expRes, userData, "User data isn't equal to expected result from previous step.");
    }
}
