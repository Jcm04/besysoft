import entidades.Categoria;
import entidades.Producto;
import entidades.Vendedor;
import entidades.Venta;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final List<Producto> productos = new ArrayList<>();
    private static final List<Categoria> categorias = new ArrayList<>();
    private static final List<Vendedor> vendedores = new ArrayList<>();


    private static final List<Venta> ventas = new ArrayList<>();

    public static void main(String[] args) {

        cargarInformacionBase();

        System.out.println(" -- Precarga del programa -- ");
        System.out.println(productos.size()+" productos cargados");
        System.out.println(categorias.size()+" categorias cargadas");
        System.out.println(vendedores.size()+" vendedores cargados");
        System.out.println(ventas.size()+" ventas cargadas");
        System.out.println(" --------- ");

        boolean salir;
        do {
            salir = mostrarMenu();
            System.out.println();
        } while (!salir);
        System.out.println("-- Saliendo del programa --");
        Utiles.s.close();
    }

    private static void cargarInformacionBase() {
        // Crear categorias base
        Categoria categoriaPan = new Categoria("Pan");
        Categoria categoriaBebidas = new Categoria("Bebidas");
        Categoria categoriaLacteos = new Categoria("Lacteos");
        categorias.add(categoriaPan);
        categorias.add(categoriaBebidas);
        categorias.add(categoriaLacteos);

        // Crear productos base
        Producto prod1 = new Producto(1, "Pan Frances", 25f, categoriaPan);
        Producto prod2 = new Producto(2, "Cerveza", 50f, categoriaBebidas);
        Producto prod3 = new Producto(3, "Leche Entera", 30f, categoriaLacteos);
        Producto prod4 = new Producto(4, "Pan Integral", 30f, categoriaPan);
        Producto prod5 = new Producto(5, "Jugo de Naranja", 35f, categoriaBebidas);

        // Agregar productos a la lista
        productos.add(prod1);
        productos.add(prod2);
        productos.add(prod3);
        productos.add(prod4);
        productos.add(prod5);


        // Crear vendedor

        Vendedor vendedor = new Vendedor(0, "Juan", 1000);
        vendedores.add(vendedor);
    }

    public static boolean mostrarMenu() {
        boolean salir = false;
        System.out.println("--- Tienda ---");
        System.out.println("1- Registrar Vendedor");
        System.out.println("2- Registrar Producto");
        System.out.println("3- Registrar venta");
        System.out.println("4- Mostrar vendedores");
        System.out.println("0- Salir");
        System.out.print("Elegir una opcion: ");
        int opcion = Utiles.ingresarEntero(0, 4);
        switch (opcion) {
            case 1 -> mostrarCrearVendedor();
            case 2 -> {
                if (categorias.isEmpty()) {
                    System.out.println("Primero debes crear minimo una categoria");
                    mostrarCrearCategoria();
                    mostrarCrearProducto();
                    break;
                }
                mostrarCrearProducto();
            }
            case 3 -> {
                if (vendedores.isEmpty()) {
                    System.out.println("No hay vendedores registrados !");
                    break;
                } else if (productos.isEmpty()) {
                    System.out.println("No hay productos registrados !");
                    break;
                }
                mostrarRegistrarVenta();
            }
            case 4 -> mostrarCalcularComision();
            case 0 -> salir = true;
        }
        return salir;
    }

    public static List<Producto> filtrarProductos(Categoria categoriaFiltro, Float precioMinimo, Float precioMaximo) {
        List<Producto> productosFiltrados = new ArrayList<>();

        for (Producto producto : productos) {
            boolean coincidenFiltros = true;

            if (categoriaFiltro != null && !producto.getCategoria().equals(categoriaFiltro)) {
                coincidenFiltros = false;
            }

            if (precioMinimo != null && producto.getPrecio() < precioMinimo) {
                coincidenFiltros = false;
            }

            if (precioMaximo != null && producto.getPrecio() > precioMaximo) {
                coincidenFiltros = false;
            }

            if (coincidenFiltros) {
                productosFiltrados.add(producto);
            }
        }
        if (productosFiltrados.isEmpty()) {
            System.out.println("No se encontraron productos que coincidan con los filtros.");
        }
        return productosFiltrados;
    }

    public static Producto mostrarProductosFiltrados(Categoria categoriaFiltro, Float precioMinimo, Float precioMaximo) {
        List<Producto> productosFiltrados = filtrarProductos(categoriaFiltro, precioMinimo, precioMaximo);
        if(productosFiltrados.isEmpty()) return null;
        Producto productoSeleccionado;
        System.out.println("Productos filtrados:");
        System.out.println("-1 Salir");
        System.out.println("0 Crear producto \n");
        for (int i = 0; i < productosFiltrados.size(); i++) {
            System.out.println((i+1) +" - " + productosFiltrados.get(i).getNombre() + ": $" + productosFiltrados.get(i).getPrecio());
        }
        int opcion = Utiles.ingresarEntero(-1, productosFiltrados.size() + 1);
        if (opcion == -1) return null;
        if (opcion == 0) return mostrarCrearProducto();
        productoSeleccionado = productosFiltrados.get(opcion - 1);
        return productoSeleccionado;



    }


    public static Producto mostrarMenuFiltrarProductos() {
        System.out.println("--- Filtrar Productos ---");

        // Seleccionar categoria
        System.out.println("Queres filtrar por categoria? (si/no)");
        String opcionCategoria = Utiles.ingresarString();
        Categoria categoriaFiltro = null;

        if (opcionCategoria.equalsIgnoreCase("si")) {
            categoriaFiltro = mostrarSeleccionarCategoria();
        }
        // Ingresar precio minimo
        System.out.print("Ingrese el precio minimo (deja vacio para omitir): ");
        Float precioMinimo = Utiles.ingresarFloat();

        // Ingresar precio maximo
        System.out.print("Ingrese el precio maximo (deja vacio para omitir): ");
        Float precioMaximo = Utiles.ingresarFloat();

        // Mostrar productos filtrados
        return mostrarProductosFiltrados(categoriaFiltro, precioMinimo, precioMaximo);

    }

    // Calcular comision de ventas
    public static void mostrarCalcularComision() {
        System.out.println("-- Estadisticas de los vendedores !--");
        for (Vendedor vendedor : vendedores) {
            List<Venta> ventasDelVendedor = ventas.stream().filter(e -> e.getVendedor().getCodigo() == vendedor.getCodigo()).toList();
            double totalVendido = ventasDelVendedor.stream().mapToDouble(v -> v.getProducto().getPrecio()).sum();
            double comision = ventasDelVendedor.size() > 2 ? totalVendido * 0.1 : totalVendido * 0.05;
            System.out.println(" -- Estadisticas  (" + vendedor.getCodigo() + ") " + vendedor.getNombre() + " -- ");
            System.out.println("Sueldo : $" + vendedor.getSueldo());
            System.out.println("Productos vendidos: " + ventasDelVendedor.size());
            System.out.println("Total : $" + totalVendido);
            System.out.println("Comision : $" + comision);
            System.out.println("-- Presione una tecla para continuar --");
            Utiles.s.nextLine();
        }
    }

    private static void mostrarRegistrarVenta() {
        Vendedor vendedor = mostrarSeleccionarVendedor();
        if (vendedor == null) return;
        Producto producto = mostrarMenuFiltrarProductos();
        if (producto == null) return;


        // Registrar la venta
        Venta venta = new Venta(vendedor, producto);
        ventas.add(venta);

        System.out.println(" -- Venta registrada: " + vendedor.getNombre() + " vendio " + producto.getNombre() + " -- ");
    }


    private static Producto mostrarCrearProducto() {
        Categoria categoria = mostrarSeleccionarCategoria();
        if (categoria == null) return null;

        System.out.print("Codigo del producto (numero): ");
        int codigo = Utiles.ingresarEntero(0, 1000);
        System.out.print("Nombre del producto: ");
        String nombre = Utiles.ingresarString();
        System.out.print("Precio del producto: ");
        float precio = Utiles.ingresarEntero(0, 100000);

        Producto producto = crearProducto(codigo, nombre, precio, categoria);
        if (producto == null) return null;
        System.out.println(" -- Producto creado: (" + producto.getCodigo() + ") " + categoria.getNombre() + " - " + producto.getNombre() + " $" + producto.getPrecio() + " -- ");
        return producto;

    }


    public static Vendedor mostrarSeleccionarVendedor() {
        System.out.println("-- Seleccione un/a vendedor/a --");
        Vendedor vendedorSeleccionado;
        System.out.println("-1 Salir");
        System.out.println("0 Crear vendedor ");
        for (int i = 0; i < vendedores.size(); i++) {
            System.out.println((i + 1) + " " + vendedores.get(i).getNombre());
        }

        int opcion = Utiles.ingresarEntero(-1, vendedores.size() + 1);
        if (opcion == -1) return null;
        if (opcion == 0) return mostrarCrearVendedor();
        vendedorSeleccionado = vendedores.get(opcion - 1);
        System.out.println("Seleccionaste al/la vendedor/a: " + vendedorSeleccionado.getNombre());
        return vendedorSeleccionado;
    }


    public static Categoria mostrarSeleccionarCategoria() {
        System.out.println("-- Seleccione una categoria --");
        Categoria categoriaSeleccionada;
        System.out.println("-1 Salir");
        System.out.println("0 Crear categoria");
        for (int i = 0; i < categorias.size(); i++) {
            System.out.println((i + 1) + " " + categorias.get(i).getNombre());
        }

        int opcion = Utiles.ingresarEntero(-1, categorias.size() + 1);
        if (opcion == -1) return null;
        if (opcion == 0) return mostrarCrearCategoria();
        categoriaSeleccionada = categorias.get(opcion - 1);
        System.out.println("Seleccionaste la categoria: " + categoriaSeleccionada.getNombre());
        return categoriaSeleccionada;
    }


    public static Categoria mostrarCrearCategoria() {
        System.out.println(" -- Crear categoria --");
        System.out.print("Nombre de la categoria: ");
        String nombre = Utiles.ingresarString();
        Categoria categoria = crearCategoria(nombre);
        if (categoria == null) return null;

        System.out.println(" -- Categoria creada: (" + categoria.getNombre() + ") -- ");
        return categoria;
    }

    public static Vendedor mostrarCrearVendedor() {
        System.out.println(" -- Crear vendedor --");
        System.out.print("Codigo del vendedor (numero): ");
        int codigo = Utiles.ingresarEntero(0, 1000);
        System.out.print("Nombre del vendedor: ");
        String nombre = Utiles.ingresarString();
        System.out.print("Sueldo del vendedor: ");
        float sueldo = Utiles.ingresarEntero(0, 100000);

        Vendedor vendedor = crearVendedor(codigo, nombre, sueldo);

        if (vendedor == null) return null;

        System.out.println(" -- Vendedor creado: (" + vendedor.getCodigo() + ") " + vendedor.getNombre() + " $" + vendedor.getSueldo() + " -- ");
        return vendedor;
    }

    public static Vendedor crearVendedor(int codigo, String nombre, float sueldo) {
        for (Vendedor ven : vendedores) {
            if (ven.getCodigo() == codigo) {
                System.out.println("Ya existe un vendedor con ese codigo (" + codigo + ") !");
                return null;
            }

        }
        Vendedor vendedor = new Vendedor(codigo, nombre, sueldo);
        vendedores.add(vendedor);
        return vendedor;
    }

    public static Producto crearProducto(int codigo, String nombre, float precio, Categoria categoria) {
        for (Producto prod : productos) {
            if (prod.getCodigo() == codigo) {
                System.out.println("Ya existe un producto con ese codigo  (" + codigo + ")  !");
                return null;
            }
        }
        Producto producto = new Producto(codigo, nombre, precio, categoria);
        productos.add(producto);
        return producto;
    }

    public static Categoria buscarCategoria(String nombre) {
        for (Categoria categoria : categorias) {
            if (categoria.getNombre().equalsIgnoreCase(nombre)) return categoria;
        }
        return null;
    }

    public static Categoria crearCategoria(String nombre) {
        // Busca si existe la categoria
        if (buscarCategoria(nombre) != null) {
            System.out.println("Ya existe una categoria con ese nombre !");
            return null;
        } else {
            // Crea la categoria
            Categoria categoria = new Categoria(nombre);
            categorias.add(categoria);
            return categoria;
        }
    }

}