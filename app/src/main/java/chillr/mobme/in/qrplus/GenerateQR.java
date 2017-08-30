package chillr.mobme.in.qrplus;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GenerateQR extends AppCompatActivity {
    private Button button1,button2;
    public final static int QRcodeWidth = 500 ;
    private Bitmap bitmap ;
    private ImageView qrImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_qr);
        button2=(Button)findViewById(R.id.tv2);
        qrImage=(ImageView) findViewById(R.id.qr_image);

        button1=(Button)findViewById(R.id.tv1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            createJson("John","Delhi","http://www.jrtstudio.com/sites/default/files/ico_android.png");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createJson("rahul","Bangalore","http://www.goldsgym.com/losangelesdtca/wp-content/uploads/sites/505/2016/08/golds-gym-downtown-la-ca-weight-training.jpg");
            }
        });
    }

    private void createJson(String name, String address, String img) {
        JSONObject rootObj=new JSONObject();
        JSONArray abcd=new JSONArray();
        JSONObject ins=new JSONObject();
        try {
            rootObj.put("abcd",abcd);
            abcd.put(ins);
            ins.put("name",name);
            ins.put("address",address);
            ins.put("image",img);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(GenerateQR.this,rootObj.toString(),Toast.LENGTH_SHORT).show();
        try {
            bitmap = TextToImageEncode(rootObj.toString());

            qrImage.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

}
