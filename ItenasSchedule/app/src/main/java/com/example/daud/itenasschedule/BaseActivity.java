package com.example.daud.itenasschedule;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daud.itenasschedule.R;
import com.example.daud.itenasschedule.client.APIService;
import com.example.daud.itenasschedule.client.response.GetNamaResponse;
import com.example.daud.itenasschedule.client.response.RegisterResponse;
import com.example.daud.itenasschedule.utils.Constant;
import com.example.daud.itenasschedule.utils.Util;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okio.Buffer;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class BaseActivity extends AppCompatActivity {
    private WebView view;
    Timer timer;
    ProgressDialog pDialog;
    EditText input,inputnama;
    String nama;

    private APIService service;

    public APIService getService() {
        return service;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(5, TimeUnit.MINUTES);
        client.setReadTimeout(5, TimeUnit.MINUTES);
        client.interceptors().add(new LoggingInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(APIService.class);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

//        if(!Util.haveLogged(getApplicationContext())){
//            Intent i = new Intent(Login.this, BayarToko.class);
//            i.putExtra("from","login");
//            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(i);
//

            verifikasinama();
//        }

        view = (WebView) this.findViewById(R.id.webView);
//        view = (WebView) this.findViewById(R.id.)
        view.getSettings().setJavaScriptEnabled(true);
        view.setWebViewClient(new MyBrowser());
        view.loadUrl("http://kpta.itenas.ac.id/"); //try js alert
        view.setWebChromeClient(new WebChromeClient()); // adding js alert support



    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //ketika disentuh tombol back
        if ((keyCode == KeyEvent.KEYCODE_BACK) && view.canGoBack()) {
            view.goBack(); //method goback() dieksekusi untuk kembali pada halaman sebelumnya
            return true;
        }
        // Jika tidak ada history (Halaman yang sebelumnya dibuka)
        // maka akan keluar dari activity
        return super.onKeyDown(keyCode, event);
    }


    public void ceklogin(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Apakah Nama Anda ?");

        inputnama = new EditText(this);

        inputnama.setEnabled(false);
        builder.setView(inputnama);
        inputnama.setText("      "+nama);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pDialog = new ProgressDialog(BaseActivity.this);

                pDialog.setMessage("Registrasi Perangkat. . ");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();

                runningCall();


            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void verifikasinama(){


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Masukan NIP Anda ?");

        FirebaseMessaging.getInstance().subscribeToTopic("TA");

        input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pDialog = new ProgressDialog(BaseActivity.this);

                pDialog.setMessage("Proses Harap Tunggu. . ");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();

                callToken();
                runningCallnama();


            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    private void callToken() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        Log.w("Kesini", resultCode+"");
        //if play service is not available
        if(ConnectionResult.SUCCESS != resultCode) {
            //If play service is supported but not installed
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                //Displaying message that play service is not installed
                Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_LONG).show();
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());

                //If play service is not supported
                //Displaying an error message
            } else {
                Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service!", Toast.LENGTH_LONG).show();
            }

            //If play service is available
        } else {
            String token = FirebaseInstanceId.getInstance().getToken();

            Log.e("token", FirebaseInstanceId.getInstance().getToken()+"");
            Util.saveString(getApplicationContext(), Constant.SharedPref.TOKEN, token);
        }
    }

    private void runningCall(){


        timer = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                if(Util.getGcmToken(getApplicationContext())!=null){
                    Log.e("SINI","UDAH BISA REGISTER"+Util.getGcmToken(getApplicationContext()));
                    sendToLocal();
                }
            }
        };

        timer.schedule(tt, 0, 1000 * 2);
    }

    private void sendToLocal(){
        Call<RegisterResponse> call;
        call = getService().registermember(input.getText().toString(),Util.getGcmToken(getApplicationContext()));

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Response<RegisterResponse> response, Retrofit retrofit) {
                if(response.isSuccess()) {
                    Log.e("response sukses",response.toString());
                    if(response.body().getStatus().equals("OK")) {

                        timer.cancel();
                        pDialog.dismiss();
                        Util.saveBoolean(getApplicationContext(), Constant.SharedPref.LOGGED,true);

//                        Intent i = new Intent(Login.this, BayarToko.class);
//                        i.putExtra("from","login");
//                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(i);
                        Toast.makeText(getApplicationContext(), "Sistem Terintegrasi !", Toast.LENGTH_LONG).show();
                        newregis();

                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("response",t.toString());
            }
        });
    }

    private void runningCallnama(){


        timer = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                if(Util.getGcmToken(getApplicationContext())!=null){
                    Log.e("SINI","Mendapatkan Nama : "+Util.getGcmToken(getApplicationContext()));
                    sendToLocalnama();
                }
            }
        };

        timer.schedule(tt, 0, 1000 * 2);




    }


    private void sendToLocalnama(){
        Call<GetNamaResponse> call;
        call = getService().requestNama(input.getText().toString());

        call.enqueue(new Callback<GetNamaResponse>() {
            @Override
            public void onResponse(Response<GetNamaResponse> response, Retrofit retrofit) {
                //   Toast.makeText(getApplicationContext(), "N:  !" + response.body().getNama(), Toast.LENGTH_LONG).show();
                if(response.isSuccess()) {
                    Log.e("response sukses",response.toString());
                    //    if(!response.body().getStatus().equals("")) {

                    timer.cancel();
                    pDialog.dismiss();
                    //           Util.saveBoolean(getApplicationContext(), Constant.SharedPref.LOGGED,true);

//                        Intent i = new Intent(Login.this, BayarToko.class);
//                        i.putExtra("from","login");
//                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(i);
                    //      Toast.makeText(getApplicationContext(), "N:  !" + response.body().getNama(), Toast.LENGTH_LONG).show();
                    nama= response.body().getNama();
                    ceklogin();
                    //   }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("response",t.toString());
            }
        });
    }






    public static class LoggingInterceptor implements Interceptor {
        @Override
        public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            String requestLog = String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers());
//            Log.d(String.format("Sending request %s on %s%n%s",
//                    request.url(), chain.connection(), request.headers()));
            Log.d("TAG LOG",requestLog+request.toString());
            if (request.method().compareToIgnoreCase("post") == 0) {
                requestLog = "\n" + requestLog + "\n" + bodyToString(request);
            }
            Log.d("TAG", "request" + "\n" + requestLog);

            com.squareup.okhttp.Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            String responseLog = String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers());

            String bodyString = response.body().string();
            Log.d("TAG", "response" + "\n" + responseLog + "\n" + bodyString);

            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), bodyString))
                    .build();
            //return response;
        }
    }
    public static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    public void newregis(){
        Call<RegisterResponse> call;
        call = getService().requestOTP(input.getText().toString(),nama);
        Toast.makeText(getApplicationContext(), "NID SEbelumnya : "+input.getText().toString()+" Nama : "+nama, Toast.LENGTH_LONG).show();
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Response<RegisterResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if(response.body().getStatus().equals("sent")){
                        Toast.makeText(getApplicationContext(), "Selamat Datang Pengguna Baru !", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("throw",t.toString());
            }
        });
    }

}
