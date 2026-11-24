package animals;

public class Eagle extends Bird {
    public Eagle(String name) {
        super(name);
    }

    @Override
    public String getSpecies() {
        return "Орел";
    }
}
