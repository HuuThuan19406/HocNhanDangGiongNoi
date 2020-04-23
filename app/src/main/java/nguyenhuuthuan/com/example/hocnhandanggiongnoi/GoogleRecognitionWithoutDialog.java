package nguyenhuuthuan.com.example.hocnhandanggiongnoi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
//import android.speech.RecognitionListener;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import nguyenhuuthuan.com.example.hocnhandanggiongnoi.databinding.ActivityGoogleRecognitionWithoutDialogBinding;

public class GoogleRecognitionWithoutDialog extends AppCompatActivity implements RecognitionListener {

    ActivityGoogleRecognitionWithoutDialogBinding binding;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGoogleRecognitionWithoutDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"vi_VN");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"vi_VN");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);

        addEvents();
    }

    private void addEvents() {
        binding.btnNoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speech.stopListening();
                speech.startListening(recognizerIntent);
            }
        });
        binding.btnThucThi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    xuLyThongTin();
                }
                catch (Exception e){
                }
            }
        });
    }

    private void xuLyThongTin() {
        String st=binding.txtKetQua.getText().toString();
        st=st.toLowerCase();
        String[] keysTatPhanMem=new String[]{"thoát","tắt","đóng","rời khỏi","ngừng"};
        for(String item : keysTatPhanMem) {
            if (st.contains(item))
                finishAndRemoveTask();
        }
        if (st.contains("đỏ")){
            binding.btnThucThi.setBackgroundColor(Color.RED);
            return;
        }
        if (st.contains("vàng")){
            binding.btnThucThi.setBackgroundColor(Color.YELLOW);
            return;
        }
        if (st.contains("lục")){
            binding.btnThucThi.setBackgroundColor(Color.GREEN);
            return;
        }
        if (st.contains("lam")){
            binding.btnThucThi.setBackgroundColor(Color.BLUE);
            return;
        }
        if (st.contains("đen")){
            binding.btnThucThi.setBackgroundColor(Color.BLACK);
            return;
        }
        if (st.contains("trắng")){
            binding.btnThucThi.setBackgroundColor(Color.WHITE);
            return;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }

    }
    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for (String result : matches)
            text += result + "\n";

        binding.txtKetQua.setText(text);
        binding.btnNoi.setText("Nhấn vào đây để nói...");
    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

}
