package com.lewandowskifabian.mvvm_rpi_datagrabber.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.lewandowskifabian.mvvm_rpi_datagrabber.R;
import com.lewandowskifabian.mvvm_rpi_datagrabber.model.LedDisplayModel;
import com.lewandowskifabian.mvvm_rpi_datagrabber.model.LedModel;
import com.lewandowskifabian.mvvm_rpi_datagrabber.model.ServerIoT;

public class LedDisplayActivity extends AppCompatActivity {

    private LedDisplayModel displayModel;
    private LedModel preview;
    private final int nullColor = 0x00000000;
    private ServerIoT serverIoT;

    private View colorView;
    private EditText urlText;
    private Button sendBtn, clearBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led_display);

        serverIoT = new ServerIoT("192.168.33.5", this);
        displayModel = new LedDisplayModel();
        preview = new LedModel();

        TableLayout ledTable = (TableLayout) findViewById(R.id.led_table);
        for(int y = 0; y < displayModel.ledY; y++) {
            //
            TableRow ledRow = new TableRow(this);
            ledRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            for (int x = 0; x < displayModel.ledX; x++)
                ledRow.addView(addLedIndicatorToTableLayout(x, y));
            ledTable.addView(ledRow);
        }

        colorView = findViewById(R.id.colorView);

        urlText = findViewById(R.id.urlText);
        urlText.setText(serverIoT.getLedScriptUrl());

        sendBtn = (Button)findViewById(R.id.sendBtn);
        clearBtn = (Button)findViewById(R.id.clearBtn);

        SeekBar redSeekBar = (SeekBar) findViewById(R.id.seekBarR);
        redSeekBar.setMax(255);
        redSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }
            public void onStartTrackingTouch(SeekBar seekBar) {/* Auto-generated method stub */ }
            public void onStopTrackingTouch(SeekBar seekBar) {
                preview.R = progressChangedValue;
                setLedViewColor(colorView, preview.getColor());
            }
        });

        SeekBar greenSeekBar = (SeekBar) findViewById(R.id.seekBarG);
        greenSeekBar.setMax(255);
        greenSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }
            public void onStartTrackingTouch(SeekBar seekBar) {/* Auto-generated method stub */ }
            public void onStopTrackingTouch(SeekBar seekBar) {
                preview.G = progressChangedValue;
                setLedViewColor(colorView, preview.getColor());
            }
        });

        SeekBar blueSeekBar = (SeekBar) findViewById(R.id.seekBarB);
        blueSeekBar.setMax(255);
        blueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }
            public void onStartTrackingTouch(SeekBar seekBar) {/* Auto-generated method stub */ }
            public void onStopTrackingTouch(SeekBar seekBar) {
                preview.B = progressChangedValue;
                setLedViewColor(colorView, preview.getColor());
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverIoT.putControlRequest(displayModel.getControlJsonArray());
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableLayout tb = (TableLayout)findViewById(R.id.led_table);
                View ledInd;
                for(int i = 0; i < displayModel.ledX; i++) {
                    for (int j = 0; j < displayModel.ledY; j++) {
                        ledInd = tb.findViewWithTag(ledIndexToTag(i, j));
                        setLedViewColor(ledInd, nullColor);
                    }
                }
            }
        });
    }

    private RelativeLayout addLedIndicatorToTableLayout(int x, int y)
    {
        // Border: RelativeView with border background drawable element
        RelativeLayout border = new RelativeLayout(this);
        // Layout parameters
        int ledIndWidth = (int)getResources().getDimension(R.dimen.ledIndWidth);
        int ledIndHeight = (int)getResources().getDimension(R.dimen.ledIndHeight);
        TableRow.LayoutParams border_params =
                new TableRow.LayoutParams(ledIndWidth, ledIndHeight);
        int borderThickness_px = (int)getResources().getDimension(R.dimen.borderThickness);
        border_params.setMargins(borderThickness_px, borderThickness_px,
                borderThickness_px, borderThickness_px);
        border_params.gravity = Gravity.CENTER;
        border_params.weight = 1;
        border.setLayoutParams(border_params);
        // Background drawable element
        border.setBackground(getResources().getDrawable(R.drawable.led_border));

        // LED indicator: View with LED background color
        View led = new View(this);
        // Layout parameters
        RelativeLayout.LayoutParams led_params =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT );
        int ledIndMarginTopBottom_px = (int)getResources().getDimension(R.dimen.ledIndMarginTopBottom);
        int ledIndMarginLeftRight_px = (int)getResources().getDimension(R.dimen.ledIndMarginLeftRight);
        led_params.setMargins(ledIndMarginLeftRight_px, ledIndMarginTopBottom_px,
                ledIndMarginLeftRight_px, ledIndMarginTopBottom_px);
        led.setLayoutParams(led_params);
        // Background color
        led.setBackground(getResources().getDrawable(R.drawable.led_view));

        // OnClick method listener
        led.setOnClickListener(led_onClick);
        // Element tag
        led.setTag(ledIndexToTag(x, y));

        // Add 'led' View to border RelativeLayout
        border.addView(led);

        return border;
    }

    private final View.OnClickListener led_onClick = new View.OnClickListener() {
        /**
         * @brief LED indicator onClick event handling procedure
         * @param v LED indicator View element
         */
        @Override
        public void onClick(View v) {
            // Set active color as background
            setLedViewColor(v, preview.getColor());
            // Find element x-y position
            int[] pos = ledTagToIndex((String)v.getTag());
            // Update LED display data model
            displayModel.setLedModel(pos[0],pos[1], preview);
        }
    };

    private void setLedViewColor(View v, int color){
        Drawable backgroundColor = v.getBackground();
        if (backgroundColor instanceof ShapeDrawable) {
            ((ShapeDrawable)backgroundColor).getPaint().setColor(color);
        } else if (backgroundColor instanceof GradientDrawable) {
            ((GradientDrawable)backgroundColor).setColor(color);
        } else if (backgroundColor instanceof ColorDrawable) {
            ((ColorDrawable)backgroundColor).setColor(color);
        }
    }

    private int[] ledTagToIndex(String tag) {
        // Tag: 'LEDxy"
        return new int[]{
                Character.getNumericValue(tag.charAt(3)),
                Character.getNumericValue(tag.charAt(4))
        };
    }

    private String ledIndexToTag(int x, int y) {
        return "LED" + Integer.toString(x) + Integer.toString(y);
    }
}