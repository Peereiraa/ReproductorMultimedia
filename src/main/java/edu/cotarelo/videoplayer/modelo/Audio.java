/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.cotarelo.videoplayer.modelo;

/**
 *
 * @author pablo
 * 
 * Clase que representa un archivo de audio en el reproductor multimedia.
 * Extiende la clase Multimedia e incluye la URL del vídeo de fondo asociado al audio.
 *
 */
public class Audio extends Multimedia {
    
    /** La URL del vídeo de fondo asociado al archivo de audio. */
    private String videoUrl;
    
    /**
     * Constructor de la clase Audio.
     * @param nombre El nombre del archivo de audio.
     * @param url La URL del archivo de audio.
     * @param extension La extensión del archivo de audio.
     * @param videoUrl La URL del vídeo de fondo asociado al archivo de audio.
     */
    public Audio(String nombre, String url, String extension, String videoUrl) {
        super(nombre, url, extension);
        this.videoUrl = videoUrl;
    }
    
    /**
     * Obtiene la URL del vídeo de fondo asociado al archivo de audio.
     * @return La URL del vídeo de fondo.
     */
    public String getVideoUrl() {
        return videoUrl;
    }
    
    /**
     * Establece la URL del vídeo de fondo asociado al archivo de audio.
     * @param videoUrl La URL del vídeo de fondo.
     */
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
