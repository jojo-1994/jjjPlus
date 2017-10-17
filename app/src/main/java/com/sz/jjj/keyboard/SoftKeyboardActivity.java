package com.sz.jjj.keyboard;

import android.inputmethodservice.Keyboard;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sz.jjj.R;

/**
 * Created by jjj on 2017/10/11.
 *
 * @description:
 */

public class SoftKeyboardActivity extends AppCompatActivity {

    private Button btn1, btn2, btn3;
    private EditText test1, test2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        final AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                playClick(32);

            }
        });

        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playClick(10);
            }
        });

        btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playClick(Keyboard.KEYCODE_DELETE);
            }
        });

        test1 = (EditText) findViewById(R.id.test1);
        test2 = (EditText) findViewById(R.id.test2);

        new KeyboardUtil(SoftKeyboardActivity.this, test1, test2);

    }

    private void playClick(int keyCode) {
        AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        switch (keyCode) {
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

}
