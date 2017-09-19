package com.chair49.binarykeyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by jacob on 19/09/17.
 */

public class BinaryIME extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView kv;
    private LinearLayout mainView;

    private Keyboard keyboard;
    private TextView preview;

    private boolean caps = false;

    String preAscii = "";

    @Override
    public View onCreateInputView() {
        mainView = (LinearLayout) getLayoutInflater().inflate(R.layout.keyboard, null);

        kv = (KeyboardView) mainView.getChildAt(1);
        keyboard = new Keyboard(this, R.xml.binary);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        preview = (TextView) mainView.getChildAt(0);

        return mainView;
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {

        InputConnection ic = getCurrentInputConnection();
        //playClick(primaryCode);
        switch (primaryCode) {
            case 0:
                preAscii += "0";
                break;
            case 1:
                preAscii += "1";
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            default:
        }
        preview.setText(preAscii);
        if (preAscii.length() == 7) {
            char c = (char) Integer.parseInt(preAscii, 2);
            switch(c) {
                case 8:
                    ic.deleteSurroundingText(1, 0);
                    break;
                default:
                    ic.commitText(String.valueOf(c), 1);
            }
            preAscii = "";
        }
        //ic.commitText(String.valueOf(0),1);
        //ic.deleteSurroundingText(1, 0);
    }

    @Override
    public void onPress(int primaryCode) {
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeRight() {
    }

    @Override
    public void swipeUp() {
    }
}