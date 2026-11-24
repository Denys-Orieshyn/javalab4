package animals;

public class Zebra extends Ungulate {
    public Zebra(String name) {
        super(name);
    }

    @Override
    public String getSpecies() {
        return "Зебра";
    }
}
