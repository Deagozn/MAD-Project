package com.example.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.InputQueue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QRCode extends AppCompatActivity {

    private ImageView QRCode;

    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);

        String username = getIntent().getStringExtra("username");
        Log.d("debug", username);
        String id = getIntent().getStringExtra("id");
        Log.d("debug", id);

        QRCode = findViewById(R.id.idIVQrcode);

        int size = 500;

        String data;
        data = username + "," + id;

        if (!data.isEmpty()) {
            Bitmap bitmap = null;
            try {
                bitmap = encodeAsBitmap(data, BarcodeFormat.QR_CODE, size, size);
            } catch (WriterException e) {
                throw new RuntimeException(e);
            }
            QRCode.setImageBitmap(bitmap);
        }


        back = findViewById(R.id.back_qrcode);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QRCode.this, Dashboard.class);
                startActivity(intent);
            }
        });
    }

    private Bitmap encodeAsBitmap(String data, BarcodeFormat format, int width, int height) throws WriterException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(data, format, width, height);
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}