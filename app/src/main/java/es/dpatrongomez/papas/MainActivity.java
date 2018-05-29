package es.dpatrongomez.papas;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
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
        papas.getSettings().setAllowContentAccess(true);
        papas.getSettings().setAppCacheEnabled(true);
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
       /* papas.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                try {
                    DownloadManager.Request request = new DownloadManager.Request(
                            Uri.parse(url));

                    String cookies = CookieManager.getInstance().getCookie(url);
                    request.addRequestHeader("cookie", cookies);

                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    final String filename = URLUtil.guessFileName(url, contentDisposition, mimetype);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Toast.makeText(getApplicationContext(), "Downloading File",Toast.LENGTH_LONG).show();
                }
                catch(SecurityException e)
                {
                    Toast.makeText(getApplicationContext(),"Please grant the storage permission !",Toast.LENGTH_LONG).show();
                }

            }
        }); */
        /*papas.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                DownloadManager.Request solicitud = new DownloadManager.Request(Uri.parse(url));
                String cookies = CookieManager.getInstance().getCookie(url);
                solicitud.addRequestHeader("cookie", cookies);
                solicitud.allowScanningByMediaScanner();
                solicitud.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                DownloadManager gestor = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                gestor.enqueue(solicitud);

                Toast.makeText(MainActivity.this, "Descargando...",Toast.LENGTH_SHORT).show();
            }
        });*/
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
        }
        return super.onOptionsItemSelected(item);
    }
    private void onForwardPressed() {
        if (papas.canGoForward()) {
            papas.goForward();
        } else {
            Toast.makeText(this, "No puedes ir hacia delante", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (papas.canGoBack()) {
            papas.goBack();
        } else {
            finish();
        }
    }
}
