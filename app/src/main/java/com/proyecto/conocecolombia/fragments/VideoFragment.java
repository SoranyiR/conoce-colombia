package com.proyecto.conocecolombia.fragments;

import android.media.MediaPlayer;
import android.os.Build;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.proyecto.conocecolombia.R;

import java.util.Locale;

public class VideoFragment extends Fragment {

    private TextView txtTituloVideo;
    private TextView txtEstadoVideo;
    private TextView txtTiempoActual;
    private TextView txtDuracionVideo;
    private TextView btnPlayCentral;

    private VideoView videoColombia;
    private MediaPlayer mediaPlayer;

    private Button btnRetroceder;
    private Button btnPlayPause;
    private Button btnAdelantar;

    private SeekBar seekVideo;

    private static final int SALTO_VIDEO = 10000;

    private final Handler handler =
            new Handler(Looper.getMainLooper());

    private boolean usuarioMoviendoSeekBar = false;
    private boolean videoPreparado = false;


    public VideoFragment() {
        // Constructor vacío obligatorio
    }


    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_video,
                container,
                false
        );

        vincularVistas(view);
        configurarVideo();
        configurarEventos();
        configurarSeekBar();

        return view;
    }


    /*
     * ============================================================
     * VINCULAR VISTAS
     * ============================================================
     */

    private void vincularVistas(View view) {

        txtTituloVideo =
                view.findViewById(R.id.txtTituloVideo);

        txtEstadoVideo =
                view.findViewById(R.id.txtEstadoVideo);

        txtTiempoActual =
                view.findViewById(R.id.txtTiempoActual);

        txtDuracionVideo =
                view.findViewById(R.id.txtDuracionVideo);

        btnPlayCentral =
                view.findViewById(R.id.btnPlayCentral);

        videoColombia =
                view.findViewById(R.id.videoColombia);

        btnRetroceder =
                view.findViewById(R.id.btnRetroceder);

        btnPlayPause =
                view.findViewById(R.id.btnPlayPause);

        btnAdelantar =
                view.findViewById(R.id.btnAdelantar);

        seekVideo =
                view.findViewById(R.id.seekVideo);
    }


    /*
     * ============================================================
     * CONFIGURAR VIDEO
     * ============================================================
     */

    private void configurarVideo() {

        /*
         * Al principio deshabilitamos los controles.
         * Solo se habilitan cuando el video esté preparado.
         */
        btnPlayPause.setEnabled(false);
        btnRetroceder.setEnabled(false);
        btnAdelantar.setEnabled(false);
        seekVideo.setEnabled(false);

        txtEstadoVideo.setText(
                "🎥 Preparando recorrido turístico..."
        );


        /*
         * Evento: video preparado correctamente.
         */
        videoColombia.setOnPreparedListener(mp -> {

            mediaPlayer = mp;

            videoPreparado = true;

            int duracion = mediaPlayer.getDuration();

            seekVideo.setMax(duracion);
            seekVideo.setProgress(0);

            txtTiempoActual.setText("00:00");

            txtDuracionVideo.setText(
                    formatearTiempo(duracion)
            );

            txtEstadoVideo.setText(
                    "🎥 Recorrido turístico por Colombia"
            );

            btnPlayPause.setEnabled(true);
            btnRetroceder.setEnabled(true);
            btnAdelantar.setEnabled(true);
            seekVideo.setEnabled(true);

            btnPlayCentral.setVisibility(
                    View.VISIBLE
            );

            actualizarProgreso();
        });


        /*
         * Evento: terminó el video.
         */
        videoColombia.setOnCompletionListener(mediaPlayer -> {

            btnPlayPause.setText(
                    "▶ Reproducir"
            );

            btnPlayCentral.setVisibility(
                    View.VISIBLE
            );

            txtEstadoVideo.setText(
                    "🎥 Recorrido turístico por Colombia"
            );

            seekVideo.setProgress(0);

            txtTiempoActual.setText(
                    "00:00"
            );
        });


        /*
         * Evento de error.
         *
         * Esto también evita que Android muestre
         * automáticamente el cuadro:
         *
         * "Can't play this video"
         */
        videoColombia.setOnErrorListener(
                (mediaPlayer, what, extra) -> {

                    videoPreparado = false;

                    btnPlayPause.setEnabled(false);
                    btnRetroceder.setEnabled(false);
                    btnAdelantar.setEnabled(false);
                    seekVideo.setEnabled(false);

                    btnPlayCentral.setVisibility(
                            View.VISIBLE
                    );

                    txtEstadoVideo.setText(
                            "⚠ No fue posible reproducir el video"
                    );

                    Toast.makeText(
                            requireContext(),
                            "Error al reproducir el video",
                            Toast.LENGTH_SHORT
                    ).show();

                    /*
                     * true = nosotros manejamos el error.
                     */
                    return true;
                }
        );


        /*
         * IMPORTANTE:
         * Configuramos los listeners ANTES
         * de asignar el archivo de video.
         */
        String rutaVideo =
                "android.resource://" +
                        requireContext().getPackageName() +
                        "/" +
                        R.raw.colombia_turismo;

        Uri uriVideo =
                Uri.parse(rutaVideo);

        videoColombia.setVideoURI(
                uriVideo
        );
    }


    /*
     * ============================================================
     * EVENTOS DE LOS CONTROLES
     * ============================================================
     */

    private void configurarEventos() {

        btnPlayCentral.setOnClickListener(
                view -> reproducirPausar()
        );

        btnPlayPause.setOnClickListener(
                view -> reproducirPausar()
        );

        btnRetroceder.setOnClickListener(
                view -> retrocederVideo()
        );

        btnAdelantar.setOnClickListener(
                view -> adelantarVideo()
        );

        videoColombia.setOnClickListener(
                view -> reproducirPausar()
        );
    }


    /*
     * ============================================================
     * REPRODUCIR / PAUSAR
     * ============================================================
     */

    private void reproducirPausar() {

        /*
         * No intentar reproducir hasta que
         * el video esté preparado.
         */
        if (!videoPreparado) {

            Toast.makeText(
                    requireContext(),
                    "Espera un momento, el video se está preparando",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        if (videoColombia.isPlaying()) {

            /*
             * PAUSAR
             */
            videoColombia.pause();

            btnPlayPause.setText(
                    "▶ Reproducir"
            );

            btnPlayCentral.setVisibility(
                    View.VISIBLE
            );

            txtEstadoVideo.setText(
                    "⏸ Video en pausa"
            );

        } else {

            /*
             * REPRODUCIR
             */
            videoColombia.start();

            btnPlayPause.setText(
                    "⏸ Pausar"
            );

            btnPlayCentral.setVisibility(
                    View.GONE
            );

            txtEstadoVideo.setText(
                    "🎥 Reproduciendo recorrido por Colombia"
            );

            actualizarProgreso();
        }
    }


    /*
     * ============================================================
     * RETROCEDER 10 SEGUNDOS
     * ============================================================
     */

    private void retrocederVideo() {

        if (!videoPreparado || mediaPlayer == null) {
            return;
        }

        int posicionActual =
                mediaPlayer.getCurrentPosition();

        int nuevaPosicion =
                posicionActual - SALTO_VIDEO;

        if (nuevaPosicion < 0) {
            nuevaPosicion = 0;
        }

        moverVideoA(nuevaPosicion);

        seekVideo.setProgress(nuevaPosicion);

        txtTiempoActual.setText(
                formatearTiempo(nuevaPosicion)
        );
    }


    /*
     * ============================================================
     * ADELANTAR 10 SEGUNDOS
     * ============================================================
     */

    private void adelantarVideo() {

        if (!videoPreparado || mediaPlayer == null) {
            return;
        }

        int posicionActual =
                mediaPlayer.getCurrentPosition();

        int duracion =
                mediaPlayer.getDuration();

        int nuevaPosicion =
                posicionActual + SALTO_VIDEO;

        if (nuevaPosicion > duracion) {
            nuevaPosicion = duracion;
        }

        moverVideoA(nuevaPosicion);

        seekVideo.setProgress(nuevaPosicion);

        txtTiempoActual.setText(
                formatearTiempo(nuevaPosicion)
        );
    }

    private void moverVideoA(int posicion) {

        if (mediaPlayer == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            mediaPlayer.seekTo(
                    posicion,
                    MediaPlayer.SEEK_CLOSEST
            );

        } else {

            mediaPlayer.seekTo(posicion);
        }
    }


    /*
     * ============================================================
     * CONFIGURAR SEEKBAR
     * ============================================================
     */

    private void configurarSeekBar() {

        seekVideo.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(
                            SeekBar seekBar,
                            int progress,
                            boolean fromUser) {

                        if (fromUser) {

                            txtTiempoActual.setText(
                                    formatearTiempo(progress)
                            );
                        }
                    }


                    @Override
                    public void onStartTrackingTouch(
                            SeekBar seekBar) {

                        usuarioMoviendoSeekBar = true;
                    }


                    @Override
                    public void onStopTrackingTouch(
                            SeekBar seekBar) {

                        usuarioMoviendoSeekBar = false;

                        if (videoPreparado) {

                            moverVideoA(
                                    seekBar.getProgress()
                            );
                        }
                    }
                }
        );
    }


    /*
     * ============================================================
     * ACTUALIZAR PROGRESO DEL VIDEO
     * ============================================================
     */

    private final Runnable runnableProgreso =
            new Runnable() {

                @Override
                public void run() {

                    if (
                            videoColombia != null &&
                                    videoPreparado
                    ) {

                        int posicion =
                                videoColombia
                                        .getCurrentPosition();

                        if (!usuarioMoviendoSeekBar) {

                            seekVideo.setProgress(
                                    posicion
                            );

                            txtTiempoActual.setText(
                                    formatearTiempo(
                                            posicion
                                    )
                            );
                        }

                        handler.postDelayed(
                                this,
                                500
                        );
                    }
                }
            };


    private void actualizarProgreso() {

        handler.removeCallbacks(
                runnableProgreso
        );

        handler.post(
                runnableProgreso
        );
    }


    /*
     * ============================================================
     * FORMATEAR MILISEGUNDOS
     * ============================================================
     */

    private String formatearTiempo(
            int milisegundos) {

        int segundosTotales =
                milisegundos / 1000;

        int minutos =
                segundosTotales / 60;

        int segundos =
                segundosTotales % 60;

        return String.format(
                Locale.getDefault(),
                "%02d:%02d",
                minutos,
                segundos
        );
    }


    /*
     * ============================================================
     * PAUSAR CUANDO CAMBIAMOS DE FRAGMENTO
     * ============================================================
     */

    @Override
    public void onPause() {

        super.onPause();

        if (
                videoColombia != null &&
                        videoPreparado &&
                        videoColombia.isPlaying()
        ) {

            videoColombia.pause();

            btnPlayPause.setText(
                    "▶ Reproducir"
            );

            btnPlayCentral.setVisibility(
                    View.VISIBLE
            );

            txtEstadoVideo.setText(
                    "⏸ Video en pausa"
            );
        }
    }


    /*
     * ============================================================
     * LIMPIEZA
     * ============================================================
     */

    @Override
    public void onDestroyView() {

        handler.removeCallbacks(
                runnableProgreso
        );

        videoPreparado = false;

        if (videoColombia != null) {

            videoColombia.stopPlayback();
        }

        super.onDestroyView();
    }
}