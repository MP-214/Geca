package com.example.lenovo.geca;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.bassaer.chatmessageview.model.Message;
import com.github.bassaer.chatmessageview.view.ChatView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.api.AIServiceException;
import ai.api.RequestExtras;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.android.GsonFactory;
import ai.api.model.AIContext;
import ai.api.model.AIError;
import ai.api.model.AIEvent;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Metadata;
import ai.api.model.Result;
import ai.api.model.Status;

public class Chatbot extends Fragment implements View.OnClickListener {
    View my_v;
    public static final String TAG = MainActivity.class.getName();
    private Gson gson = GsonFactory.getGson();
    private AIDataService aiDataService;
    private ChatView chatView;
    private User myAccount;
    private User droidKaigiBot;
    String type = "", marks = "", category = "", caste = "", b = " ";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v= inflater.inflate(R.layout.chatbot, container, false);
        initChatView();
        //Language, Dialogflow Client access token
        final LanguageConfig config = new LanguageConfig("en", "678707e107ae4a06afaa7d9e588773bd");
        initService(config);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return my_v;
    }
    @Override
    public void onClick(View v) {
        //new message
        final Message message = new Message.Builder()
                .setUser(myAccount)
                .setRightMessage(true)
                .setMessageText(chatView.getInputText())
                .hideIcon(true)
                .build();
        //Set to chat view
        chatView.send(message);
        sendRequest(chatView.getInputText());
        //Reset edit text
        chatView.setInputText("");
    }
    private void sendRequest(String text) {
        Log.d(TAG, text);
        final String queryString = String.valueOf(text);
        final String eventString = null;
        final String contextString = null;
        if (TextUtils.isEmpty(queryString) && TextUtils.isEmpty(eventString)) {
            onError(new AIError(getString(R.string.non_empty_query)));
            return;
        }
        new AiTask().execute(queryString, eventString, contextString);
    }
    public class AiTask extends AsyncTask<String, Void, AIResponse> {
        private AIError aiError;
        @Override
        protected AIResponse doInBackground(final String... params) {
            final AIRequest request = new AIRequest();
            String query = params[0];
            String event = params[1];
            String context = params[2];

            if (!TextUtils.isEmpty(query)) {
                request.setQuery(query);
            }

            if (!TextUtils.isEmpty(event)) {
                request.setEvent(new AIEvent(event));
            }

            RequestExtras requestExtras = null;
            if (!TextUtils.isEmpty(context)) {
                final List<AIContext> contexts = Collections.singletonList(new AIContext(context));
                requestExtras = new RequestExtras(contexts, null);
            }

            try {
                return aiDataService.request(request, requestExtras);
            } catch (final AIServiceException e) {
                aiError = new AIError(e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(final AIResponse response) {
            if (response != null) {
                onResult(response);
            } else {
                onError(aiError);
            }
        }
    }

    private void onResult(final AIResponse response) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Variables
                gson.toJson(response);
                final Status status = response.getStatus();
                final Result result = response.getResult();
                final String speech = result.getFulfillment().getSpeech();
                final Metadata metadata = result.getMetadata();

                final HashMap<String, JsonElement> params = result.getParameters();

                // Logging
                Log.d(TAG, "onResult");
                Log.i(TAG, "Received success response");
                Log.i(TAG, "Status code: " + status.getCode());
                Log.i(TAG, "Status type: " + status.getErrorType());
                Log.i(TAG, "Resolved query: " + result.getResolvedQuery());
                Log.i(TAG, "Action: " + result.getAction());
                Log.i(TAG, "Speech: " + speech);

                if (metadata != null) {
                    Log.i(TAG, "Intent id: " + metadata.getIntentId());
                    Log.i(TAG, "Intent name: " + metadata.getIntentName());   //WEIGHT
                }

                if (params != null && !params.isEmpty()) {
                    Log.i(TAG, "Parameters: ");
                    for (final Map.Entry<String, JsonElement> entry : params.entrySet()) {
                        Log.i(TAG, String.format("%s: %s",
                                entry.getKey(), entry.getValue().toString()));

                        if (entry.getKey().equals("number")) {
                            type = entry.getValue().toString();
                            String num_final = "";
                            char[] chArr = type.toCharArray();
                            for (int i = 0; i < chArr.length; i++) {
                                if (chArr[i] == '"') {

                                } else {
                                    num_final = num_final + "" + chArr[i];
                                }
                            }
                            type = num_final;
                            Toast.makeText(my_v.getContext(), "Marks= " + type, Toast.LENGTH_SHORT).show();

                        }
                        if (entry.getKey().equals("caste")) {
                            category = entry.getValue().toString();
                            String num_final = "";
                            char[] chArr = category.toCharArray();
                            for (int i = 0; i < chArr.length; i++) {
                                if (chArr[i] == '"') {

                                } else {
                                    num_final = num_final + "" + chArr[i];
                                }
                            }
                            category = num_final;
                            Toast.makeText(my_v.getContext(), "Caste= " + category, Toast.LENGTH_SHORT).show();
                        }
                        if (entry.getKey().equals("br")) {
                            b = entry.getValue().toString().trim();
                            String num_final = " ";
                            Log.d("B value", b);
                            char[] chArr = b.toCharArray();
                            for (int i = 0; i < chArr.length; i++) {
                                if (chArr[i] == '"') {

                                } else {
                                    num_final = num_final + "" + chArr[i];
                                }
                            }

                            b = num_final;
                            Toast.makeText(my_v.getContext(), "branch= " + b, Toast.LENGTH_SHORT).show();


                            Log.d("B value", b);
                            Log.d("Checking....", "" + b.trim().equals("no"));
                            Toast.makeText(my_v.getContext(), "marks=" + type + "Caste= " + category + "Branch= " + b, Toast.LENGTH_SHORT).show();
                           // if (b.trim().equals("no")) {
                                Log.d("In IF........", b);
                                Toast.makeText(my_v.getContext(), "Marks= " + type + "Caste= " + category + "Branch= " + b, Toast.LENGTH_SHORT).show();
                                Toast.makeText(my_v.getContext(), "List of Colleges are", Toast.LENGTH_SHORT).show();
                                try {
                                    Toast.makeText(my_v.getContext(), "http://192.168.43.171:5000/result1?marks=" + type + "&caste=" + category + "&branch=" + b, Toast.LENGTH_SHORT).show();
                                    URL url = new URL("http://192.168.43.171:5000/result1?marks="+ type + "&caste="+ category + "&branch="+ b);           // URL url=new URL("http://localhost:5000/");
                                    URLConnection con;
                                    con = url.openConnection();
                                    InputStream in = con.getInputStream();
                                    int x;
                                    StringBuffer sb = new StringBuffer();
                                    Toast.makeText(my_v.getContext(), "2", Toast.LENGTH_SHORT).show();
                                    do {
                                        x = in.read();
                                        sb.append((char) x);
                                    } while (x != -1);
                                    in.close();
                                    Toast.makeText(my_v.getContext(), "" + sb.toString(), Toast.LENGTH_SHORT).show();
                                    final Message receivedMessage = new Message.Builder()
                                            .setUser(droidKaigiBot)
                                            .setRightMessage(false)
                                            .setMessageText(sb.toString())
                                            .build();
                                    chatView.receive(receivedMessage);
                                } catch (Exception e) {
                                    Toast.makeText(my_v.getContext(), "" + e, Toast.LENGTH_SHORT).show();
                                }

                           // }
                        }


                    }
                }

                //Update view to bot says
                final Message receivedMessage = new Message.Builder()
                        .setUser(droidKaigiBot)
                        .setRightMessage(false)
                        .setMessageText(speech)
                        .build();
                chatView.receive(receivedMessage);

            }
        });
    }

    private void onError(final AIError error) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, error.toString());
            }
        });
    }

    private void initChatView() {
        int myId = 0;
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_user);
        String myName = "You";
        myAccount = new User(myId, myName, icon);

        int botId = 1;
        String botName = "Bob";
        droidKaigiBot = new User(botId, botName, icon);

        chatView = (ChatView)my_v.findViewById(R.id.chat_view);
        chatView.setRightBubbleColor(ContextCompat.getColor(my_v.getContext(), R.color.green500));
        chatView.setLeftBubbleColor(Color.WHITE);
        chatView.setBackgroundColor(ContextCompat.getColor(my_v.getContext(), R.color.blueGray500));
        chatView.setSendButtonColor(ContextCompat.getColor(my_v.getContext(), R.color.lightBlue500));
        chatView.setSendIcon(R.drawable.ic_action_send);
        chatView.setRightMessageTextColor(Color.WHITE);
        chatView.setLeftMessageTextColor(Color.BLACK);
        chatView.setUsernameTextColor(Color.WHITE);
        chatView.setSendTimeTextColor(Color.WHITE);
        chatView.setDateSeparatorColor(Color.WHITE);
        chatView.setInputTextHint("new message...");
        chatView.setMessageMarginTop(5);
        chatView.setMessageMarginBottom(5);
        chatView.setOnClickSendButtonListener(this);
    }

    private void initService(final LanguageConfig languageConfig) {
        final AIConfiguration.SupportedLanguages lang =
                AIConfiguration.SupportedLanguages.fromLanguageTag(languageConfig.getLanguageCode());
        final AIConfiguration config = new AIConfiguration(languageConfig.getAccessToken(), lang,AIConfiguration.RecognitionEngine.System);
        aiDataService = new AIDataService(my_v.getContext(), config);
    }
}