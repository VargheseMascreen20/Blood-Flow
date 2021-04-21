package com.var.bloodflow.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.var.bloodflow.R;

import com.var.bloodflow.ModelClasses.Photo;

import com.var.bloodflow.ModelClasses.UserAccountSettings;
import com.var.bloodflow.ModelClasses.UserSettings;
//import com.connect.foodies.foodies.profile.AccountSettingsActivity;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.var.bloodflow.ModelClasses.Users;
import com.var.bloodflow.fragments.HomeFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class FirebaseMethods {

    private static final String TAG = "FirebaseMethods";
    
    //firebase

    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference reference;
    private StorageReference mStorageReference;
    private String userID;

    //vars
    private Context mContext;
    private double mPhotoUploadProgress = 0;

    public FirebaseMethods(Context context) {
        fAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        reference = mFirebaseDatabase.getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mContext = context;

        if(fAuth.getCurrentUser() != null){
            userID = fAuth.getCurrentUser().getUid();
        }
    }

//    public void uploadNewPhoto(String photoType, final String caption, final int count, final String imgUrl,
//                               Bitmap bm){
//        Log.d(TAG, "uploadNewPhoto: attempting to uplaod new photo.");
//
//        FilePaths filePaths = new FilePaths();
//        //case1) new photo
//        if(photoType.equals(mContext.getString(R.string.new_photo))){
//            Log.d(TAG, "uploadNewPhoto: uploading NEW photo.");
//
//            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
//            StorageReference storageReference = mStorageReference
//                    .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + user_id + "/photo" + (count + 1));
//
//            //convert image url to bitmap
//            if(bm == null){
//                bm = ImageManager.getBitmap(imgUrl);
//            }
//
//            byte[] bytes = ImageManager.getBytesFromBitmap(bm, 100);
//
//            UploadTask uploadTask = null;
//            uploadTask = storageReference.putBytes(bytes);
//
//            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Uri firebaseUrl = taskSnapshot.getDownloadUrl();
//
//                    Toast.makeText(mContext, "photo upload success", Toast.LENGTH_SHORT).show();
//
//                    //add the new photo to 'photos' node and 'user_photos' node
//                    addPhotoToDatabase(caption, firebaseUrl.toString());
//
//                    //navigate to the main feed so the user can see their photo
//                    Intent intent = new Intent(mContext, HomeFragment.class);
//                    mContext.startActivity(intent);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(Exception e) {
//                    Log.d(TAG, "onFailure: Photo upload failed.");
//                    Toast.makeText(mContext, "Photo upload failed ", Toast.LENGTH_SHORT).show();
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//
//                    if(progress - 15 > mPhotoUploadProgress){
//                        Toast.makeText(mContext, "photo upload progress: " + String.format("%.0f", progress) + "%", Toast.LENGTH_SHORT).show();
//                        mPhotoUploadProgress = progress;
//                    }
//
//                    Log.d(TAG, "onProgress: upload progress: " + progress + "% done");
//                }
//            });
//
//        }
//        //case new profile photo
//        else if(photoType.equals(mContext.getString(R.string.profile_photo))){
//            Log.d(TAG, "uploadNewPhoto: uploading new PROFILE photo");
//
//
//            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
//            StorageReference storageReference = mStorageReference
//                    .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + user_id + "/profile_photo");
//
//            //convert image url to bitmap
//            if(bm == null){
//                bm = ImageManager.getBitmap(imgUrl);
//            }
//            byte[] bytes = ImageManager.getBytesFromBitmap(bm, 100);
//
//            UploadTask uploadTask = null;
//            uploadTask = storageReference.putBytes(bytes);
//
//            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Uri firebaseUrl = taskSnapshot.getDownloadUrl();
//
//                    Toast.makeText(mContext, "photo upload success", Toast.LENGTH_SHORT).show();
//
//                    //insert into 'user_account_settings' node
//                    setProfilePhoto(firebaseUrl.toString());
//
//                    ((AccountSettingsActivity)mContext).setViewPager(
//                            ((AccountSettingsActivity)mContext).pagerAdapter
//                                    .getFragmentNumber(mContext.getString(R.string.edit_profile_fragment))
//                    );
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(Exception e) {
//                    Log.d(TAG, "onFailure: Photo upload failed.");
//                    Toast.makeText(mContext, "Photo upload failed ", Toast.LENGTH_SHORT).show();
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//
//                    if(progress - 15 > mPhotoUploadProgress){
//                        Toast.makeText(mContext, "photo upload progress: " + String.format("%.0f", progress) + "%", Toast.LENGTH_SHORT).show();
//                        mPhotoUploadProgress = progress;
//                    }
//
//                    Log.d(TAG, "onProgress: upload progress: " + progress + "% done");
//                }
//            });
//        }
//
//    }

    private void setProfilePhoto(String url){
        Log.d(TAG, "setProfilePhoto: setting new profile image: " + url);

        reference.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mContext.getString(R.string.profile_photo))
                .setValue(url);
    }

    private String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CANADA);
        sdf.setTimeZone(TimeZone.getTimeZone("Canada/Pacific"));
        return sdf.format(new Date());
    }

//    private void addPhotoToDatabase(String caption, String url){
//        Log.d(TAG, "addPhotoToDatabase: adding photo to database.");
//
//        String tags = StringManipulation.getTags(caption);
//        String newPhotoKey = reference.child(mContext.getString(R.string.dbname_photos)).push().getKey();
//        Photo photo = new Photo();
//        photo.setCaption(caption);
//        photo.setDate_created(getTimestamp());
//        photo.setImage_path(url);
//        photo.setTags(tags);
//        photo.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        photo.setPhoto_id(newPhotoKey);
//
//        //insert into database
//        reference.child(mContext.getString(R.string.dbname_user_photos))
//                .child(FirebaseAuth.getInstance().getCurrentUser()
//                        .getUid()).child(newPhotoKey).setValue(photo);
//        reference.child(mContext.getString(R.string.dbname_photos)).child(newPhotoKey).setValue(photo);
//
//    }

    public int getImageCount(DataSnapshot dataSnapshot){
        int count = 0;
        for(DataSnapshot ds: dataSnapshot
                .child(mContext.getString(R.string.dbname_user_photos))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .getChildren()){
            count++;
        }
        return count;
    }

    /**
     * Update 'user_account_settings' node for the current user
     * @param displayName
     * @param website
     * @param description
     * @param phoneNumber
     */
    public void updateUserAccountSettings(String displayName, String website, String description, long phoneNumber){

        Log.d(TAG, "updateUserAccountSettings: updating user account settings.");

        if(displayName != null){
            reference.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_display_name))
                    .setValue(displayName);
        }


        if(website != null) {
            reference.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_website))
                    .setValue(website);
        }

        if(description != null) {
            reference.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_description))
                    .setValue(description);
        }

        if(phoneNumber != 0) {
            reference.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_phone_number))
                    .setValue(phoneNumber);
        }
    }

    /**
     * update username in the 'users' node and 'user_account_settings' node
     * @param username
     */
    public void updateUsername(String username){
        Log.d(TAG, "updateUsername: upadting username to: " + username);

        reference.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .child(mContext.getString(R.string.field_username))
                .setValue(username);

        reference.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.field_username))
                .setValue(username);
    }

    /**
     * update the email in the 'user's' node
     * @param email
     */
    public void updateEmail(String email){
        Log.d(TAG, "updateEmail: upadting email to: " + email);

        reference.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .child(mContext.getString(R.string.field_email))
                .setValue(email);

    }

//    public boolean checkIfUsernameExists(String uname, DataSnapshot datasnapshot){
//        Log.d(TAG, "checkIfUsernameExists: checking if " + uname + " already exists.");
//
//        Users user = new Users();
//
//        for (DataSnapshot ds: datasnapshot.child(userID).getChildren()){
//            Log.d(TAG, "checkIfUsernameExists: datasnapshot: " + ds);
//
//            user.setUname(ds.getValue(Users.class).getUname());
//            Log.d(TAG, "checkIfUsernameExists: username: " + user.getUname());
//
//            if(StringManipulation.expandUsername(user.getUname()).equals(uname)){
//                Log.d(TAG, "checkIfUsernameExists: FOUND A MATCH: " + user.getUname());
//                return true;
//            }
//        }
//        return false;
//    }

    /**
     * Register a new email and password to Firebase Authentication
     * @param email
     * @param password
     * @param username
     */
    public void registerNewEmail(final String email, String password, final String username){
        fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();

                        }
                        else if(task.isSuccessful()){
                            //send verificaton email
                            sendVerificationEmail();

                            userID = fAuth.getCurrentUser().getUid();
                            Log.d(TAG, "onComplete: Authstate changed: " + userID);
                        }

                    }
                });
    }

    public void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if(task.isSuccessful()){

                            }else{
                                Toast.makeText(mContext, "couldn't send verification email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    /**
     * Add information to the users nodes
     * Add information to the user_account_settings node
     * @param email
     * @param username
     * @param description
     * @param website
     * @param profile_photo
     */
//    public void addNewUser(String email, String username, String description, String website, String profile_photo){
//
//        Users user = new Users( userID,  1,  email,  StringManipulation.condenseUsername(username) );
//
//        reference.child(mContext.getString(R.string.dbname_users))
//                .child(userID)
//                .setValue(user);
//
//
//        UserAccountSettings settings = new UserAccountSettings(
//                description,
//                username,
//                0,
//                0,
//                0,
//                profile_photo,
//                StringManipulation.condenseUsername(username),
//                website,
//                userID
//        );
//
//        reference.child(mContext.getString(R.string.dbname_user_account_settings))
//                .child(userID)
//                .setValue(settings);
//
//    }


    /**
     * Retrieves the account settings for teh user currently logged in
     * Database: user_acount_Settings node
     * @param dataSnapshot
     * @return
     */
    public UserSettings getUserSettings(DataSnapshot dataSnapshot){
        Log.d(TAG, "getUserAccountSettings: retrieving user account settings from firebase.");


        UserAccountSettings settings  = new UserAccountSettings();
        Users user = new Users();

        for(DataSnapshot ds: dataSnapshot.getChildren()){

            // user_account_settings node
            if(ds.getKey().equals(mContext.getString(R.string.dbname_user_account_settings))) {
                Log.d(TAG, "getUserAccountSettings: user account settings node datasnapshot: " + ds);

                try {

                    settings.setDisplay_name(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getDisplay_name()
                    );
                    settings.setUsername(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getUsername()
                    );
                    settings.setWebsite(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getWebsite()
                    );
                    settings.setDescription(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getDescription()
                    );
                    settings.setProfile_photo(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getProfile_photo()
                    );
                    settings.setPosts(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getPosts()
                    );
                    settings.setFollowing(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getFollowing()
                    );
                    settings.setFollowers(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getFollowers()
                    );

                    Log.d(TAG, "getUserAccountSettings: retrieved user_account_settings information: " + settings.toString());
                } catch (NullPointerException e) {
                    Log.e(TAG, "getUserAccountSettings: NullPointerException: " + e.getMessage());
                }
            }


                // users node
                Log.d(TAG, "getUserSettings: snapshot key: " + ds.getKey());
                if(ds.getKey().equals(mContext.getString(R.string.dbname_users))) {
                    Log.d(TAG, "getUserAccountSettings: users node datasnapshot: " + ds);

                    user.setName(
                            ds.child(userID)
                                    .getValue(Users.class)
                                    .getName()
                    );
                    user.setUname(
                            ds.child(userID)
                                    .getValue(Users.class)
                                    .getUname()
                    );
                    user.setDob(
                            ds.child(userID)
                                    .getValue(Users.class)
                                    .getDob()
                    );
                    user.setPhno(
                            ds.child(userID)
                                    .getValue(Users.class)
                                    .getPhno()
                    );
                    user.setBldgrp(
                            ds.child(userID)
                                    .getValue(Users.class)
                                    .getBldgrp()
                    );
                    user.setGender(
                            ds.child(userID)
                                    .getValue(Users.class)
                                    .getGender()
                    );


                    Log.d(TAG, "getUserAccountSettings: retrieved users information: " + user.toString());
                }
        }
        return new UserSettings(user, settings);

    }

}












































