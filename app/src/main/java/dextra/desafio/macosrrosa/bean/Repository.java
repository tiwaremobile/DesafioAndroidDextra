package dextra.desafio.macosrrosa.bean;

/**
 * Created by marcos.fael on 12/01/2016.
 */
public class Repository {
    private String name;
    private String fullName;
    private String urlPage;

    public Repository(String name, String fullName, String urlPage) {
        this.name = name;
        this.fullName = fullName;
        this.urlPage = urlPage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUrlPage() {
        return urlPage;
    }

    public void setUrlPage(String urlPage) {
        this.urlPage = urlPage;
    }
}
