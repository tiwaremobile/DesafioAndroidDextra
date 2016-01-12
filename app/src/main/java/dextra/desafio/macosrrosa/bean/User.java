package dextra.desafio.macosrrosa.bean;

/**
 * Created by marcos.fael@gmail.com on 10/01/2016.
 */
public class User {
    private String login;
    private String urlAvatar;
    private String urlPage;

    public User(String login, String urlAvatar, String urlPage) {
        this.login = login;
        this.urlAvatar = urlAvatar;
        this.urlPage = urlPage;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    public String getUrlPage() {
        return urlPage;
    }

    public void setUrlPage(String urlPage) {
        this.urlPage = urlPage;
    }
}
