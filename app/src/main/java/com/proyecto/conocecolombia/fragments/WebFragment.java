package com.proyecto.conocecolombia.fragments;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.proyecto.conocecolombia.R;

public class WebFragment extends Fragment {

    private TextView txtTituloWeb;
    private TextView txtEstadoWeb;
    private TextView btnRecargarWeb;

    private EditText edtUrl;
    private Button btnIr;

    private ProgressBar progressWeb;
    private WebView webView;


    public WebFragment() {
        // Constructor vacío obligatorio
    }


    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_web,
                container,
                false
        );

        vincularVistas(view);

        configurarWebView();

        configurarEventos();

        return view;
    }


    /*
     * ============================================================
     * VINCULAR VISTAS
     * ============================================================
     */

    private void vincularVistas(View view) {

        txtTituloWeb =
                view.findViewById(R.id.txtTituloWeb);

        txtEstadoWeb =
                view.findViewById(R.id.txtEstadoWeb);

        btnRecargarWeb =
                view.findViewById(R.id.btnRecargarWeb);

        edtUrl =
                view.findViewById(R.id.edtUrl);

        btnIr =
                view.findViewById(R.id.btnIr);

        progressWeb =
                view.findViewById(R.id.progressWeb);

        webView =
                view.findViewById(R.id.webView);
    }


    /*
     * ============================================================
     * CONFIGURACIÓN DEL WEBVIEW
     * ============================================================
     */

    private void configurarWebView() {

        WebSettings settings =
                webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        settings.setLoadWithOverviewMode(false);
        settings.setUseWideViewPort(false);

        settings.setTextZoom(100);

        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);

        webView.setInitialScale(100);


        /*
         * Mantener la navegación dentro de la app.
         */
        webView.setWebViewClient(
                new WebViewClient() {

                    @Override
                    public void onPageStarted(
                            WebView view,
                            String url,
                            Bitmap favicon) {

                        super.onPageStarted(
                                view,
                                url,
                                favicon
                        );

                        progressWeb.setVisibility(
                                View.VISIBLE
                        );

                        txtEstadoWeb.setText(
                                "Cargando sitio..."
                        );

                        edtUrl.setText(url);
                    }


                    @Override
                    public void onPageFinished(
                            WebView view,
                            String url) {

                        super.onPageFinished(
                                view,
                                url
                        );

                        txtEstadoWeb.setText(
                                "Sitio cargado correctamente"
                        );

                        edtUrl.setText(url);
                    }
                }
        );


        /*
         * Barra de progreso.
         */
        webView.setWebChromeClient(
                new WebChromeClient() {

                    @Override
                    public void onProgressChanged(
                            WebView view,
                            int newProgress) {

                        progressWeb.setProgress(
                                newProgress
                        );

                        if (newProgress >= 100) {

                            progressWeb.setVisibility(
                                    View.GONE
                            );

                        } else {

                            progressWeb.setVisibility(
                                    View.VISIBLE
                            );
                        }
                    }
                }
        );
    }


    /*
     * ============================================================
     * EVENTOS
     * ============================================================
     */

    private void configurarEventos() {

        /*
         * Botón IR.
         */
        btnIr.setOnClickListener(
                view -> cargarPagina()
        );


        /*
         * Botón recargar.
         */
        btnRecargarWeb.setOnClickListener(
                view -> {

                    if (webView.getUrl() != null) {

                        webView.reload();

                    } else {

                        Toast.makeText(
                                requireContext(),
                                "Primero carga una página",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );


        /*
         * Botón GO del teclado.
         */
        edtUrl.setOnEditorActionListener(
                (textView, actionId, event) -> {

                    if (
                            actionId ==
                                    EditorInfo.IME_ACTION_GO
                    ) {

                        cargarPagina();

                        return true;
                    }

                    return false;
                }
        );
    }


    /*
     * ============================================================
     * CARGAR PÁGINA
     * ============================================================
     */

    private void cargarPagina() {

        String url =
                edtUrl
                        .getText()
                        .toString()
                        .trim();

        if (url.isEmpty()) {

            Toast.makeText(
                    requireContext(),
                    "Escribe una dirección web",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if (
                !url.startsWith("http://") &&
                        !url.startsWith("https://")
        ) {

            url = "https://" + url;
        }

        // Ocultar teclado
        ocultarTeclado();

        // Quitar foco de la caja URL
        edtUrl.clearFocus();

        // Dar foco al navegador
        webView.requestFocus();

        // Cargar sitio
        webView.loadUrl(url);
    }


    /*
     * ============================================================
     * LIMPIAR WEBVIEW
     * ============================================================
     */

    private void ocultarTeclado() {

        View view = requireActivity().getCurrentFocus();

        if (view != null) {

            InputMethodManager inputMethodManager =
                    (InputMethodManager) requireContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);

            inputMethodManager.hideSoftInputFromWindow(
                    view.getWindowToken(),
                    0
            );

            view.clearFocus();
        }
    }

    @Override
    public void onDestroyView() {

        if (webView != null) {

            webView.stopLoading();

            webView.setWebChromeClient(null);

            webView.setWebViewClient(null);

            webView.destroy();
        }

        super.onDestroyView();
    }
}

