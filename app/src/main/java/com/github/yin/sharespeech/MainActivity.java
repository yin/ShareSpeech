package com.github.yin.sharespeech;

import android.app.*;
import android.content.*;
import android.os.*;
import android.speech.tts.*;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity
{
    protected TextToSpeech tts;
    protected ArrayList<String> texts = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Intent intent = getIntent();
        String action =intent.getAction();
        String type =intent.getType();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Intent.ACTION_SEND.equals(action) && type != null)
        {
            handleText(intent);
        }
        ListView list = (ListView) findViewById(R.id.speach_list);
        String[] initial = texts.toArray(new String[texts.size()]);
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                initial);
        list.setAdapter(arrayAdapter);
    }

    private void handleText(Intent intent)
    {
        final String text = intent.getStringExtra(Intent.EXTRA_TEXT);
        texts.add(text);

        tts = new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener(){
                    public  void onInit(int status)
                    {
                        if (status != TextToSpeech.ERROR)
                        {
                            tts.setLanguage(Locale.UK);
                        }
                        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                    }

                });
    }
}
