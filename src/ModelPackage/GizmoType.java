package ModelPackage;

public enum GizmoType {
    SQUARE("Square"),
    TRIANGLE("Triangle"),
    CIRCLE("Circle"),
    LEFTFLIPPER("Left Flipper"),
    RIGHTFLIPPER("Right Flipper"),
    ABSORBER("Absorber")
    ;

    private final String text;

    /**
     * @param text
     */
    GizmoType(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }

    public static GizmoType fromString(String type){
        switch (type){
            case "Circle":
                return CIRCLE;
            case "Triangle":
                return TRIANGLE;
            case "Square":
                return SQUARE;
            case "Left Flipper":
                return LEFTFLIPPER;
            case "Right Flipper":
                return RIGHTFLIPPER;
            case "Absorber":
                return ABSORBER;
            default:
                return null;
        }
    }
}
