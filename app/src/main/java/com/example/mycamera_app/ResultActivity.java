package com.example.mycamera_app;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mycamera_app.utils.BitmapExtractor;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {


    TextView resultTextView;

    private static final String MODEL_FILE = "file:///android_asset/_basicTFonAndroid.pb";
    private static final String INPUT_NODES = "modelInputA";
    private static final String[] OUTPUT_NODES = {"modelOutputAB"};
    private static final int[] INPUT_DIM = {1};

    private TensorFlowInferenceInterface inferenceInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        resultTextView = (TextView) findViewById(R.id.resultTextView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Uri videoUri = Uri.parse(getIntent().getExtras().getString("videoUri"));


        List<Bitmap> bitmaps = new BitmapExtractor().createBitmaps(this, videoUri);

        List<Bitmap> resizedBitmaps = new ArrayList<>();

        List<byte[]> bytesList = new ArrayList<>();

        for (Bitmap bitmap : bitmaps) {
            Bitmap smallBitmap = new BitmapExtractor().getResizedBitmap(bitmap, 28, 28);
            Bitmap bwBitmap = new BitmapExtractor().convertToBlackAndWhite(smallBitmap);
            resizedBitmaps.add(bwBitmap);
            byte[] bitmapBytes = new BitmapExtractor().convertBitmapToByteArrayUncompressed(bwBitmap);
            bytesList.add(bitmapBytes);
            //resizedBitmaps.add(smallBitmap);
        }

        List<Bitmap> tempBitmaps = resizedBitmaps;


        String output = runOnNeuralNet(bytesList);

        resultTextView.setText(output);


    }

    private String runOnNeuralNet(List<byte[]> bytesList) {
        inferenceInterface = new TensorFlowInferenceInterface(getAssets(), MODEL_FILE);

        String outputConcated = "";

        for (byte[] byteData : bytesList) {

            inferenceInterface.feed(INPUT_NODES, byteData, INPUT_DIM[0]);

            inferenceInterface.run(OUTPUT_NODES);

            byte[] modelOutputAB = new byte[4080];
            inferenceInterface.fetch(OUTPUT_NODES[0], modelOutputAB);

            String output = new String(modelOutputAB);

            outputConcated = outputConcated + " " + output;
        }

        return outputConcated;

    }
}

