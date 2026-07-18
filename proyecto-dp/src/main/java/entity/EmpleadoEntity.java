package entity;

public class EmpleadoEntity {

    private String idEmpleado;
    private String nombre;
    private String apellidos;
    private String email;
    private String usuario;
    private String clave;

    public EmpleadoEntity() {
    }

    public EmpleadoEntity(String idEmpleado, String nombre, String apellidos, String email, String usuario, String clave) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.usuario = usuario;
        this.clave = clave;
    }

    // Getters y Setters
    public String getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(String idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}