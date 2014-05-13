package com.davidpablos.intentresolution;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainFragment extends Fragment {
	
	public static final String INFO_EDITTEXTMAIN = "INFO_EDITTEXT1";
	private final int FORM_ACTION = 0;
	private final int CAMERA_ACTION = 1;
	private final int CONTACTS_ACTION = 2;
	public static final String TEXTVIEW = "TEXTVIEW";
	public static final String IMG = "IMG";
	
	private View v;
	private ImageView iv;
	
	String mCurrentPhotoPath;
	File image;
	
	public interface IIntentResolution {
	}
	
	private IIntentResolution activity;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			this.activity = (IIntentResolution)activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " debe implementar la interfaz IIntentResolution");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Create, or inflate the Fragment's UI, and return it.
		// If this Fragment has no UI then return null.
		
		this.v = inflater.inflate(R.layout.fragment_main, container, false);
		
		iv = (ImageView)v.findViewById(R.id.imageViewMain);
		
		Button boton = (Button) v.findViewById(R.id.buttonFormMain);		
		boton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View viewButton) {
				Log.d("TAG", "onClick() Form");
				EditText tv = (EditText) v.findViewById(R.id.editTextMain);
				String info = tv.getText().toString();
				Intent intent = new Intent(getActivity(), SecondActivity.class);
				intent.putExtra(INFO_EDITTEXTMAIN, info);
				startActivityForResult(intent, FORM_ACTION);
			}
		});
		
		Button botonCamera = (Button) v.findViewById(R.id.buttonCameraMain);
		botonCamera.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View viewButton) {
				Log.d("TAG", "onClick() Camera");
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
//			        startActivityForResult(intent, CAMERA_ACTION);
			    	File photoFile = null;
			        try {
			            photoFile = createImageFile();
			        } catch (IOException ex) {
			            // Error occurred while creating the File
			        }
			        // Continue only if the File was successfully created
			        if (photoFile != null) {
			            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
			            startActivityForResult(intent, CAMERA_ACTION);
			        }
			    }
			}
		});
		return v;
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("TAG", "onActivityResult() fragment ");
		
		switch(requestCode) {
		case FORM_ACTION:
			
			if (resultCode == Activity.RESULT_OK) {
				Log.d("TAG", "FORM_ACTION RESULT OK");
				TextView tv = (TextView) v.findViewById(R.id.textViewMain);
				tv.setText(data.getStringExtra(SecondFragment.INFO_EDITTEXTSECOND));
			}
			      
			break;
			
		case CAMERA_ACTION:
		    if (requestCode == CAMERA_ACTION && resultCode == Activity.RESULT_OK) {
//		        Bundle extras = data.getExtras();
//		        Bitmap imageBitmap = (Bitmap) extras.get("data");
//		        iv.setImageBitmap(imageBitmap);
		    	setPic();
		    }
		    break;
		}
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		TextView tv = (TextView)v.findViewById(R.id.textViewMain);
		outState.putString(TEXTVIEW, tv.getText().toString());
		if(image != null){
			outState.putString(IMG, image.getPath());
		}
		
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		setPic();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		if(savedInstanceState != null) {
			image = new File(savedInstanceState.getString(IMG));
		}
	}
	
	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewStateRestored(savedInstanceState);
	}
	
	private File createImageFile() throws IOException {
	    // Create an image file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = "JPEG_" + timeStamp + "_";
	    File storageDir = Environment.getExternalStorageDirectory();
	    image = File.createTempFile(
	        imageFileName,  /* prefix */
	        ".jpg",         /* suffix */
	        storageDir      /* directory */
	    );

	    // Save a file: path for use with ACTION_VIEW intents
	    mCurrentPhotoPath = "file:" + image.getAbsolutePath();
	    return image;
	}
	
	private void setPic() {
	    // Get the dimensions of the View
	    int targetW = iv.getWidth();
	    int targetH = iv.getHeight();
	    
	    if(image != null) {
	    	 // Get the dimensions of the bitmap
		    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		    bmOptions.inJustDecodeBounds = true;
		    BitmapFactory.decodeFile(image.getPath(), bmOptions);
		    int photoW = bmOptions.outWidth;
		    int photoH = bmOptions.outHeight;

		    // Determine how much to scale down the image
		    int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

		    // Decode the image file into a Bitmap sized to fill the View
		    bmOptions.inJustDecodeBounds = false;
		    bmOptions.inSampleSize = scaleFactor;
		    bmOptions.inPurgeable = true;

		    Bitmap bitmap = BitmapFactory.decodeFile(image.getPath(), bmOptions);
		    iv.setImageBitmap(bitmap);
	    }

	   
	}

}
