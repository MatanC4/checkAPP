package bl.entities;

/**
 * Created by Daniel_m on 11/03/2017.
 */

public class Category implements Comparable<Category> {

    private CategoryName name;
    private String apiKey;

    public Category(){

    }

    public Category(CategoryName name, String apiKey) {
        this.name = name;
        this.apiKey = apiKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Category category = (Category) o;
        return name == category.name;
    }

    @Override
    public int compareTo(Category category) {
        if(this.equals(category))
            return 0;
        return this.name.toString().compareTo(category.name.toString());
    }

    public CategoryName getName() {
        return name;
    }

    public void setName(CategoryName name) {
        this.name = name;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

}
