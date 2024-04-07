/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.cotarelo.videoplayer.modelo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author pablo
 * 
 * Clase que representa una lista de archivos multimedia en el reproductor.
 *
 */
public class Lista {

    /** La lista de archivos multimedia. */
    public List<Multimedia> listArchivos;

    /** El índice del archivo multimedia actualmente seleccionado. */
    public int currentIndex;

    /** Indica si la reproducción es aleatoria o no. */
    boolean aleatorio = false;
    
    /**
     * Constructor de la clase Lista.
     */
    public Lista() {
        this.currentIndex = 0;
        listArchivos = new ArrayList<>();
    }
    
    /**
     * Obtiene la lista de archivos multimedia.
     * @return La lista de archivos multimedia.
     */
    public List<Multimedia> getListArchivos() {
        return listArchivos;
    }
    
    /**
     * Establece la lista de archivos multimedia.
     * @param listArchivos La lista de archivos multimedia.
     */
    public void setListArchivos(List<Multimedia> listArchivos) {
        this.listArchivos = listArchivos;
    }
    
    /**
     * Verifica si la reproducción es aleatoria.
     * @return true si la reproducción es aleatoria, false de lo contrario.
     */
    public boolean isAleatorio() {
        return aleatorio;
    }
    
    /**
     * Establece si la reproducción debe ser aleatoria o no.
     * @param aleatorio true para activar la reproducción aleatoria, false de lo contrario.
     */
    public void setAleatorio(boolean aleatorio) {
        this.aleatorio = aleatorio;
    }
    
    /**
     * Establece el índice del archivo multimedia seleccionado.
     * @param index El índice del archivo multimedia.
     */
    public void setSelectedIndex(int index) {
        this.currentIndex = index;
    }
    
    /**
     * Obtiene el índice del archivo multimedia seleccionado.
     * @return El índice del archivo multimedia seleccionado.
     */
    public int getSelectedIndex() {
        return this.currentIndex;
    }
    
    /**
     * Obtiene la extensión de un archivo.
     * @param f El archivo del que se desea obtener la extensión.
     * @return La extensión del archivo.
     */
    public String getExtension(File f) {
        if (f == null || !f.isFile()) {
            return null;
        }

        String fileName = f.getName();
        int index = fileName.lastIndexOf('.');
        if (index == -1 || index == fileName.length() - 1) {
            return "";
        }

        // Devuelve la extensión en minúsculas
        return fileName.substring(index).toLowerCase();
    }
    
    /**
     * Añade archivos a la lista de reproducción.
     * @param selectArchivos La lista de archivos a añadir.
     */
    public void añadirFicheros(List<File> selectArchivos) {
        if (selectArchivos != null) {

            for (File f : selectArchivos) {
                String nombre = f.getName();
                String extension = getExtension(f);

                if (!archivoExisteEnLista(nombre)) {
                    String url = f.toURI().toString();
                    if (extension.equals(".mp4") || extension.equals(".mov")) {
                        Multimedia m = new Video(nombre, url, extension);
                        listArchivos.add(m);
                    } else if (extension.equals(".mp3") || extension.equals(".wav")) {
                        // URL predeterminada para el video de fondo
                        String videoUrl = getClass().getResource("/edu/cotarelo/videoplayer/chill.mp4").toExternalForm();
                        Multimedia m = new Audio(nombre, url, extension, videoUrl);
                        listArchivos.add(m);
                    }
                }
            }
        }
    }
    
    /**
     * Verifica si un archivo ya existe en la lista.
     * @param nombreArchivo El nombre del archivo a verificar.
     * @return true si el archivo existe en la lista, false de lo contrario.
     */
    private boolean archivoExisteEnLista(String nombreArchivo) {
        for (Multimedia m : listArchivos) {
            if (m.getNombre().equals(nombreArchivo)) {
                return true;
            }
        }

        return false;
    }
    
    /**
     * Verifica si la lista de archivos multimedia está vacía.
     * @return true si la lista está vacía, false de lo contrario.
     */
    public boolean isEmpty() {
        if (listArchivos.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Obtiene el archivo multimedia siguiente en la lista de reproducción.
     * @return El archivo multimedia siguiente.
     */
    public Multimedia getSiguiente() {
        if (listArchivos == null || listArchivos.isEmpty()) {
            return null;
        }

        currentIndex++;

        if (currentIndex >= listArchivos.size()) {
            currentIndex = 0;
        }

        return listArchivos.get(currentIndex);
    }
    
    /**
     * Obtiene el archivo multimedia anterior en la lista de reproducción.
     * @return El archivo multimedia anterior.
     */
    public Multimedia getAnterior() {
        if (listArchivos == null || listArchivos.isEmpty()) {
            return null;
        }

        currentIndex--;

        if (currentIndex < 0) {
            currentIndex = 0;
        }

        return listArchivos.get(currentIndex);
    }
    
    /**
     * Obtiene el archivo multimedia en la posición especificada.
     * @param index La posición del archivo multimedia.
     * @return El archivo multimedia en la posición especificada.
     */
    public Multimedia get(int index) {
        if (listArchivos == null || listArchivos.isEmpty() || index < 0 || index >= listArchivos.size()) {
            return null;
        }

        return listArchivos.get(index);
    }
    
    /**
     * Obtiene el archivo multimedia actualmente seleccionado.
     * @return El archivo multimedia actualmente seleccionado.
     */
    public Multimedia getCurrent() {
        if (listArchivos == null || listArchivos.isEmpty() || currentIndex < 0 || currentIndex >= listArchivos.size()) {
            return null;
        }

        return listArchivos.get(currentIndex);
    }
    
    /**
     * Obtiene un archivo multimedia aleatorio de la lista.
     * @param multimediaActual El archivo multimedia actualmente reproducido.
     * @return Un archivo multimedia aleatorio.
     */
    public Multimedia getAleatorio(Multimedia multimediaActual) {
        if (listArchivos == null || listArchivos.isEmpty()) {
            return null;
        }

        if (aleatorio) {
            int newIndex;
            do {
                newIndex = (int) (Math.floor(Math.random() * listArchivos.size()));
            } while (listArchivos.get(newIndex).equals(multimediaActual)); // Evitar el mismo multimedia
            currentIndex = newIndex;
        }

        return listArchivos.get(currentIndex);
    }
    
    /**
     * Abre un diálogo de selección de archivos y añade los archivos seleccionados a la lista de reproducción.
     */
    public void select() {
        FileChooser fc = new FileChooser();

        fc.setTitle("Elige un fichero");
        List<File> selectFiles = fc.showOpenMultipleDialog(null);
        this.añadirFicheros(selectFiles);

    }
    
    /**
     * Elimina el archivo multimedia seleccionado de la lista de reproducción.
     * @param listView La vista de lista que muestra los archivos multimedia.
     */
    public void eliminarSeleccionado(ListView<String> listView) {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < listArchivos.size()) {
            Multimedia selectedMultimedia = listArchivos.get(selectedIndex);
            if (selectedIndex == currentIndex) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No se puede eliminar el multimedia que se está reproduciendo");

                alert.showAndWait();
            } else {
                listArchivos.remove(selectedIndex);
                if (selectedIndex < currentIndex) {
                    currentIndex--;
                }

                // Actualizar la ListView
                ObservableList<String> nombres = FXCollections.observableArrayList();
                for (Multimedia m : listArchivos) {
                    nombres.add(m.getNombre());
                }
                listView.setItems(nombres);
            }
        }
    }
    
    /**
     * Busca un archivo multimedia por su nombre.
     * @param nombreArchivo El nombre del archivo a buscar.
     * @return El archivo multimedia encontrado, o null si no se encontró.
     */
    public Multimedia buscarMultimediaPorNombre(String nombreArchivo) {
        for (Multimedia m : listArchivos) {
            if (m.getNombre().equals(nombreArchivo)) {
                return m;
            }
        }
        return null;
    }

}
