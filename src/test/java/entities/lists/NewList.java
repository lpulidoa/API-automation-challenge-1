package entities.lists;

public class NewList {
    private String name;
    private String description;
    private String language;

    public NewList(){
    }

    public NewList(String name, String description, String language) {
        this.name = name;
        this.description = description;
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
