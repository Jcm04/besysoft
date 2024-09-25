package entidades;

public class Vendedor {
    private final int codigo;
    private final String nombre;
    private final float sueldo;

    public Vendedor(int codigo, String nombre, float sueldo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.sueldo = sueldo;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public float getSueldo() {
        return sueldo;
    }
}
