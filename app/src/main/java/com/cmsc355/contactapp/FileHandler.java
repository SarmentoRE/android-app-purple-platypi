package com.cmsc355.contactapp;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

import com.sun.mail.imap.Utility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

import yogesh.firzen.filelister.FileListerDialog;
import yogesh.firzen.filelister.OnFileSelectedListener;

import static com.cmsc355.contactapp.App.context;
import static com.cmsc355.contactapp.App.getContext;

/**
 * Created by Austin on 11/11/2017.
 */

public class FileHandler extends Activity{
    private String fileAsString;
    private File file;
    private byte[] byteRepresentation;
    private FileListerDialog fileListerDialog = FileListerDialog.createFileListerDialog(context);
    Activity activity;
    private int REQUEST_CAMERA = 0;
    private int REQUEST_GALLERY = 1;

    public FileHandler(Activity activity){
        this.activity = activity;
    }

    private String getFileAsString() {
        if (file.exists()) {
            try {
                byteRepresentation = FileUtils.readFileToByteArray(file);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            fileAsString = byteRepresentation.toString();
            return fileAsString;
        }
        return "";
    }

    public void addFileToContact(File file, Contact contact) {
        contact.addAttribute(file.getName(),getFileAsString());
    }

    public File chooseFile() {
        fileListerDialog.setOnFileSelectedListener(new OnFileSelectedListener() {
            @Override
            public void onFileSelected(File selectedFile, String path) {
                file = selectedFile;
            }
        });
        fileListerDialog.setFileFilter(FileListerDialog.FILE_FILTER.ALL_FILES);
        fileListerDialog.show();
        return file;
    }

    public void choosePicture() {
        final String[] options = {"Take Photo","Choose From Library","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selected) {
                switch (options[selected]) {
                    case "TakePhoto":
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        activity.startActivityForResult(intent,REQUEST_CAMERA);
                        break;
                    case "Choose From Library":
                        break;
                    case "Cancel":
                        break;
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            }
            else if (requestCode == REQUEST_GALLERY) {
                //onSelectFromGalleryResult(data);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        ImageView img = findViewById(R.id.info_pic);
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".png");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        img.setImageBitmap(thumbnail);
    }
}
