package entidades;

public class Producto {
    private final int codigo;
    private final String nombre;
    private final float precio;
    private final Categoria categoria;

    public Producto(int codigo, String nombre, float precio, Categoria categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }
}
