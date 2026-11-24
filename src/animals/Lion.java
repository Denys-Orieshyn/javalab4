package animals;

public class Lion extends Mammal {
    public Lion(String name) {
        super(name);
    }

    @Override
    public String getSpecies() {
        return "Лев";
    }
}
