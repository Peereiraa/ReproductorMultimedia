/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.cotarelo.videoplayer.modelo;

import java.io.File;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

/**
 *
 * @author pablo
 *
 * Clase que gestiona la reproducción de archivos multimedia.
 */
public class Reproductor {

    /**
     * El archivo multimedia actualmente en reproducción.
     */
    public Media media;

    /**
     * El reproductor de medios.
     */
    public MediaPlayer mediaPlayer;

    /**
     * El reproductor de audios.
     */
    public MediaPlayer mediaAudio;

    /**
     * La lista de reproducción asociada al reproductor.
     */
    public Lista lista;

    /**
     * La vista de medios para mostrar el video.
     */
    public MediaView mediaView;

    /**
     * El control deslizante para el tiempo de reproducción.
     */
    public Slider sliderTiempo;

    /**
     * La etiqueta para mostrar la duración del archivo multimedia.
     */
    public Label lbDuration;

    /**
     * El control deslizante para el volumen de reproducción.
     */
    public Slider sliderVolumen;

    /**
     * Constructor de la clase Reproductor.
     *
     * @param sliderTiempo El control deslizante para el tiempo de reproducción.
     * @param lbDuration La etiqueta para mostrar la duración del archivo
     * multimedia.
     * @param sliderVolumen El control deslizante para el volumen de
     * reproducción.
     * @param mediaView La vista de medios para mostrar el video.
     */
    public Reproductor(Slider sliderTiempo, Label lbDuration, Slider sliderVolumen, MediaView mediaView) {
        this.lista = new Lista();
        this.sliderTiempo = sliderTiempo;
        this.lbDuration = lbDuration;
        this.sliderVolumen = sliderVolumen;
        this.mediaView = mediaView;
    }

    /**
     * Obtiene el archivo multimedia siguiente, anterior o aleatorio dependiendo
     * de los parámetros.
     *
     * @param anterior true si se desea obtener el archivo anterior, false de lo
     * contrario.
     * @param aleatorio true si se desea obtener un archivo aleatorio, false de
     * lo contrario.
     * @param siguiente true si se desea obtener el archivo siguiente, false de
     * lo contrario.
     * @return El archivo multimedia obtenido.
     */
    public Multimedia getMultimedia(boolean anterior, boolean aleatorio, boolean siguiente) {
        if (lista == null || lista.isEmpty()) {
            return null;
        }

        if (siguiente && !aleatorio) {
            return lista.getSiguiente();
        } else if (anterior && !aleatorio) {
            return lista.getAnterior();
        } else if (aleatorio) {
            Multimedia multimediaActual = lista.getCurrent();
            return lista.getAleatorio(multimediaActual);
        } else {
            return lista.get(0);
        }
    }

    /**
     * Inicia la reproducción del archivo multimedia.
     *
     * @return El reproductor de medios.
     */
    public MediaPlayer play() {
        if (media != null && mediaPlayer != null) {
            try {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                mediaPlayer.play();
                mediaPlayer.setAutoPlay(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return mediaPlayer;
    }

    /**
     * Reinicia la reproducción del archivo multimedia.
     *
     * @return El reproductor de medios.
     */
    public MediaPlayer reset() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.seek(Duration.ZERO);
        }
        return mediaPlayer;
    }

    /**
     * Pausa la reproducción del archivo multimedia.
     *
     * @return El reproductor de medios.
     */
    public MediaPlayer pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        return mediaPlayer;
    }

    /**
     * Abre un diálogo de selección de archivos y añade los archivos
     * seleccionados a la lista de reproducción.
     */
    public void select() {
        lista.select();

        if (!lista.isEmpty()) {
            prepare();
        }
    }

    /**
     * Prepara el archivo multimedia para la reproducción.
     *
     * @return El reproductor de medios.
     */
    public MediaPlayer prepare() {
        Multimedia archivo = lista.getCurrent();
        if (archivo != null) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            }

            Media media = new Media(archivo.getUrl());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            mediaPlayer.currentTimeProperty().addListener((observableValue, oldValue, newValue) -> {
                sliderTiempo.setValue(newValue.toSeconds());
                Duration currentTime = mediaPlayer.getCurrentTime();
                lbDuration.setText(formatTime(currentTime.toSeconds()) + " / " + formatTime(mediaPlayer.getTotalDuration().toSeconds()));
            });

            mediaPlayer.setOnReady(() -> {
                sliderTiempo.setMax(mediaPlayer.getTotalDuration().toSeconds());
                mediaPlayer.play();
            });

            mediaPlayer.setOnEndOfMedia(() -> {
                
                next();
            });

            mediaPlayer.setOnPlaying(() -> {
                mediaView.setMediaPlayer(mediaPlayer);
            });

            sliderTiempo.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (sliderTiempo.isValueChanging()) {
                    mediaPlayer.seek(Duration.seconds(newValue.doubleValue()));
                }
            });

            sliderVolumen.setValue(mediaPlayer.getVolume() * 100);
            sliderVolumen.valueProperty().addListener((observable, oldValue, newValue) -> {
                mediaPlayer.setVolume(newValue.doubleValue() / 100.0);
            });
        }
        return mediaPlayer;
    }

    /**
     * Formatea el tiempo en segundos a un formato "MM:SS".
     *
     * @param seconds Los segundos a formatear.
     * @return El tiempo formateado.
     */
    private String formatTime(double seconds) {
        int minutes = (int) seconds / 60;
        int secs = (int) seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }

    /**
     * Reproduce el archivo multimedia siguiente.
     */
    public void next() {
        Multimedia s;

        if (!lista.isAleatorio()) {
            s = getMultimedia(false, false, true);
        } else {
            Multimedia multimediaActual = lista.getCurrent();
            do {
                s = lista.getAleatorio(multimediaActual);
            } while (s == null); // Manejar el caso de no obtener multimedia válido
        }

        if (s != null) {
            prepare();
        }
    }

    /**
     * Reproduce el archivo multimedia anterior.
     */
    public void previous() {
        Multimedia s;

        if (!lista.isAleatorio()) {
            s = getMultimedia(true, false, false);
        } else {
            Multimedia multimediaActual = lista.getCurrent();
            do {
                s = lista.getAleatorio(multimediaActual);
            } while (s == null); // Manejar el caso de no obtener multimedia válido
        }

        if (s != null) {
            prepare();
        }
    }

    /**
     * Añade archivos multimedia a la lista de reproducción.
     *
     * @param file La lista de archivos a añadir.
     */
    public void añadirArchivo(List<File> file) {
        lista.añadirFicheros(file);
    }

    /**
     * Obtiene la lista de reproducción asociada al reproductor.
     *
     * @return La lista de reproducción.
     */
    public Lista getLista() {
        return lista;
    }

    /**
     * Prepara y reproduce el primer video en la lista de reproducción.
     */
    public void prepareFirstVideo() {
        if (!lista.isEmpty()) {
            Multimedia primerVideo = lista.get(0);
            if (primerVideo != null) {
                prepare(); // Preparar el primer video
                play(); // Reproducir el primer video
            }
        }
    }
}
