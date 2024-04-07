package edu.cotarelo.videoplayer;

import edu.cotarelo.videoplayer.modelo.Lista;
import edu.cotarelo.videoplayer.modelo.Multimedia;
import edu.cotarelo.videoplayer.modelo.Reproductor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * Controlador principal de la aplicación VideoPlayer. Maneja la interfaz
 * gráfica y la funcionalidad principal del reproductor multimedia.
 */
public class PrimaryController {

    /**
     * El reproductor de medios.
     */
    private MediaPlayer mediaPlayer;

    /**
     * Indica si el reproductor está pausado.
     */
    private boolean isPaused = false;

    /**
     * La lista de reproducción asociada al reproductor.
     */
    private Lista lista;

    /**
     * El reproductor de archivos multimedia.
     */
    private Reproductor reproductor;

    /**
     * El control deslizante para el volumen de reproducción.
     */
    @FXML
    private Slider sliderVolume;

    /**
     * El control deslizante para el tiempo de reproducción.
     */
    @FXML
    private Slider sliderTiempo;

    /**
     * La lista de archivos multimedia en la interfaz gráfica.
     */
    @FXML
    private ListView<String> listView;

    /**
     * La vista de medios para mostrar el video.
     */
    @FXML
    private MediaView elMediaView;

    /**
     * La casilla de verificación para la reproducción aleatoria.
     */
    @FXML
    private CheckBox checkBoxRandom;

    /**
     * La etiqueta para mostrar la duración del archivo multimedia.
     */
    @FXML
    private Label lbDuration;

    /**
     * Inicializa el controlador. Configura el reproductor multimedia y el
     * contenedor de la vista de medios.
     */
    public void initialize() {
        reproductor = new Reproductor(sliderTiempo, lbDuration, sliderVolume, elMediaView);
        HBox hbox = (HBox) elMediaView.getParent();
        elMediaView.fitHeightProperty().bind(hbox.heightProperty());
        elMediaView.fitWidthProperty().bind(hbox.widthProperty());

    }

    /**
     * Evento que se activa cuando se arrastra un archivo de vídeo sobre la
     * ventana. Acepta el arrastre de archivos de vídeo y configura los modos de
     * transferencia.
     *
     * @param event Evento de arrastre del ratón.
     */
    @FXML
    void arrastrarVideo(DragEvent event) {
        if (event.getGestureSource() != elMediaView && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    /**
     * Evento que se activa al soltar un archivo de vídeo en la ventana. Añade
     * los archivos de vídeo soltados a la lista de reproducción y reproduce el
     * primer vídeo.
     *
     * @param event Evento de arrastrar y soltar archivos.
     */
    @FXML
    void soltarVideo(DragEvent event) {
        List<File> files = event.getDragboard().getFiles();
        reproductor.getLista().añadirFicheros(files);

        // Llama a prepareFirstVideo() justo después de añadir los archivos
        reproductor.prepareFirstVideo();

        // Actualiza la lista en la interfaz gráfica
        actualizarLista();

        event.setDropCompleted(true);
        event.consume();
    }

    /**
     * Método que se activa cuando se entra en el área de arrastre de archivos
     * del ListView. Cambia el estilo del ListView para resaltar visualmente que
     * se puede soltar un archivo.
     *
     * @param event Evento de arrastre del ratón.
     */
    @FXML
    void dragEntered(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            listView.setStyle("-fx-background-color: #d0d0d0;");
        }
        event.consume();
    }

    /**
     * Método que se activa cuando se sale del área de arrastre de archivos del
     * ListView. Restaura el estilo por defecto del ListView.
     *
     * @param event Evento de arrastre del ratón.
     */
    @FXML
    void dragExited(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            listView.setStyle("");
        }
        event.consume();
    }

    /**
     * Actualiza la lista de archivos en la interfaz gráfica.
     */
    private void actualizarLista() {
        ObservableList<String> nombres = FXCollections.observableArrayList();
        for (Multimedia m : reproductor.getLista().getListArchivos()) {
            nombres.add(m.getNombre());
        }
        listView.setItems(nombres);
    }

    /**
     * Evento que se activa al hacer clic en el botón de eliminar. Elimina el
     * archivo multimedia seleccionado de la lista de reproducción.
     *
     * @param event Evento de clic del mouse.
     */
    @FXML
    void onClickEliminar(MouseEvent event) {
        reproductor.getLista().eliminarSeleccionado(listView);

    }

    /**
     * Evento que se activa al hacer clic en el botón de reproducción aleatoria.
     * Cambia el modo de reproducción entre aleatorio y normal.
     *
     * @param event Evento de acción del botón.
     */
    @FXML
    void reproducirAleatoriamente(ActionEvent event) {

        boolean isChecked = checkBoxRandom.isSelected();
        reproductor.getLista().setAleatorio(isChecked);
    }

    /**
     * Evento que se activa al hacer clic en el botón de reproducción. Inicia la
     * reproducción del vídeo actual o del primer vídeo si no hay ninguno en
     * reproducción.
     *
     * @param event Evento de clic del mouse.
     */
    @FXML
    void play(MouseEvent event) {
        if (mediaPlayer == null) {
            mediaPlayer = reproductor.prepare();
            elMediaView.setMediaPlayer(mediaPlayer);
        }

        if (mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            mediaPlayer.play();
        }
    }

    /**
     * Evento que se activa al hacer clic en el botón de reinicio. Reinicia el
     * vídeo actual.
     *
     * @param event Evento de clic del mouse.
     */
    @FXML
    void reset(MouseEvent event) {
        reproductor.reset();
    }

    /**
     * Evento que se activa al hacer clic en el botón de pausa/parada. Pausa la
     * reproducción del vídeo actual.
     *
     * @param event Evento de clic del mouse.
     */
    @FXML
    void stop(MouseEvent event) {
        reproductor.pause();
    }

    /**
     * Evento que se activa al seleccionar un elemento en la lista de
     * reproducción. Selecciona el elemento y lo prepara para su reproducción.
     *
     * @param event Evento de clic del mouse.
     */
    @FXML
    void selectMielda(MouseEvent event) {
        reproductor.select();
        reproductor.prepareFirstVideo();

        ObservableList<String> nombres = FXCollections.observableArrayList();

        for (Multimedia m : reproductor.getLista().getListArchivos()) {
            nombres.add(m.getNombre());
        }

        listView.setItems(nombres);
    }

    /**
     * Evento que se activa al hacer clic en el botón de vídeo anterior.
     * Reproduce el vídeo anterior en la lista de reproducción.
     *
     * @param event Evento de clic del mouse.
     */
    @FXML
    void videoanterior(MouseEvent event) {
        reproductor.previous();
        mediaPlayer = reproductor.prepare();
        elMediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
    }

    /**
     * Evento que se activa al hacer clic en el botón de vídeo siguiente.
     * Reproduce el siguiente vídeo en la lista de reproducción.
     *
     * @param event Evento de clic del mouse.
     */
    @FXML
    void videosiguiente(MouseEvent event) {
        reproductor.next();
        mediaPlayer = reproductor.prepare();
        elMediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
    }

    /**
     * Evento que se activa al hacer doble clic en un elemento de la lista de
     * reproducción. Reproduce el vídeo seleccionado en la lista.
     *
     * @param event Evento de doble clic del mouse.
     */
    @FXML
    void reproducirVideoSeleccionado(MouseEvent event) {
        if (event.getClickCount() == 2) {
            String nombreArchivo = listView.getSelectionModel().getSelectedItem();
            if (nombreArchivo != null) {
                Multimedia multimedia = reproductor.getLista().buscarMultimediaPorNombre(nombreArchivo);
                if (multimedia != null) {
                    reproductor.getLista().setSelectedIndex(reproductor.getLista().getListArchivos().indexOf(multimedia));
                    mediaPlayer = reproductor.prepare();
                    elMediaView.setMediaPlayer(mediaPlayer);
                    mediaPlayer.play();
                }
            }
        }
    }

}
