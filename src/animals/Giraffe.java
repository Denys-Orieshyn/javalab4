package animals;

public class Giraffe extends Ungulate {
    public Giraffe(String name) {
        super(name);
    }

    @Override
    public String getSpecies() {
        return "Жираф";
    }
}
