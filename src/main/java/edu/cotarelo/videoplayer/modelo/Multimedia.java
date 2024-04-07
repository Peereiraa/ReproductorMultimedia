/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.cotarelo.videoplayer.modelo;

import java.io.File;

/**
 *
 * @author pablo
 * 
 * Clase que representa un archivo multimedia en el reproductor.
 *
 */
public class Multimedia {
    
    /** El nombre del archivo multimedia. */
    public String nombre;
    
    /** La URL del archivo multimedia. */
    public String url;
    
    /** El objeto File asociado al archivo multimedia. */
    public File file;
    
    /** La extensión del archivo multimedia. */
    public String extension;
    
    /** La duración del archivo multimedia en segundos. */
    public int duracion;
    
    /**
     * Constructor de la clase Multimedia.
     * @param nombre El nombre del archivo multimedia.
     * @param url La URL del archivo multimedia.
     * @param extension La extensión del archivo multimedia.
     */
    public Multimedia(String nombre, String url, String extension){
        this.nombre = nombre;
        this.extension = extension;
        this.file = file;
        this.url = url;
    }

    /**
     * Obtiene el nombre del archivo multimedia.
     * @return El nombre del archivo multimedia.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del archivo multimedia.
     * @param nombre El nombre del archivo multimedia.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la URL del archivo multimedia.
     * @return La URL del archivo multimedia.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece la URL del archivo multimedia.
     * @param url La URL del archivo multimedia.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Obtiene el objeto File asociado al archivo multimedia.
     * @return El objeto File asociado al archivo multimedia.
     */
    public File getFile() {
        return file;
    }

    /**
     * Establece el objeto File asociado al archivo multimedia.
     * @param file El objeto File asociado al archivo multimedia.
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Obtiene la extensión del archivo multimedia.
     * @return La extensión del archivo multimedia.
     */
    public String getExtension() {
        return extension;
    }

    /**
     * Establece la extensión del archivo multimedia.
     * @param extension La extensión del archivo multimedia.
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * Obtiene la duración del archivo multimedia en segundos.
     * @return La duración del archivo multimedia en segundos.
     */
    public int getDuracion() {
        return duracion;
    }

    /**
     * Establece la duración del archivo multimedia en segundos.
     * @param duracion La duración del archivo multimedia en segundos.
     */
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }  
}