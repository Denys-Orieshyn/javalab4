package animals;

// Копитні — спільний предок зебр і жирафів.
// Саме цей клас використовується як обмеження для вольєра копитних.
public abstract class Ungulate extends Mammal {
    protected Ungulate(String name) {
        super(name);
    }
}
