package johnny.nguyen.nbaagent;

/**
 * Created by johnnywin24 on 9/14/17.
 */

public class Article {

    private String Author;
    private String Title;
    private String DateTime;


    public Article() {
    }

    public Article(String Author, String Title, String dateTime) {
        this.Author = Author;
        this.Title = Title;
        this.DateTime = dateTime;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

}
