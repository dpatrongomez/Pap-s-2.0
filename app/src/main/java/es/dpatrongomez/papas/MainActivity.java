package es.dpatrongomez.papas;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ForwardingListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    ProgressBar carga;
    WebView papas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carga = findViewById(R.id.carga);
        papas = findViewById(R.id.papas);

        carga.setMax(100);

        papas.loadUrl("https://papas.jccm.es/");
        papas.getSettings().setJavaScriptEnabled(true);
        papas.getSettings().setSupportZoom(true);
        papas.getSettings().setUseWideViewPort(true);
        papas.getSettings().setAllowContentAccess(true);
        papas.getSettings().setAppCacheEnabled(true);
        papas.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        papas.getSettings().setDomStorageEnabled(true);
        papas.getSettings().setAllowFileAccess(true);
        papas.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        papas.setWebViewClient(new WebViewClient());
        papas.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                carga.setProgress(newProgress);
                if (newProgress < 100) {
                    carga.setVisibility(ProgressBar.VISIBLE);
                } else {
                    carga.setVisibility(ProgressBar.GONE);
                }
            }

        });
        papas.setDownloadListener(new DownloadListener() {

            @Override


            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimeType,
                                        long contentLength) {

                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(url));
                request.setMimeType(mimeType);
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription(getString(R.string.descargando));
                request.setTitle(URLUtil.guessFileName(url, contentDisposition,
                        mimeType));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalFilesDir(MainActivity.this,
                        Environment.DIRECTORY_DOWNLOADS, ".pdf");
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                if (dm != null) {
                    dm.enqueue(request);
                }
                Toast.makeText(getApplicationContext(), R.string.descargando,
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_anterior:
                onBackPressed();
                break;
            case R.id.menu_siguiente:
                onForwardPressed();
                break;
            case R.id.info:
                Intent intent = new Intent(this, Info.class);
                startActivity(intent);
                break;
            case R.id.donacion:
                Uri paypal = Uri.parse("https://paypal.me/dpatrongomez");
                Intent donacion = new Intent(Intent.ACTION_VIEW, paypal);
                startActivity(donacion);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void onForwardPressed() {
        if (papas.canGoForward()) {
            papas.goForward();
        } else {
            Toast.makeText(this, R.string.erroravanzar, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (papas.canGoBack()) {
            papas.goBack();
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("¿Quieres salir de la aplicación");
            builder.setCancelable(true);
            builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
    }
}
