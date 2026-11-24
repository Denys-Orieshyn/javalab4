package cages;

import animals.Ungulate;

// Вольєр для копитних: приймає Ungulate, тобто і зебр, і жирафів (обидва — нащадки Ungulate).
public class UngulateCage extends MammalCage<Ungulate> {
    public UngulateCage(int capacity) {
        super(capacity);
    }

    @Override
    public String getTitle() {
        return "Вольєр для копитних";
    }
}
