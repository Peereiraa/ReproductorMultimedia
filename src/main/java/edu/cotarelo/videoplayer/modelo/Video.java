/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.cotarelo.videoplayer.modelo;

/**
 *
 * @author pablo
 * Clase que representa un archivo de vídeo en el reproductor multimedia.
 * Extiende la clase Multimedia y hereda los atributos y métodos de esta.
 */
class Video extends Multimedia {
    
    /**
     * Constructor de la clase Video.
     * @param nombre El nombre del archivo de vídeo.
     * @param url La URL del archivo de vídeo.
     * @param extension La extensión del archivo de vídeo.
     */
    public Video(String nombre, String url, String extension){
        super(nombre, url, extension);
    }
}
