package com.example.validator

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.validator.Helper.GraphicOverlay
import com.example.validator.Helper.RectOverlay
import com.google.android.gms.tasks.OnSuccessListener
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import com.google.mlkit.vision.objects.defaults.PredefinedCategory
import kotlinx.android.synthetic.main.activity_inferencer.*
import kotlin.math.max


class InferencerActivity: AppCompatActivity() {
    private var graphicOverlay: GraphicOverlay? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inferencer)

        val context = this
        val db = DataBaseHandler(context)
        val bmp = db.readDataImg()
//        imageViewshow.setImageBitmap(bmp)

        graphicOverlay = findViewById(R.id.graphic_overlay_inf)

        var image = InputImage.fromBitmap(bmp, 0)

//        // Live detection and tracking
//        val options = ObjectDetectorOptions.Builder()
//            .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
//            .enableClassification()  // Optional
//            .build()

// Multiple object detection in static images
        val options = ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .enableMultipleObjects()
            .enableClassification()
            .build()

        val objectDetector = ObjectDetection.getClient(options)
        var count = 0
        var ids = 0
        var lab = ""


        // Clear the overlay first
        graphicOverlay!!.clear()
        // Get the dimensions of the image view
        val targetedSize = Pair(768, 1024)
        // Determine how much to scale down the image
        if (bmp != null) {
            val scaleFactor = max(
                bmp.width.toFloat() / targetedSize.first.toFloat(),
                bmp.height.toFloat() / targetedSize.second.toFloat()
            )
            val resizedBitmap = Bitmap.createScaledBitmap(
                bmp,
                (bmp.width / scaleFactor).toInt(),
                (bmp.height / scaleFactor).toInt(),
                true
            )
            imageViewshow!!.setImageBitmap(resizedBitmap)
            image = InputImage.fromBitmap(resizedBitmap, 0)
        }

        button_inf.setOnClickListener {
            objectDetector.process(image)
                .addOnSuccessListener { detectedObjects ->
                    for (detectedObject in detectedObjects) {
                        graphicOverlay?.add(ObjectGraphic(graphicOverlay!!, detectedObject))
                        count += 1
                        for (label in detectedObject.labels) {
                            count += 100
                            val text = label.text
                            lab += text
                        }
                    }
                    textView_count.text = "Count: " + count.toString() + lab
                }
//                .addOnSuccessListener(object : OnSuccessListener<List<DetectedObject>> {
//                    override fun onSuccess(results: List<DetectedObject>) {
//                        for (result in results) {
//                            graphicOverlay?.add(ObjectGraphic(graphicOverlay!!, result))
//                            count += 1
//                            ids += if (result.trackingId != null) {
//                                result.trackingId
//                            } else {
//                                100
//                            }
//                        }
//                        textView_count.text = "Count: " + ids.toString() + count.toString()
//                    }
//                })
                .addOnFailureListener { e ->
                    Toast.makeText(this, "cannot inferrence", Toast.LENGTH_SHORT).show()
                }
        }
    }
}