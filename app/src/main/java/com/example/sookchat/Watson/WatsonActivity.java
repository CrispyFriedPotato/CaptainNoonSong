package com.example.sookchat.Watson;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sookchat.R;
import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneHelper;
import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneInputStream;
import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.developer_cloud.assistant.v2.Assistant;
import com.ibm.watson.developer_cloud.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.developer_cloud.assistant.v2.model.MessageInput;
import com.ibm.watson.developer_cloud.assistant.v2.model.MessageOptions;
import com.ibm.watson.developer_cloud.assistant.v2.model.MessageResponse;
import com.ibm.watson.developer_cloud.assistant.v2.model.SessionResponse;
import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;

import java.util.ArrayList;


public class WatsonActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ChatAdapter mAdapter;
    private ArrayList messageArrayList;
    public EditText inputMessage;
    private ImageButton btnSend;
    private ImageButton btnRecord;
    StreamPlayer streamPlayer = new StreamPlayer();
    private boolean initialRequest;
    private boolean permissionToRecordAccepted = false;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String TAG = "MainActivity";
    private static final int RECORD_REQUEST_CODE = 101;
    private boolean listening = false;
    private MicrophoneInputStream capture;
    public static Context mContext;
    private MicrophoneHelper microphoneHelper;

    private Assistant watsonAssistant;
    private SessionResponse watsonAssistantSession;
    private SpeechToText speechService;
    private TextToSpeech textToSpeech;
    public int FLAG=701;
    public String ClickMessage;
    public String imageSource;
    private void createServices() {
        watsonAssistant = new Assistant("2018-11-08", new IamOptions.Builder()
                .apiKey(mContext.getString(R.string.assistant_apikey))
                .build());
        watsonAssistant.setEndPoint(mContext.getString(R.string.assistant_url));

        textToSpeech = new TextToSpeech();
        textToSpeech.setIamCredentials(new IamOptions.Builder()
                .apiKey(mContext.getString(R.string.TTS_apikey))
                .build());
        textToSpeech.setEndPoint(mContext.getString(R.string.TTS_url));

        speechService = new SpeechToText();
        speechService.setIamCredentials(new IamOptions.Builder()
                .apiKey(mContext.getString(R.string.STT_apikey))
                .build());
        speechService.setEndPoint(mContext.getString(R.string.STT_url));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watson);

        mContext=this;

        inputMessage = findViewById(R.id.message);
        btnSend = findViewById(R.id.btn_send);
        btnRecord = findViewById(R.id.btn_record);
        String customFont = "NotoSerif-Regular.ttf";
        Typeface typeface = Typeface.createFromAsset(getAssets(), customFont);
        inputMessage.setTypeface(typeface);
        recyclerView = findViewById(R.id.recycler_view);

        messageArrayList = new ArrayList<>();
        mAdapter = new ChatAdapter(mContext,messageArrayList);
        microphoneHelper = new MicrophoneHelper(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.scrollToPosition(mAdapter.getItemCount());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.canScrollVertically(0);
        this.inputMessage.setText("");
        this.initialRequest = true;


        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied");
            //makeRequest();
        } else {
            Log.i(TAG, "Permission to record was already granted");
        }

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInternetConnection()) {
                    FLAG=800;
                    sendMessage();
                    FLAG=701;
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInternetConnection()) {
                    sendMessage();
                }
            }
        });


        createServices();
        sendMessage();
    }



    // Speech-to-Text Record Audio permission

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                MicrophoneHelper.REQUEST_PERMISSION);
    }

    // Sending a message to Watson Assistant Service
    public void sendMessage() {
        final String inputmessage;
        if(FLAG==700){
            inputmessage=ClickMessage;
            this.inputMessage.setText("");

        }
        else if(FLAG==800){inputmessage="처음으로";
        }
        else {
            inputmessage = this.inputMessage.getText().toString().trim();
        }
        if (!this.initialRequest) {
            Message inputMessage = new Message();
            inputMessage.setMessage(inputmessage);
            inputMessage.setId("1");
            messageArrayList.add(inputMessage);
        }
        else {
            Message inputMessage = new Message();
            inputMessage.setMessage(inputmessage);
            inputMessage.setId("100");
            this.initialRequest = false;
        }
       this.inputMessage.setText("");
        mAdapter.notifyDataSetChanged();

        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    if (watsonAssistantSession == null) {
                        ServiceCall<SessionResponse> call = watsonAssistant.createSession(new CreateSessionOptions.Builder().assistantId(mContext.getString(R.string.assistant_id)).build());
                        watsonAssistantSession = call.execute();
                    }

                    MessageInput input = new MessageInput.Builder()
                            .text(inputmessage)
                            .build();
                    MessageOptions options = new MessageOptions.Builder()
                            .assistantId(mContext.getString(R.string.assistant_id))
                            .input(input)
                            .sessionId(watsonAssistantSession.getSessionId())
                            .build();
                    MessageResponse response = watsonAssistant.message(options).execute();
                    Log.i(TAG, "run: "+response);


                    //get Message from Watson
                    final Message outMessage = new Message();
                    if (response != null &&
                            response.getOutput() != null &&
                            !response.getOutput().getGeneric().isEmpty() ) {

                        if ("text".equals(response.getOutput().getGeneric().get(0).getResponseType())) {

                            outMessage.setMessage(response.getOutput().getGeneric().get(0).getText());
                            outMessage.setId("2");
                            messageArrayList.add(outMessage);


                            if (response != null &&
                                    response.getOutput() != null &&
                                    !response.getOutput().getGeneric().isEmpty() &&
                                    response.getOutput().getGeneric().get(1).getOptions() != null) {

                                Message outMessageOptions;
                                try {
                                    for (int i = 0; response.getOutput().getGeneric().get(1).getOptions().get(i) != null; i++) {
                                        outMessageOptions=new Message();

                                        outMessageOptions.setOptions(response.getOutput().getGeneric().get(1).getOptions().get(i).getLabel());

                                        outMessageOptions.setId("3");
                                        messageArrayList.add(outMessageOptions);

                                    }
                                } catch (IndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                }
                            }

                            else if("image".equals(response.getOutput().getGeneric().get(1).getResponseType())){
                                Message outMessageImages;
                                try{
                                    outMessageImages=new Message();
                                    outMessageImages.setId("4");
                                    imageSource=response.getOutput().getGeneric().get(1).getSource();
                                    messageArrayList.add(outMessageImages);
                                }
                                catch (IndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        else if(response.getOutput().getGeneric().get(0).getOptions().get(0).getValue() != null||response.getOutput().getGeneric().get(1).getOptions().get(0).getValue() != null) {
                            if (response.getOutput().getGeneric().get(0).getOptions().get(0).getValue() != null) {


                                Message outMessageOptions;
                                try {

                                    for (int i = 0; response.getOutput().getGeneric().get(0).getOptions().get(i) != null; i++) {

                                        outMessageOptions = new Message();
                                        outMessageOptions.setOptions(response.getOutput().getGeneric().get(0).getOptions().get(i).getLabel());
                                        outMessageOptions.setId("3");
                                        messageArrayList.add(outMessageOptions);


                                    }
                                } catch (IndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                }

                            }
                            else if (response.getOutput().getGeneric().get(1).getOptions().get(0).getValue() != null) {

                                String optionString;
                                optionString = response.getOutput().getGeneric().get(1).getTitle();
                                Message outMessageOptions;
                                try {
                                    for (int i = 0; response.getOutput().getGeneric().get(1).getOptions().get(i) != null; i++) {
                                        outMessageOptions = new Message();
                                        outMessageOptions.setOptions(response.getOutput().getGeneric().get(1).getOptions().get(i).getLabel());
                                        outMessageOptions.setId("3");
                                        messageArrayList.add(outMessageOptions);
                                    }
                                } catch (IndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                }

                            }

                        }

                        runOnUiThread(new Runnable() {
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                              //  if (mAdapter.getItemCount() >= 0) {
                                    recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, mAdapter.getItemCount()-1);

                                //}

                            }
                        });
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }

    private class SayTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            /**streamPlayer.playStream(textToSpeech.synthesize(new SynthesizeOptions.Builder()
             .text(params[0])
             .voice(SynthesizeOptions.Voice.EN_US_LISAVOICE)
             .accept(SynthesizeOptions.Accept.AUDIO_WAV)
             .build()).execute());**/
            return "Did synthesize";
        }
    }
    //Record a message via Watson Speech to Text
    private void recordMessage() {
        if (listening != true) {
            capture = microphoneHelper.getInputStream(true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                    } catch (Exception e) {

                    }
                }
            }).start();
            listening = true;
            Toast.makeText(WatsonActivity.this, "Listening....Click to Stop", Toast.LENGTH_LONG).show();

        } else {
            try {
                microphoneHelper.closeInputStream();
                listening = false;
                Toast.makeText(WatsonActivity.this, "Stopped Listening....Click to Start", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    /* Check Internet Connection
     *
     * @return*/

    private boolean checkInternetConnection() {
        // get Connectivity Manager object to check connection
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        // Check for network connections
        if (isConnected) {
            return true;
        } else {
            Toast.makeText(this, " No Internet Connection available ", Toast.LENGTH_LONG).show();
            return false;
        }

    }

    //Private Methods - Speech to Text


    //Watson Speech to Text Methods.






}
