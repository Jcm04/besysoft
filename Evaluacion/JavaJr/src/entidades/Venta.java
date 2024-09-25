package entidades;

public class Venta {
    private final Vendedor vendedor;
    private final Producto producto;

    public Venta(Vendedor vendedor, Producto producto) {
        this.vendedor = vendedor;
        this.producto = producto;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public Producto getProducto() {
        return producto;
    }
}
