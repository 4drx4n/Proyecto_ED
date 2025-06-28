package model;

/**
 * Clase que representa un producto dentro del sistema.
 * Contiene información básica como su identificador, nombre, precio, stock y categoría.
 */
public class Producto {

	private int idProducto;
	private String nombre;
	private double precio;
	private int stock;
	private String categoría;

	/**
	 * Constructor vacío. Necesario para ciertas operaciones de frameworks o instanciación sin datos.
	 */
	public Producto() {}

	/**
	 * Constructor completo que inicializa todos los atributos del producto.
	 * @param idProducto Identificador único del producto.
	 * @param nombre Nombre del producto.
	 * @param precio Precio unitario del producto.
	 * @param stock Cantidad disponible en inventario.
	 * @param categoría Categoría a la que pertenece el producto.
	 */
	public Producto(int idProducto, String nombre, double precio, int stock, String categoría) {
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
		this.categoría = categoría;
	}

	/**
	 * Constructor que inicializa el producto sin categoría.
	 * @param idProducto Identificador del producto.
	 * @param nombre Nombre del producto.
	 * @param precio Precio del producto.
	 * @param stock Stock disponible.
	 */
	public Producto(int idProducto, String nombre, double precio, int stock) {
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
	}

	/**
	 * Constructor utilizado para listar productos sin necesidad de ID ni categoría.
	 * @param nombre Nombre del producto.
	 * @param precio Precio del producto.
	 * @param stock Stock disponible.
	 */
	public Producto(String nombre, double precio, int stock) {
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
	}

	/** @return el ID del producto */
	public int getIdProducto() {
		return idProducto;
	}

	/** @param idProducto el ID del producto a establecer */
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	/** @return el nombre del producto */
	public String getNombre() {
		return nombre;
	}

	/** @param nombre el nombre del producto a establecer */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/** @return el precio del producto */
	public double getPrecio() {
		return precio;
	}

	/** @param precio el precio del producto a establecer */
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	/** @return el stock disponible del producto */
	public int getStock() {
		return stock;
	}

	/** @param stock la cantidad de stock a establecer */
	public void setStock(int stock) {
		this.stock = stock;
	}

	/** @return la categoría del producto */
	public String getCategoría() {
		return categoría;
	}

	/** @param categoría la categoría del producto a establecer */
	public void setCategoría(String categoría) {
		this.categoría = categoría;
	}
}
