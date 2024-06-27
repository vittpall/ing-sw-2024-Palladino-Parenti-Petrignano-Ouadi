package it.polimi.ingsw.model.enumeration;

/**
 * This enums contains all possible Resources available on the cards
 */
public enum Resource {
    PLANT_KINGDOM("Plant Kingdom"), ANIMAL_KINGDOM("Animal Kingdom"), FUNGI_KINGDOM("Fungi Kingdom"), INSECT_KINGDOM("Insect Kingdom");

    private final String text;

    /**
     * Constructor
     *
     * @param text is the representation the Resource
     */

    Resource(String text) {
        this.text = text;
    }

    /**
     * @return the value of the string representing the Resource
     */

    public String get() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }


}
