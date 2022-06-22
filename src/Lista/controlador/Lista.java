/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lista.controlador;

import Lista.modelo.Nodo;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 *
 * @author Usuario
 */
public class Lista<T> implements Serializable {
     private Nodo cabecera;
    private Class clazz;
    private Integer nro_elem;

    public static final Integer ASCENDENTE = 1;
    public static final Integer DESCENDENTE = 2;

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public boolean estaVacia() {
        return this.cabecera == null;
    }

    public boolean estaLlena() {
        return tamanio() == nro_elem;
    }

    private void insertar(T dato) {
        Nodo nodo = new Nodo(dato, cabecera);
        cabecera = nodo;
    }

    private void insertarFinal(T dato) {
        insertar(dato, tamanio());
    }

    /**
     * Insertar dato por pisicion
     *
     * @param dato El dato a insertr
     * @param pos La posicion a insertar
     */
    public void insertar(T dato, int pos) {
        if (estaVacia() || pos <= 0) {
            insertar(dato);
        } else {
            Nodo iterador = cabecera;
            for (int i = 0; i < pos - 1; i++) {
                if (iterador.getNodoSiguiente() == null) {
                    break;
                }
                iterador = iterador.getNodoSiguiente();
            }
            Nodo tmp = new Nodo(dato, iterador.getNodoSiguiente());
            iterador.setNodoSiguiente(tmp);
        }
    }

    /**
     * Agregar item a lista ascendente, queire decir que el primer elemento es
     * la cabecera
     *
     * @param dato El dato a agregar
     */
    public void insertarNodo(T dato) {
        if (tamanio() > 0) {
            insertarFinal(dato);
        } else {
            insertar(dato);
        }
    }

    /**
     * Retorno el tama;o de la lista return numero de elementos
     */
    public int tamanio() {
        int cont = 0;
        Nodo tmp = cabecera;
        while (!estaVacia() && tmp != null) {
            cont++;
            tmp = tmp.getNodoSiguiente();
        }
        return cont;
    }

    /**
     * Permite extraer el primer dato de la lista
     *
     * @return el dato
     */
    public T extraer() {
        T dato = null;
        if (!estaVacia()) {
            dato = (T) cabecera.getDato();
            cabecera = cabecera.getNodoSiguiente();
        }
        return dato;
    }

    /**
     *
     * @Permite consulta dato por pisicion
     * @return dato encontrado en la posicion
     */
    public T consultarDatoPosicion(int pos) {
        T dato = null;
        if (!estaVacia() && (pos >= 0 && pos <= tamanio() - 1)) {
            Nodo tmp = cabecera;
            for (int i = 0; i < pos; i++) {
                tmp = tmp.getNodoSiguiente();
                if (tmp == null) {
                    break;
                }
            }
            if (tmp != null) {
                dato = (T) tmp.getDato();
            }
        }
        return dato;
    }

    public void imprimir() {
        Nodo tmp = cabecera;
        while (!estaVacia() && tmp != null) {
            System.out.println(tmp.getDato());
            tmp = tmp.getNodoSiguiente();
        }
    }

    public void eliminarNodo(T dato) {
        Nodo tmp = cabecera;
        Nodo anterior = null;
        while (!estaVacia() && tmp != null) {
            if (tmp == dato) {
                boolean x = tmp == dato;
                if (tmp == cabecera) {
                    cabecera = cabecera.getNodoSiguiente();
                } else {
                    anterior.setNodoSiguiente(tmp.getNodoSiguiente());
                }
            }

            anterior = tmp;
            tmp = tmp.getNodoSiguiente();
        }
    }

    public boolean modificarPorPos(T dato, int pos) {
        if (!estaVacia() && (pos <= tamanio() - 1) && pos >= 0) {
            Nodo iterador = cabecera;
            for (int i = 0; i < pos; i++) {
                iterador = iterador.getNodoSiguiente();
                if (iterador == null) {
                    break;
                }
            }
            if (iterador != null) {
                iterador.setDato(dato);
                return true;
            }
        }
        return false;
    }

    private Field getField(String nombre) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equalsIgnoreCase(nombre)) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }

    private Object Value(T dato, String atributo) throws Exception {
        return getField(atributo).get(dato);
    }

    public Lista<T> seleccion_clase(String atributo, Integer ordenacion) {
        try {
            int i, j, k = 0;
            T t = null;
            int n = tamanio();
            for (i = 0; i < n - 1; i++) {
                k = i;
                t = consultarDatoPosicion(i);
                
                for (j = i + 1; j < n; j++) {
                    boolean band = false;
                    Object datoT = Value(t, atributo);
                    Object datoJ = Value(consultarDatoPosicion(j), atributo);
                    if (datoT instanceof Number) {
                        Number aux = (Number) datoT;
                        Number numero = (Number) datoJ;
                        band = (ordenacion.intValue() == Lista.ASCENDENTE.intValue())
                                ? numero.doubleValue() < aux.doubleValue()
                                : numero.doubleValue() > aux.doubleValue();
                    } else {
                        band = (ordenacion.intValue() == Lista.ASCENDENTE.intValue())
                                ? datoT.toString().compareTo(datoJ.toString()) > 0
                                : datoT.toString().compareTo(datoJ.toString()) < 0;
                    }
                    if (band) {
                        t = consultarDatoPosicion(j);
                        k = j;
                    }
                }
                modificarPorPos(consultarDatoPosicion(i), k);
                modificarPorPos(t, i);
            }
        } catch (Exception e) {
            System.out.println("Error en ordenacion" + e);
        }
        return this;
    }
    
    public Lista<T> QuisortClase(String atributo, int primero, int ultimo, Integer direccion) {
        try {
            int i, j, central;
            Object pivote;
            central = (primero + ultimo) / 2;
            pivote = consultarDatoPosicion(central);
            i = primero;
            j = ultimo;
            if (pivote instanceof Number) {
                do {
                    if (direccion.intValue() == Lista.ASCENDENTE) {
                        while (((Number) Value(consultarDatoPosicion(i), atributo)).doubleValue() < ((Number) Value(consultarDatoPosicion(central), atributo)).doubleValue()) {
                            i++;
                        }
                        while (((Number) Value(consultarDatoPosicion(j), atributo)).doubleValue() > ((Number) Value(consultarDatoPosicion(central), atributo)).doubleValue()) {
                            j--;
                        }
                    } else {
                        while (((Number) Value(consultarDatoPosicion(i), atributo)).doubleValue() > ((Number) Value(consultarDatoPosicion(central), atributo)).doubleValue()) {
                            i++;
                        }
                        while (((Number) Value(consultarDatoPosicion(j), atributo)).doubleValue() < ((Number) Value(consultarDatoPosicion(central), atributo)).doubleValue()) {
                            j--;
                        }
                    }

                    if (i <= j) {
                        T auxiliar = consultarDatoPosicion(i);
                        modificarPorPos(consultarDatoPosicion(j), i);
                        modificarPorPos(auxiliar, j);
                        i++;
                        j--;
                    }
                } while (i <= j);

            } else {
                do {
                    if (direccion.intValue() == Lista.ASCENDENTE) {
                        while (Value(consultarDatoPosicion(central), atributo).toString().compareTo(Value(consultarDatoPosicion(i), atributo).toString()) > 0) {
                            i++;
                        }
                        while (Value(consultarDatoPosicion(j), atributo).toString().compareTo(Value(consultarDatoPosicion(central), atributo).toString()) > 0) {
                            j--;
                        }
                    } else {
                        while (Value(consultarDatoPosicion(central), atributo).toString().compareTo(Value(consultarDatoPosicion(i), atributo).toString()) < 0) {
                            i++;
                        }
                        while (Value(consultarDatoPosicion(j), atributo).toString().compareTo(Value(consultarDatoPosicion(central), atributo).toString()) < 0) {
                            j--;
                        }
                    }
                    if (i <= j) {
                        T auxiliar = consultarDatoPosicion(i);
                        modificarPorPos(consultarDatoPosicion(j), i);
                        modificarPorPos(auxiliar, j);
                        i++;
                        j--;
                    }
                } while (i <= j);

            }
            if (primero < j) {
                QuisortClase(atributo, primero, j, direccion);
            }
            if (i < ultimo) {
                QuisortClase(atributo, i, ultimo, direccion);
            }
        } catch (Exception e) {
            System.out.println("Error quiscksort" + e);
        }
        return this;
    }

    public Lista<T> ShellClase(Integer direccion, String atributo) {
        try {
            int intervalo, i, j, k;
            int n = tamanio();
            intervalo = n / 2;
            while (intervalo > 0) {
                for (i = intervalo; i < n; i++) {
                    j = i - intervalo;
                    if (consultarDatoPosicion(intervalo) instanceof Number) {
                        while (j >= 0) {
                            k = j + intervalo;
                            if (direccion.intValue() == Lista.ASCENDENTE.intValue()) {
                                if (((Number) Value(consultarDatoPosicion(j), atributo)).intValue() <= ((Number) Value(consultarDatoPosicion(k), atributo)).intValue()) {
                                    j = -1;
                                } else {
                                    T aux = consultarDatoPosicion(j);
                                    modificarPorPos(consultarDatoPosicion(j + 1), j);
                                    modificarPorPos(aux, j + 1);
                                    j -= intervalo;
                                }
                            } else {
                                if (((Number) Value(consultarDatoPosicion(j), atributo)).intValue() >= ((Number) Value(consultarDatoPosicion(k), atributo)).intValue()) {
                                    j = -1;
                                } else {
                                    T aux = consultarDatoPosicion(j);
                                    modificarPorPos(consultarDatoPosicion(j + 1), j);
                                    modificarPorPos(aux, j + 1);
                                    j -= intervalo;
                                }
                            }
                        }
                    } else {
                        while (j >= 0) {
                            k = j + intervalo;
                            if (direccion.intValue() == Lista.ASCENDENTE.intValue()) {
                                if (Value(consultarDatoPosicion(k), atributo).toString().compareTo(Value(consultarDatoPosicion(j), atributo).toString()) >= 0) {
                                    j = -1;
                                } else {
                                    T aux = consultarDatoPosicion(j);
                                    modificarPorPos(consultarDatoPosicion(j + 1), j);
                                    modificarPorPos(aux, j + 1);
                                    j -= intervalo;
                                }
                            } else {
                                if (Value(consultarDatoPosicion(j), atributo).toString().compareTo(Value(consultarDatoPosicion(k), atributo).toString()) >= 0) {
                                    j = -1;
                                } else {
                                    T aux = consultarDatoPosicion(j);
                                    modificarPorPos(consultarDatoPosicion(j + 1), j);
                                    modificarPorPos(aux, j + 1);
                                    j -= intervalo;
                                }
                            }
                        }
                    }
                }
                intervalo = intervalo / 2;
            }
        } catch (Exception e) {
            System.out.println("Error en Shell: "+e);
        }
        return this;
    }
    
    public Lista<T> BusquedaBinariaClase(Object elemento, String atributo) {
        try {
            Lista auxiliar = new Lista();
            int centro, primero, ultimo;
            primero = 0;
            ultimo = tamanio() - 1;
            while (primero <= ultimo) {
                if (consultarDatoPosicion(0) instanceof Number) {
                    centro = (primero + ultimo) / 2;
                    if ( ((Number) Value((T) elemento, atributo)).intValue() == ((Number) Value(consultarDatoPosicion(centro), atributo)).intValue()) {
                        T po = consultarDatoPosicion(centro);
                        auxiliar.insertarNodo(po);
                        return auxiliar;
                    } else if (((Number) Value((T) elemento, atributo)).intValue() < ((Number) Value(consultarDatoPosicion(centro), atributo)).intValue()){
                        ultimo = centro - 1;
                    } else {
                        primero = centro + 1;
                    }
                } else {
                    centro = (primero + ultimo) / 2;
                    if (Value(consultarDatoPosicion(centro), atributo).toString().toLowerCase().contains(elemento.toString().toLowerCase())) {
                        auxiliar.insertarNodo(consultarDatoPosicion(centro));
                        return auxiliar;
                    } else if (Value(consultarDatoPosicion(centro), atributo).toString().toLowerCase().compareTo(elemento.toString().toLowerCase()) > 0) {
                        ultimo = centro - 1;
                    } else {
                        primero = centro + 1;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Hay un error en busqueda: "+e);
        }
        return null;
    }
}
