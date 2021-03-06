package com.sdk.snippets.modules;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.quickblox.core.Consts;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.core.QBProgressCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.helper.FileHelper;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.core.request.QBRequestUpdateBuilder;
import com.quickblox.customobjects.QBCustomObjects;
import com.quickblox.customobjects.QBCustomObjectsFiles;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.customobjects.model.QBCustomObjectFileField;
import com.quickblox.customobjects.model.QBPermissions;
import com.quickblox.customobjects.model.QBPermissionsLevel;
import com.sdk.snippets.*;
import com.sdk.snippets.core.AsyncSnippet;
import com.sdk.snippets.core.Snippet;
import com.sdk.snippets.core.Snippets;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by QuickBlox on 22.01.14.
 */
public class SnippetsCustomObjects extends Snippets {

    private static final String TAG = SnippetsCustomObjects.class.getSimpleName();
    // Define custom object model in QB Admin Panel
    // http://quickblox.com/developers/Custom_Objects
    //
    private final String MOVIE_CLASS = "Movie";

    private File imageFile;

    public SnippetsCustomObjects(Context context) {
        super(context);

        // get file1
        int fileId = R.raw.kharkov;
        InputStream is = context.getResources().openRawResource(fileId);
        imageFile = FileHelper.getFileInputStream(is, "kharkivimage.jpg", "kharkivimage");


        snippets.add(getCustomObjectByID);
        snippets.add(getCustomObjectByIDSynchronous);
        //
        snippets.add(getCustomObjectsByIDs);
        snippets.add(getCustomObjectsByIDsSynchronous);
        //
        snippets.add(getCustomObjects);
        snippets.add(getCustomObjectsSynchronous);
        //
        snippets.add(getCountCustomsObjects);
        snippets.add(getCountCustomsObjectsSynchronous);
        //
        snippets.add(createCustomObject);
        snippets.add(createCustomObjectSynchronous);
        //
        snippets.add(createCustomObjects);
        snippets.add(createCustomObjectsSynchronous);
        //
        snippets.add(updateCustomObject);
        snippets.add(updateCustomObjectSynchronous);
        //
        snippets.add(updateCustomObjects);
        snippets.add(updateCustomObjectsSynchronous);
        //
        snippets.add(deleteCustomObject);
        snippets.add(deleteCustomObjectSynchronous);
        //
        snippets.add(deleteCustomObjects);
        snippets.add(deleteCustomObjectsSynchronous);
        //
        //
        snippets.add(getCustomObjectPermissions);
        snippets.add(getCustomObjectPermissionsSynchronous);
        //
        snippets.add(updateCustomObjectPermissions);
        snippets.add(updateCustomObjectPermissionsSynchronous);
        //
        snippets.add(createCustomObjectWithPermissions);
        snippets.add(createCustomObjectWithPermissionsSynchronous);
        //
        //
        snippets.add(uploadFile);
        snippets.add(uploadFileSynchronous);
        //
        snippets.add(downloadFile);
        snippets.add(downloadFileSynchronous);
        //
        snippets.add(deleteFile);
        snippets.add(deleteFileSynchronous);
    }


    //
    //////////////////////////////////// Get Custom Object by ID ////////////////////////////////////
    //


    Snippet getCustomObjectByID = new Snippet("get object by ID") {
        @Override
        public void execute() {
            QBCustomObject object = new QBCustomObject("JSON", "561cbde9a28f9a520c001c35");

            QBCustomObjects.getObject(object, new QBEntityCallbackImpl<QBCustomObject>(){
                @Override
                public void onSuccess(QBCustomObject customObject, Bundle params) {
                    Log.i(TAG, ">>> custom object: " + customObject);

                    ArrayList<Object> arr = (ArrayList<Object>)customObject.getFields().get("arrint");

                    for(Object o : arr){
                        Log.i(TAG, o.getClass().getCanonicalName());
                    }
                }

                @Override
                public void onError(List<String> errors) {
                    handleErrors(errors);
                }
            });
        }
    };

    Snippet getCustomObjectByIDSynchronous = new AsyncSnippet("get object by ID (synchronous)", context) {
        @Override
        public void executeAsync() {
            QBCustomObject object = new QBCustomObject(MOVIE_CLASS, "53f323ab535c12567903ba43");

            QBCustomObject getObject = null;
            try {
                getObject = QBCustomObjects.getObject(object);
            } catch (QBResponseException e) {
                setException(e);
            }
            if (getObject != null) {
                Log.i(TAG, ">>> custom object: " + getObject);
            }
        }
    };


    //
    ////////////////////////////////// Get Custom Objects by IDs ////////////////////////////////////
    //


    Snippet getCustomObjectsByIDs = new Snippet("get objects by IDs") {
        @Override
        public void execute() {
            StringifyArrayList<String> coIDs = new StringifyArrayList<String>();
            coIDs.add("53f323ab535c12567903ba43");
            coIDs.add("53f32498535c12a57a03f434");

            QBCustomObjects.getObjectsByIds(MOVIE_CLASS, coIDs, new QBEntityCallbackImpl<ArrayList<QBCustomObject>>() {
                @Override
                public void onSuccess(ArrayList<QBCustomObject> customObjects, Bundle params) {
                    Log.i(TAG, ">>> custom objects: " + customObjects);
                }

                @Override
                public void onError(List<String> errors) {
                    handleErrors(errors);
                }
            });
        }
    };

    Snippet getCustomObjectsByIDsSynchronous = new AsyncSnippet("get objects by IDs (synchronous)", context) {
        @Override
        public void executeAsync() {
            StringifyArrayList<String> coIDs = new StringifyArrayList<String>();
            coIDs.add("53f323ab535c12567903ba43");
            coIDs.add("53f32498535c12a57a03f434");

            ArrayList<QBCustomObject> getObjects = null;
            try {
                getObjects = QBCustomObjects.getObjectsByIds(MOVIE_CLASS, coIDs);
            } catch (QBResponseException e) {
                setException(e);
            }
            if (getObjects != null) {
                Log.i(TAG, ">>> custom objects: " + getObjects);
            }
        }
    };


    //
    //////////////////////////////////// Get Custom Objects  //////////////////////////////////////
    //


    Snippet getCustomObjects = new Snippet("get objects") {
        @Override
        public void execute() {
            QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
            requestBuilder.setPagesLimit(10);
            requestBuilder.all("tags", "a", "b");
//            requestBuilder.gt("rating", 1);
//            requestBuilder.near("location", new Double[]{2.,3.}, 1000);
            

            QBCustomObjects.getObjects(MOVIE_CLASS, requestBuilder, new QBEntityCallbackImpl<ArrayList<QBCustomObject>>() {

                @Override
                public void onSuccess(ArrayList<QBCustomObject> customObjects, Bundle params) {
                    int skip = params.getInt(Consts.SKIP);
                    int limit = params.getInt(Consts.LIMIT);
                    Log.i(TAG, "limit=" + limit + " skip=" + skip);
                    Log.i(TAG, ">>> custom objects: " + customObjects);
                }

                @Override
                public void onError(List<String> errors) {
                    handleErrors(errors);
                }
            });
        }
    };

    Snippet getCustomObjectsSynchronous = new AsyncSnippet("get objects (synchronous)", context) {

        @Override
        public void executeAsync() {
            QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
            requestBuilder.setPagesLimit(10);
            requestBuilder.gt("rating", 1);

            Bundle params = new Bundle();
            ArrayList<QBCustomObject> objects = null;
            try {
                objects = QBCustomObjects.getObjects(MOVIE_CLASS, requestBuilder, params);
            } catch (QBResponseException e) {
                setException(e);
            }
            if (objects != null) {
                int skip = params.getInt(Consts.SKIP);
                int limit = params.getInt(Consts.LIMIT);
                Log.i(TAG, "limit=" + limit + " skip=" + skip);
                Log.i(TAG, ">>> custom objects: " + objects);
            }
        }
    };


    //
    //////////////////////////////// Get Count Custom Objects  //////////////////////////////////////
    //


    Snippet getCountCustomsObjects = new Snippet("get count") {
        @Override
        public void execute() {
            QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
            requestBuilder.gt("rating", 1);

            QBCustomObjects.countObjects(MOVIE_CLASS, requestBuilder, new QBEntityCallbackImpl<Integer>() {

                    @Override
                    public void onSuccess(Integer count, Bundle params) {
                        Log.i(TAG, "count=" + count);
                    }

                    @Override
                    public void onError(List<String> errors) {
                        handleErrors(errors);
                    }
                }
            );
        }
    };

    Snippet getCountCustomsObjectsSynchronous = new AsyncSnippet("get count (synchronous)", context) {

        @Override
        public void executeAsync() {
            QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
            requestBuilder.gt("rating", 1);

            Integer count = null;
            try {
                count = QBCustomObjects.countObjects(MOVIE_CLASS, requestBuilder);
            } catch (QBResponseException e) {
                setException(e);
            }
            if (count != null) {
                Log.i(TAG, ">>> count: " + count);
            }
        }
    };


    //
    /////////////////////////////////// Create Custom Object  //////////////////////////////////////
    //


    Snippet createCustomObject = new Snippet("create object") {
        @Override
        public void execute() {
            QBCustomObject newRecord = new QBCustomObject(MOVIE_CLASS);
            newRecord.put("rating", 10);
            newRecord.put("name", "The Dark Knight");
            newRecord.put("description", "About Batman");

            QBCustomObjects.createObject(newRecord, new QBEntityCallbackImpl<QBCustomObject>() {
                @Override
                public void onSuccess(QBCustomObject object, Bundle params) {
                    Log.i(TAG, ">>> created object: " + object);
                }

                @Override
                public void onError(List<String> errors) {
                    handleErrors(errors);
                }
            });
        }
    };

    Snippet createCustomObjectSynchronous = new AsyncSnippet("create object (synchronous)", context) {

        @Override
        public void executeAsync() {
            QBCustomObject newRecord = new QBCustomObject(MOVIE_CLASS);
            newRecord.put("rating", 10);
            newRecord.put("name", "The Dark Knight");
            newRecord.put("description", "About Batman");

            QBCustomObject createdObject = null;
            try {
                createdObject = QBCustomObjects.createObject(newRecord);
            } catch (QBResponseException e) {
                setException(e);
            }
            if (createdObject != null) {
                Log.i(TAG, ">>> created object: " + createdObject);
            }
        }
    };


    //
    ////////////////////////////////// Create Custom Objects  //////////////////////////////////////
    //


    Snippet createCustomObjects = new Snippet("create objects") {

        @Override
        public void execute() {
            List<QBCustomObject> qbCustomObjectList = new ArrayList<QBCustomObject>(2);

            QBCustomObject newRecord1 = new QBCustomObject(MOVIE_CLASS);
            newRecord1.put("rating", 10);
            newRecord1.put("name", "The Dark Knight");
            newRecord1.put("description", "About Batman");

            QBCustomObject newRecord2 = new QBCustomObject(MOVIE_CLASS);
            newRecord2.put("rating", 6);
            newRecord2.put("name", "The Godfather");
            newRecord2.put("description", "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son");

            qbCustomObjectList.add(newRecord1);
            qbCustomObjectList.add(newRecord2);

            QBCustomObjects.createObjects(qbCustomObjectList, new QBEntityCallbackImpl<ArrayList<QBCustomObject>>() {
                @Override
                public void onSuccess(ArrayList<QBCustomObject> qbCustomObjects, Bundle args) {
                    super.onSuccess(qbCustomObjects, args);
                    Log.i(TAG, ">>> custom object list: " + qbCustomObjects);
                }

                @Override
                public void onError(List<String> errors) {
                    handleErrors(errors);
                }
            });
        }
    };

    Snippet createCustomObjectsSynchronous = new AsyncSnippet("create objects (synchronous)", context) {

        @Override
        public void executeAsync() {
            List<QBCustomObject> qbCustomObjectList = new ArrayList<QBCustomObject>(2);

            QBCustomObject newRecord1 = new QBCustomObject(MOVIE_CLASS);
            newRecord1.put("rating", 10);
            newRecord1.put("name", "The Dark Knight");
            newRecord1.put("description", "About Batman");

            QBCustomObject newRecord2 = new QBCustomObject(MOVIE_CLASS);
            newRecord2.put("rating", 6);
            newRecord2.put("name", "The Godfather");
            newRecord2.put("description", "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son");

            qbCustomObjectList.add(newRecord1);
            qbCustomObjectList.add(newRecord2);

            List<QBCustomObject> createdObjects = null;
            try {
                createdObjects = QBCustomObjects.createObjects(qbCustomObjectList);
            } catch (QBResponseException e) {
                setException(e);
            }
            if (createdObjects != null) {
                Log.i(TAG, ">>> created objects: " + createdObjects);
            }
        }
    };


    //
    /////////////////////////////////// Update Custom Object  //////////////////////////////////////
    //


    Snippet updateCustomObject = new Snippet("update object") {
        @Override
        public void execute() {
            QBCustomObject record = new QBCustomObject(MOVIE_CLASS, "546c757f535c1287db0adb4d");
            //
            HashMap<String, Object> fields = new HashMap<String, Object>();
            fields.put("rating", 10);
            record.setFields(fields);

            QBRequestUpdateBuilder rb = new QBRequestUpdateBuilder();
            rb.updateArrayValue("tags", 1, "44");

            QBCustomObjects.updateObject(record, rb, new QBEntityCallbackImpl<QBCustomObject>() {
                @Override
                public void onSuccess(QBCustomObject object, Bundle params) {
                    Log.i(TAG, ">>> updated record: : " + object.toString());
                }

                @Override
                public void onError(List<String> errors) {
                    handleErrors(errors);
                }
            });
        }
    };

    Snippet updateCustomObjectSynchronous = new AsyncSnippet("update object (synchronous)", context) {

        @Override
        public void executeAsync() {
            QBCustomObject obj = new QBCustomObject(MOVIE_CLASS, "53f365f06fd1dfa9c43ce5a3");
            //
            HashMap<String, Object> fields = new HashMap<String, Object>();
            fields.put("rating", 11);
            obj.setFields(fields);

            QBCustomObject updatedObject = null;
            try {
                updatedObject = QBCustomObjects.updateObject(obj);
            } catch (QBResponseException e) {
                setException(e);
            }
            if (updatedObject != null) {
                Log.i(TAG, ">>> updated object: " + updatedObject);
            }
        }
    };


    //
    ////////////////////////////////// Update Custom Objects  //////////////////////////////////////
    //


    Snippet updateCustomObjects = new Snippet("update objects") {
        @Override
        public void execute() {
            List<QBCustomObject> qbCustomObjectList = new ArrayList<QBCustomObject>(2);

            QBCustomObject record1 = new QBCustomObject(MOVIE_CLASS);
            record1.setCustomObjectId("53f70b55535c12201e0014d4");
            HashMap<String, Object> fields = new HashMap<String, Object>();
            fields.put("rating", 1);
            record1.setFields(fields);

            QBCustomObject record2 = new QBCustomObject(MOVIE_CLASS);
            record2.setCustomObjectId("53f3660b6fd1dfa9c43ce5a5");
            HashMap<String, Object> fields2 = new HashMap<String, Object>();
            fields2.put("rating", 1);
            record2.setFields(fields2);

            qbCustomObjectList.add(record1);
            qbCustomObjectList.add(record2);

            QBCustomObjects.updateObjects(qbCustomObjectList, new QBEntityCallbackImpl<ArrayList<QBCustomObject>>() {
                @Override
                public void onSuccess(ArrayList<QBCustomObject> objects, Bundle params) {
                    Log.i(TAG, ">>> updated records: " + objects.toString());
                }

                @Override
                public void onError(List<String> errors) {
                    handleErrors(errors);
                }
            });
        }
    };

    Snippet updateCustomObjectsSynchronous = new AsyncSnippet("update objects (synchronous)", context) {

        @Override
        public void executeAsync() {
            List<QBCustomObject> qbCustomObjectList = new ArrayList<QBCustomObject>(2);

            QBCustomObject record1 = new QBCustomObject(MOVIE_CLASS);
            record1.setCustomObjectId("53f70b55535c12201e0014d4");
            HashMap<String, Object> fields = new HashMap<String, Object>();
            fields.put("rating", 2);
            record1.setFields(fields);

            QBCustomObject record2 = new QBCustomObject(MOVIE_CLASS);
            record2.setCustomObjectId("53f3660b6fd1dfa9c43ce5a5");
            HashMap<String, Object> fields2 = new HashMap<String, Object>();
            fields2.put("rating", 2);
            record2.setFields(fields2);

            qbCustomObjectList.add(record1);
            qbCustomObjectList.add(record2);

            ArrayList<QBCustomObject> updatedObjects = null;
            Bundle params = new Bundle();
            try {
                updatedObjects = QBCustomObjects.updateObjects(qbCustomObjectList, params);
            } catch (QBResponseException e) {
                setException(e);
            }
            if (updatedObjects != null) {
                Log.i(TAG, ">>> updated objects: " + updatedObjects);
                Log.i(TAG, ">>> not found ids: " + params.getStringArrayList(com.quickblox.customobjects.Consts.NOT_FOUND_IDS));
            }
        }
    };


    //
    ////////////////////////////////// Delete Custom Object ////////////////////////////////////////
    //


    Snippet deleteCustomObject = new Snippet("delete object") {
        @Override
        public void execute() {
            QBCustomObjects.deleteObject(MOVIE_CLASS, "561b9af2a28f9a382c008d01", new QBEntityCallbackImpl() {
                @Override
                public void onSuccess() {
                    Log.i(TAG, ">>> custom object deleted OK");
                }

                @Override
                public void onError(List errors) {
                    handleErrors(errors);
                }
            });
        }
    };

    Snippet deleteCustomObjectSynchronous = new AsyncSnippet("delete object synchronous", context) {
        @Override
        public void executeAsync() {
            try {
                QBCustomObjects.deleteObject(MOVIE_CLASS, "561b9af7a0eb47fc1e000718");
                Log.i(TAG, ">>> custom object deleted OK");
            } catch (QBResponseException e) {
                setException(e);
            }
        }
    };


    //
    ////////////////////////////////// Delete Custom Objects ////////////////////////////////////////
    //


    Snippet deleteCustomObjects = new Snippet("delete objects") {
        @Override
        public void execute() {
            StringifyArrayList<String> deleteIds = new StringifyArrayList<String>();
            deleteIds.add("561b9b3ea0eb473496000a2f");
            deleteIds.add("561b9b7f05244fbde8e75d8f");

            QBCustomObjects.deleteObjects(MOVIE_CLASS, deleteIds, new QBEntityCallbackImpl<ArrayList<String>>() {

                @Override
                public void onSuccess(ArrayList<String> deletedObjects, Bundle params) {
                    Log.i(TAG, ">>> deleted: " + deletedObjects.toString());
                    ArrayList<String> notFound = params.getStringArrayList(com.quickblox.customobjects.Consts.NOT_FOUND_IDS);
                    ArrayList<String> wrongPermissions = params.getStringArrayList(com.quickblox.customobjects.Consts.WRONG_PERMISSIONS_IDS);
                    Log.i(TAG, ">>> notFound: " + notFound.toString());
                    Log.i(TAG, ">>> wrongPermissions: " + wrongPermissions.toString());
                }

                @Override
                public void onError(List<String> errors) {
                    handleErrors(errors);
                }
            });
        }
    };

    Snippet deleteCustomObjectsSynchronous = new AsyncSnippet("delete objects synchronous", context) {
        @Override
        public void executeAsync() {
            StringifyArrayList<String> deleteIds = new StringifyArrayList<String>();
            deleteIds.add("561b9b7505244fbde8e75d8d");
//            deleteIds.add("53f36e7b6fd1dfa9c43ce5a9");

            Bundle params = new Bundle();
            ArrayList<String> deleted = null;
            try {
                deleted = QBCustomObjects.deleteObjects(MOVIE_CLASS, deleteIds, params);
            } catch (QBResponseException e) {
                setException(e);
            }
            if(deleted != null){
                Log.i(TAG, ">>> deleted: " + deleted.toString());
                ArrayList<String> notFound = params.getStringArrayList(com.quickblox.customobjects.Consts.NOT_FOUND_IDS);
                ArrayList<String> wrongPermissions = params.getStringArrayList(com.quickblox.customobjects.Consts.WRONG_PERMISSIONS_IDS);
                Log.i(TAG, ">>> notFound: " + notFound.toString());
                Log.i(TAG, ">>> wrongPermissions: " + wrongPermissions.toString());
            }
        }
    };


    //
    ////////////////////////////// Get Custom Object permissions ///////////////////////////////////
    //


    Snippet getCustomObjectPermissions = new Snippet("get object permissions") {
        @Override
        public void execute() {

            QBCustomObjects.getObjectPermissions(MOVIE_CLASS, "53f44e7befa3573473000002", new QBEntityCallbackImpl<QBPermissions>() {

                @Override
                public void onSuccess(QBPermissions permissions, Bundle params) {
                    Log.i(TAG, ">>> custom object's permissions: " + permissions);
                }

                @Override
                public void onError(List<String> errors) {
                    handleErrors(errors);
                }
            });
        }
    };

    Snippet getCustomObjectPermissionsSynchronous = new AsyncSnippet("delete objects synchronous", context) {
        @Override
        public void executeAsync() {
            QBPermissions permissions = null;
            try {
                permissions = QBCustomObjects.getObjectPermissions(MOVIE_CLASS, "53f365f06fd1dfa9c43ce5a3");
            } catch (QBResponseException e) {
                setException(e);
            }
            if(permissions != null){
                Log.i(TAG, ">>> permissions: " + permissions);
            }
        }
    };


    //
    /////////////////////////////////// Update permissions ////////////////////////////////////////
    //


    Snippet updateCustomObjectPermissions = new Snippet("update permissions") {
        @Override
        public void execute() {
            QBCustomObject record = new QBCustomObject(MOVIE_CLASS, "53f365f06fd1dfa9c43ce5a3");
            //
            // update permissions:
            // READ
            QBPermissions permissions = new QBPermissions();
            permissions.setReadPermission(QBPermissionsLevel.OPEN);
            //
            // DELETE
            ArrayList<String> openPermissionsForUserIDS = new  ArrayList<String>();
            openPermissionsForUserIDS.add("33");
            openPermissionsForUserIDS.add("92");
            permissions.setDeletePermission(QBPermissionsLevel.OPEN_FOR_USER_IDS, openPermissionsForUserIDS);
            //
            // UPDATE
            ArrayList<String> openPermissionsForGroups = new  ArrayList<String>();
            openPermissionsForGroups.add("man");
            openPermissionsForGroups.add("car");
            permissions.setUpdatePermission(QBPermissionsLevel.OPEN_FOR_GROUPS, openPermissionsForGroups);
            record.setPermission(permissions);

            QBCustomObjects.updateObject(record, (QBRequestUpdateBuilder) null, new QBEntityCallbackImpl<QBCustomObject>() {
                @Override
                public void onSuccess(QBCustomObject object, Bundle params) {
                    Log.i(TAG, ">>> updated record: : " + object.toString());
                }

                @Override
                public void onError(List<String> errors) {
                    handleErrors(errors);
                }
            });
        }
    };

    Snippet updateCustomObjectPermissionsSynchronous = new AsyncSnippet("update permissions (synchronous)", context) {

        @Override
        public void executeAsync() {
            QBCustomObject obj = new QBCustomObject(MOVIE_CLASS, "53f365f06fd1dfa9c43ce5a3");
            //
            // update permissions:
            // READ
            QBPermissions permissions = new QBPermissions();
            permissions.setReadPermission(QBPermissionsLevel.OPEN);
            //
            // DELETE
            ArrayList<String> openPermissionsForUserIDS = new  ArrayList<String>();
            openPermissionsForUserIDS.add("33");
            openPermissionsForUserIDS.add("92");
            permissions.setDeletePermission(QBPermissionsLevel.OPEN_FOR_USER_IDS, openPermissionsForUserIDS);
            //
            // UPDATE
            ArrayList<String> openPermissionsForGroups = new  ArrayList<String>();
            openPermissionsForGroups.add("man");
            openPermissionsForGroups.add("car");
            permissions.setUpdatePermission(QBPermissionsLevel.OPEN_FOR_GROUPS, openPermissionsForGroups);
            obj.setPermission(permissions);

            QBCustomObject updatedObject = null;
            try {
                updatedObject = QBCustomObjects.updateObject(obj);
            } catch (QBResponseException e) {
                setException(e);
            }
            if (updatedObject != null) {
                Log.i(TAG, ">>> updated object: " + updatedObject);
            }
        }
    };


    //
    /////////////////////////////// Create object with permissions /////////////////////////////////
    //


    Snippet createCustomObjectWithPermissions = new Snippet("create object with permissions") {
        @Override
        public void execute() {
            QBCustomObject newRecord = new QBCustomObject(MOVIE_CLASS);
            newRecord.put("rating", 10);
            newRecord.put("name", "The Dark Knight");
            newRecord.put("description", "About Batman");
            //
            // set permissions:
            // READ
            QBPermissions permissions = new QBPermissions();
            permissions.setReadPermission(QBPermissionsLevel.OPEN);
            //
            // DELETE
            ArrayList<String> openPermissionsForUserIDS = new  ArrayList<String>();
            openPermissionsForUserIDS.add("33");
            openPermissionsForUserIDS.add("92");
            permissions.setDeletePermission(QBPermissionsLevel.OPEN_FOR_USER_IDS, openPermissionsForUserIDS);
            //
            // UPDATE
            permissions.setUpdatePermission(QBPermissionsLevel.OWNER);
            newRecord.setPermission(permissions);

            QBCustomObjects.createObject(newRecord, new QBEntityCallbackImpl<QBCustomObject>() {
                @Override
                public void onSuccess(QBCustomObject object, Bundle params) {
                    Log.i(TAG, ">>> created object: " + object);
                }

                @Override
                public void onError(List<String> errors) {
                    handleErrors(errors);
                }
            });
        }
    };

    Snippet createCustomObjectWithPermissionsSynchronous = new AsyncSnippet("create object with permissions(synchronous)", context) {

        @Override
        public void executeAsync() {
            QBCustomObject newRecord = new QBCustomObject(MOVIE_CLASS);
            newRecord.put("rating", 10);
            newRecord.put("name", "The Dark Knight");
            newRecord.put("description", "About Batman");
            //
            // set permissions:
            // READ
            QBPermissions permissions = new QBPermissions();
            permissions.setReadPermission(QBPermissionsLevel.OPEN);
            //
            // DELETE
            ArrayList<String> openPermissionsForUserIDS = new  ArrayList<String>();
            openPermissionsForUserIDS.add("33");
            openPermissionsForUserIDS.add("92");
            permissions.setDeletePermission(QBPermissionsLevel.OPEN_FOR_USER_IDS, openPermissionsForUserIDS);
            //
            // UPDATE
            permissions.setUpdatePermission(QBPermissionsLevel.OWNER);
            newRecord.setPermission(permissions);

            QBCustomObject createdObject = null;
            try {
                createdObject = QBCustomObjects.createObject(newRecord);
            } catch (QBResponseException e) {
                setException(e);
            }
            if (createdObject != null) {
                Log.i(TAG, ">>> created object: " + createdObject);
            }
        }
    };


    //
    ///////////////////////////////////////// Upload file //////////////////////////////////////////
    //


    Snippet uploadFile = new Snippet("upload file") {
        @Override
        public void execute() {
            QBCustomObject qbCustomObject = new QBCustomObject(MOVIE_CLASS, "54b51dadefa3574f1600000c");

            QBCustomObjectsFiles.uploadFile(imageFile, qbCustomObject, "image", new QBEntityCallbackImpl<QBCustomObjectFileField>() {

                @Override
                public void onSuccess(QBCustomObjectFileField uploadFileResult, Bundle params) {
                    Log.i(TAG, ">>>upload response:" + uploadFileResult.getFileName() + " " + uploadFileResult.getFileId() + " " +
                            uploadFileResult.getContentType());
                }

                @Override
                public void onError(List<String> errors) {
                       handleErrors(errors);
                }
            }, new QBProgressCallback() {
                @Override
                public void onProgressUpdate(int progress) {
                    Log.i(TAG, "progress: " + progress);
                }
            });
        }
    };

    Snippet uploadFileSynchronous = new AsyncSnippet("upload file synchronous", context) {
        @Override
        public void executeAsync() {
            QBCustomObject qbCustomObject = new QBCustomObject(MOVIE_CLASS, "54b51dadefa3574f1600000c");

            QBCustomObjectFileField uploadFileResult = null;
            try {
                uploadFileResult = QBCustomObjectsFiles.uploadFile(imageFile, qbCustomObject, "image", new QBProgressCallback() {
                    @Override
                    public void onProgressUpdate(int progress) {
                        Log.i(TAG, "progress: " + progress);
                    }
                });
            } catch (QBResponseException e) {
                setException(e);
            }
            if(uploadFileResult != null){
                Log.i(TAG, ">>>upload response:" + uploadFileResult.getFileName() + " " + uploadFileResult.getFileId() + " " +
                        uploadFileResult.getContentType());
            }
        }
    };


    //
    /////////////////////////////////////// Download file //////////////////////////////////////////
    //


    Snippet downloadFile = new Snippet("download file") {
        @Override
        public void execute() {
            QBCustomObject qbCustomObject = new QBCustomObject(MOVIE_CLASS, "54b51dadefa3574f1600000c");

            QBCustomObjectsFiles.downloadFile(qbCustomObject, "image", new QBEntityCallbackImpl<InputStream>(){
                @Override
                public void onSuccess(InputStream inputStream, Bundle params) {
                    Log.i(TAG, "file downloaded");
                }

                @Override
                public void onError(List<String> errors) {
                    handleErrors(errors);
                }
            }, new QBProgressCallback() {
                @Override
                public void onProgressUpdate(int progress) {
                    Log.i(TAG, "progress: " + progress);
                }
            });
        }
    };

    AsyncSnippet downloadFileSynchronous = new AsyncSnippet("download file (synchronous)", context) {
        @Override
        public void executeAsync() {
            QBCustomObject qbCustomObject = new QBCustomObject(MOVIE_CLASS, "54b51dadefa3574f1600000c");

            Bundle bundle = new Bundle();
            InputStream inputStream = null;
            try {
                inputStream = QBCustomObjectsFiles.downloadFile(qbCustomObject, "image", bundle, new QBProgressCallback() {
                    @Override
                    public void onProgressUpdate(int progress) {
                        Log.i(TAG, "progress: " + progress);
                    }
                });
            } catch (QBResponseException e) {
                setException(e);
            }
            if(inputStream != null){
                Log.i(TAG, "file downloaded");
            }
        }
    };


    //
    //////////////////////////////////////// Delete file ///////////////////////////////////////////
    //


    Snippet deleteFile = new Snippet("delete file") {
        @Override
        public void execute() {
            QBCustomObject qbCustomObject = new QBCustomObject(MOVIE_CLASS, "53f370cc535c1276290447d9");

            QBCustomObjectsFiles.deleteFile(qbCustomObject, "image", new QBEntityCallbackImpl() {

                @Override
                public void onSuccess() {
                    Log.i(TAG, "deleted successfully");
                }

                @Override
                public void onError(List errors) {
                     handleErrors(errors);
                }
            });
        }
    };

    AsyncSnippet deleteFileSynchronous = new AsyncSnippet("delete file (synchronous)", context) {
        @Override
        public void executeAsync() {
            QBCustomObject qbCustomObject = new QBCustomObject(MOVIE_CLASS, "53f370cc535c1276290447d9");

            try {
                 QBCustomObjectsFiles.deleteFile(qbCustomObject, "image");
            } catch (QBResponseException e) {
                setException(e);
            }
        }
    };
}
