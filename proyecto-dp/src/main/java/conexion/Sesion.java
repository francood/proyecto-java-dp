package conexion;

import entity.EmpleadoEntity;

public class Sesion {
    private static EmpleadoEntity empleadoActual;

    public static void iniciarSesion(EmpleadoEntity empleado) {
        empleadoActual = empleado;
    }

    public static EmpleadoEntity getEmpleado() {
        return empleadoActual;
    }

    public static String getIdEmpleado() {
        return empleadoActual != null ? empleadoActual.getIdEmpleado() : null;
    }

    public static void cerrarSesion() {
        empleadoActual = null;
    }
}
