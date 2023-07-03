package com.example.project;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.InputQueue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QRCode extends AppCompatActivity {

    private ImageView QRCode;

    private EditText dataInput;

    private Button generateQrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);

        QRCode = findViewById(R.id.idIVQrcode);
        dataInput = findViewById(R.id.idEdt);
        generateQrCode = findViewById(R.id.idBtnGenerateQR);
        int size = 500;

        generateQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data;
                data = dataInput.getText().toString();

                if (!data.isEmpty()) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = encodeAsBitmap(data, BarcodeFormat.QR_CODE, size, size);
                    } catch (WriterException e) {
                        throw new RuntimeException(e);
                    }
                    QRCode.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(getApplicationContext(), "Data cannot be empty", Toast.LENGTH_SHORT).show();
                }
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