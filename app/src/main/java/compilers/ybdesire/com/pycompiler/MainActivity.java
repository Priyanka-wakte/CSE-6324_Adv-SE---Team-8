package compilers.ybdesire.com.pycompiler;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;

    private void setText(final TextView text,final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Admob
        MobileAds.initialize(this, "ca-app-pub-8100413825150401~5715492852");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        //Edit Text
        final AppCompatEditText editText = (AppCompatEditText) findViewById(R.id.text_input_code);

        //Buttons
        Button btn=findViewById(R.id.button_tab);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText.getText().insert(editText.getSelectionStart(), "    ");
            }
        });
        btn=findViewById(R.id.button_println);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText.getText().insert(editText.getSelectionStart(), "print(  )");
            }
        });
        btn=findViewById(R.id.button_quote);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText.getText().insert(editText.getSelectionStart(), "\"");
            }
        });
        btn=findViewById(R.id.button_semi);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText.getText().insert(editText.getSelectionStart(), ";");
            }
        });
        // compile
        btn=findViewById(R.id.button_compile);
        // On clicking on run button in the key word it calls the below method
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {

                        try  {

                            // creating okhttp client request
                            OkHttpClient client = new OkHttpClient();
                            /* Adding Key Value Pairs for the request body*/
                            JsonObject postData = new JsonObject();
                            postData.addProperty("LanguageChoice", "5");
                            postData.addProperty("Program", editText.getText().toString());
                            postData.addProperty("Input", "enter_the_input_to_your_code_here");

                            /*Crafting our okhttp post request*/
                            final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                            RequestBody body = RequestBody.create(mediaType, postData.toString());
                            Request request = new Request.Builder()
                                    .url("https://code-compiler.p.rapidapi.com/v2")
                                    .post(body)
                                    .addHeader("content-type", "application/json")
                                    .addHeader("x-rapidapi-host", "code-compiler.p.rapidapi.com")
                                    .addHeader("x-rapidapi-key", "fb06f9962amshae3d838c4414c9cp111592jsn6fc575d99b64")
                                    .build();




                            //Getting response from the api and integrating it to UI elemnets
                            String code = editText.getText().toString();    
                            try {
                                Response response = client.newCall(request).execute();
                                String responseBody = response.body().string();
                                Log.i("myapp", "response body1"+responseBody);

                                Log.d("myapp", "works till here. 2");

                                TextView txtOutput=findViewById(R.id.txt_output);//find output label by id

                                JSONObject jdata = new JSONObject(responseBody);
                                Log.d("myapp", "jdata " + jdata);

                                if( (jdata.getString("Errors")).equals("null") )
                                {
                                    setText(txtOutput,  jdata.get("Result").toString());
                                }
                                else
                                {
                                    setText(txtOutput,jdata.get("Errors").toString());
                                }


                                Log.d("myapp", "response " + responseBody);
                                Log.d("myapp", "errors " + jdata.get("Errors"));
                                Log.d("myapp", "output " + jdata.get("Result"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                TextView txtOutput=findViewById(R.id.txt_output);//find output label by id
                                setText(txtOutput,getString(R.string.err_network));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            TextView txtOutput=findViewById(R.id.txt_output);//find output label by id
                            txtOutput.setText(getString(R.string.err_network));
                        }
                    }
                });

                thread.start();
                //disable button and modify color
                Button btnc=findViewById(R.id.button_compile);
                btnc.setClickable(false);
                btnc.setBackgroundColor(Color.GRAY);

                //timer for 5s delay and enable button
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        Button btncc=findViewById(R.id.button_compile);
                        btncc.setClickable(true);
                        btncc.setBackgroundResource(android.R.drawable.btn_default);
                    }
                }, 5000);


            }
        });

        //init
        String str = "import os\n\nprint('hello world')\n";
        SpannableString ss = CodeEditText.setHighLight(str);
        editText.setText(ss);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int start, int count, int after) {
                //Log.d("onTextChanged", "onTextChanged,str_len="+cs.toString().length());
                //Log.d("onTextChanged", "start="+start);
                //Log.d("onTextChanged", "str="+cs.toString().substring(start,start+1));
                //Log.d("onTextChanged", "count="+count);
                //Log.d("onTextChanged", "after"+after);

                //if(cs.toString().substring(start,start+1).equals(" "))
                //{
                //Log.d("onTextChanged", "get space");
                    /*
                    SpannableString ss = new SpannableString(cs.toString());
                    String textToSearch = "public";
                    Pattern pattern = Pattern.compile(textToSearch);
                    Matcher matcher = pattern.matcher(ss);
                    while (matcher.find()) {
                        ss.setSpan(new ForegroundColorSpan(Color.RED), matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    edittext.setText(ss);
                    */
                //}

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                //Log.d("DBG", "beforeTextChanged");
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Log.d("DBG", "afterTextChanged");
                editText.removeTextChangedListener(this);
                String str = editText.getText().toString();

                int po = editText.getSelectionStart();//get cursor
                SpannableString ss = CodeEditText.setHighLight(str);
                editText.setText(ss);

                editText.setSelection(po);//set cursor
                editText.addTextChangedListener(this);

            }

        });


    }
}
