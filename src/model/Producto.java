package model;

public class Producto {

	private String nombre;
	private double precio;
	private int stock;
	private String categoría;
	
	public Producto(String nombre, String detalles, double precio, int stock, String categoría) {
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
		this.categoría = categoría;	
	}
	
	public Producto(String nombre, double precio, int stock) {
		this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	public int getStock() {
		return stock;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public String getCategoría() {
		return categoría;
	}
	
	public void setCategoría(String categoría) {
		this.categoría = categoría;
	}
	
}
