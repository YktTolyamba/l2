package org.example.forms;

import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

import static org.example.constants.WebElementAttributes.*;
import static org.example.constants.UtilConstants.*;
import static org.example.constants.StringDelimiters.*;

public class MyProfilePage extends Form {
    private static final By pageUniqueLocator = By.xpath("//div[@class='ProfileWrapper']");
    private final By newPostAuthorLocator = By.xpath("//a[@class='author']");
    private final By showNextCommentButtonLocator = By.xpath("//span[@class='js-replies_next_label']");
    private final By likeNewPostButtonLocator = By.xpath("//div[contains( @class, 'PostButtonReactionsContainer' )]");
    private final By postTextLocator = By.xpath("//div[contains(@class, 'wall_post_text')]");
    private final By postPhotoLocator = By.xpath("//a[@aria-label = 'photo']");
    private final By commentLocator = By.xpath("//div[@data-answering-id]");
    private final String newPostIdXpathString = "//div[@id='%s']";
    private final String deletedPostIdXpathString = "//div[@id='%s' and contains( @class, 'unshown' )]";
    private final IButton editButton = getElementFactory().getButton(By.xpath("//div[@class='ProfileHeaderButton']"), "Edit button");
    private ILabel newPost;
    private IButton showNextCommentButton;

    public MyProfilePage() {
        super(pageUniqueLocator, "My profile page");
    }

    public void waitForPageToLoad() {
        editButton.state().waitForDisplayed();
    }

    public void findNewPostById(String newPostIdString) {
        By newPostLocator = By.xpath(String.format(newPostIdXpathString, newPostIdString));
        newPost = getElementFactory().getLabel(newPostLocator, "New post");
        newPost.state().waitForDisplayed();
    }

    public int getNewPostAuthorsId() {
        ILink latestPostAuthor = newPost.findChildElement(newPostAuthorLocator, "New post author link", ElementType.LINK);
        String href = latestPostAuthor.getAttribute(HREF);
        return Integer.parseInt(href.substring(href.lastIndexOf(ID_PREFIX) + ID_INDENT));
    }

    public String getTextFromNewPost() {
        ILabel postText = newPost.findChildElement(postTextLocator, "New post text", ElementType.LABEL);
        return postText.getText();
    }

    public int getPostPhotoId() {
        ILink postPhoto = newPost.findChildElement(postPhotoLocator, "Edited new post photo", ElementType.LINK);
        String href = postPhoto.getAttribute(HREF);
        return Integer.parseInt(href.substring(href.lastIndexOf(PHOTO_ID_SEPARATOR) + PHOTO_ID_INDENT));
    }

    public void scrollToShowNextCommentButton() {
        showNextCommentButton = newPost.findChildElement(showNextCommentButtonLocator, "Show next comment button", ElementType.BUTTON);
        showNextCommentButton.state().waitForDisplayed();
        showNextCommentButton.getJsActions().scrollToTheCenter();
    }

    public void clickShowNextCommentButton() {
        showNextCommentButton.state().waitForClickable();
        showNextCommentButton.click();
    }

    public int getCommentAuthorId() {
        ILabel comment = newPost.findChildElement(commentLocator, "Comment on new post", ElementType.LABEL);
        return Integer.parseInt(comment.getAttribute(DATA_ANSWERING_ID));
    }

    public void clickLikePostButton() {
        IButton likeNewPostButton = newPost.findChildElement(likeNewPostButtonLocator, "Like post button", ElementType.BUTTON);
        likeNewPostButton.state().waitForDisplayed();
        likeNewPostButton.click();
    }

    public boolean isNewPostDisplayed(String newPostIdString) {
        By deletedNewPostLocator = By.xpath(String.format(deletedPostIdXpathString, newPostIdString));
        ILabel deletedNewPost = getElementFactory().getLabel(deletedNewPostLocator, "Deleted new post");
        return deletedNewPost.state().isDisplayed();
    }
}
