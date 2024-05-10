package api.orders;

public enum ScooterColor {
    BLACK("BLACK"),
    GREY("GREY");
    private final String color;

    ScooterColor(String color) {this.color = color;}

    public String getMode() { return color;}
}
