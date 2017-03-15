package bl.entities;

/**
 * Created by Daniel_m on 14/03/2017.
 */

public class Amendment {
    private AmendmentType type;
    private String description;

    public Amendment(){
        this.type = null;
        this.description = null;
    }

    public Amendment(AmendmentType type, String description) {
        this.type = type;
        this.description = description;
    }

    public AmendmentType getType() {
        return type;
    }

    public void setType(AmendmentType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
