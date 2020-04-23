package nguyenhuuthuan.com.example.hocnhandanggiongnoi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import nguyenhuuthuan.com.example.hocnhandanggiongnoi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    HashMap<String,String>dictionary=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeDictionary();
        addEvents();
    }

    private void makeDictionary() {
        dictionary.put("School","Trường học");
        dictionary.put("College","Trường Cao đẳng");
        dictionary.put("University","Trường Đại học");
        dictionary.put("Beautiful","Đẹp");
        dictionary.put("Light","Đèn, ánh sáng");
        dictionary.put("Night","Buổi tối");
        dictionary.put("Wife","Vợ");
    }

    private void addEvents() {
        binding.btnGiongNoi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGiongNoiGoogleAI();
            }
        });
        binding.btnTaoTiengAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rd=new Random();
                int position=rd.nextInt(dictionary.size());
                String word= (String) dictionary.keySet().toArray()[position];
                binding.txtEnglish.setText(word);
            }
        });
        binding.btnGiongNoi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,GoogleRecognitionWithoutDialog.class);
                startActivity(intent);
            }
        });
    }

    private void xuLyGiongNoiGoogleAI() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"vi_VN");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi_VN");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Please talk somethings");
        try {
            startActivityForResult(intent, 113);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "This phone is not support Google AI",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==113 && requestCode==RESULT_OK && data!=null){
            ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            binding.txtSpeechToText.setText(result.get(0));
        }
    }
}
