/*
 * MIT/X Consortium License.
 * 
 * uberspot Paul Sarbinowski.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package pl.pobiegne.mobile.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.os.Environment;


/**
 * Class containing some useful functions for easy usage of the storage capabilities in an Android device.
 */
public class StorageUtil extends ContextWrapper {
    
    /**
     * Initial constructor.
     * 
     * @param base the context of the activity calling the utilities.
     */
    public StorageUtil(final Context base) {
        super(base);
    }
    
    /**
     * Checks if a file with the given name already exists in the internal storage.
     * 
     * @param fileName the name of the file to check
     * @return true if the file exists, false otherwise
     */
    public boolean fileExists(final String fileName) {
        String[] files = fileList();
        for (String file : files) {
            if (file.equalsIgnoreCase(fileName)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Appends data to the given file.
     * 
     * @param fileName the name of the file to append data to
     * @param data an array of bytes to append to the file
     * @return true if the operation was successful, false otherwise
     */
    public boolean appendToFile(final String fileName, final byte[] data) {
        FileOutputStream fos;
        try {
            fos = openFileOutput(fileName, Context.MODE_APPEND);
            fos.write(data);
            fos.close();
        }
        catch (final Exception e) {
            return false;
        }
        return true;
    }
    
    /**
     * Returns a FileInputStream to the file in the internal storage with the given name.
     * 
     * @param fileName the name of the file
     * @return a FileInputStream to the file
     */
    public FileInputStream getInnerFileInputStream(final String fileName) {
        try {
            if (fileExists(fileName)) {
                return openFileInput(fileName);
            }
            return null;
        }
        catch (final FileNotFoundException e) {
            return null;
        }
    }
    
    /**
     * Returns a FileOutputStream to the file in the internal storage with the given name.
     * 
     * @param fileName the name of the file
     * @return a FileOutputStream to the file
     */
    public FileOutputStream getInnerFileOutputStream(final String fileName) {
        try {
            if (fileExists(fileName)) {
                return openFileOutput(fileName, Context.MODE_PRIVATE);
            }
            return null;
        }
        catch (final FileNotFoundException e) {
            return null;
        }
    }
    
    /**
     * Saves the given object in the internal storage.
     * 
     * @param obj the object to save
     * @param fileName the name of the file in which it will be saved
     * @return true if it's saved successfully, false otherwise
     */
    public boolean saveObjectToInternalStorage(final Object obj, final String fileName) {
        ObjectOutputStream output = null;
        try {
            output = new ObjectOutputStream(new BufferedOutputStream(openFileOutput(fileName, Context.MODE_PRIVATE)));
            output.writeObject(obj);
            output.flush();
            return true;
        }
        catch (final Exception e) {
            e.printStackTrace(System.out);
        }
        finally {
            if (output != null) {
                try {
                    output.close();
                }
                catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    /**
     * Loads the object stored in the given fileName.
     * 
     * @param fileName the name of the file to load
     * @return the object loaded from the file
     */
    public Object loadObjectFromInternalStorage(final String fileName) {
        Object obj = null;
        ObjectInputStream input = null;
        try {
            input = new ObjectInputStream(new BufferedInputStream(openFileInput(fileName)));
            obj = input.readObject();
        }
        catch (final Exception e) {
            e.printStackTrace(System.out);
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }
    
    /**
     * Saves a preference in the storage.
     * 
     * @param preferencesName the name of the file holding the preferences
     * @param valueName the name of the preference
     * @param value the value of the preference
     * @return true if it was saved successfully, false otherwise
     */
    public boolean savePreference(final String preferencesName, final String valueName, final String value) {
        SharedPreferences.Editor editor = getSharedPreferences(preferencesName, Context.MODE_PRIVATE).edit();
        editor.putString(valueName, value);
        return editor.commit();
    }
    
    /**
     * Loads a preference from the storage.
     * 
     * @param preferencesName the name of the file holding the preferences
     * @param valueName the name of the preference
     * @return a string containing the preference
     */
    public String getPreference(final String preferencesName, final String valueName) {
        return getSharedPreferences(preferencesName, Context.MODE_PRIVATE).getString(valueName, "");
    }
    
    /**
     * Save the given object to a file in external storage.
     * 
     * @param obj the object to save
     * @param directory the directory in the SD card to save it into
     * @param fileName the name of the file
     * @param overwrite if set to true the file will be overwritten if it already exists
     * @return true if the file was written successfully, false otherwise
     */
    public boolean saveObjectToExternalStorage(final Object obj, final String directory, final String fileName,
            final boolean overwrite) {
        String temporaryDirectory = directory;
        if (!temporaryDirectory.startsWith(File.separator)) {
            temporaryDirectory = File.separator + temporaryDirectory;
        }
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + temporaryDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, fileName);
        if (file.exists() && !overwrite) {
            return false;
        }
        ObjectOutputStream output = null;
        try {
            output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            output.writeObject(obj);
            output.flush();
            return true;
        }
        catch (final Exception e) {
            e.printStackTrace(System.out);
        }
        finally {
            if (output != null) {
                try {
                    output.close();
                }
                catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    /**
     * Loads the object with the given fileName from a file in external storage.
     * 
     * @param fileName the fileName in which the object was saved
     * @return the Object read from the file
     */
    public Object loadObjectFromExternalStorage(final String fileName) {
        String temporaryFileName = fileName;
        if (!temporaryFileName.startsWith(File.separator)) {
            temporaryFileName = File.separator + temporaryFileName;
        }
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + temporaryFileName);
        Object obj = null;
        ObjectInputStream input = null;
        try {
            input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            obj = input.readObject();
        }
        catch (final Exception e) {
            e.printStackTrace(System.out);
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }
    
    /**
     * Returns a FileInputStream to the file in the external storage with the given name.
     * 
     * @param fileName the name of the file
     * @return a FileInputStream to the file
     */
    public FileInputStream getExternalFileInputStream(final String fileName) {
        String temporaryFileName = fileName;
        if (!temporaryFileName.startsWith(File.separator)) {
            temporaryFileName = File.separator + temporaryFileName;
        }
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + temporaryFileName);
        try {
            return new FileInputStream(file);
        }
        catch (final Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
    
    /**
     * Returns a FileOutputStream to the file in the external storage with the given name.
     * 
     * @param fileName the name of the file
     * @return a FileOutputStream to the file
     */
    public FileOutputStream getExternalFileOutputStream(final String fileName) {
        String temporaryFileName = fileName;
        if (!temporaryFileName.startsWith(File.separator)) {
            temporaryFileName = File.separator + temporaryFileName;
        }
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + temporaryFileName);
        try {
            return new FileOutputStream(file);
        }
        catch (final Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
    
    /**
     * Check if device has an external storage.
     * 
     * @param requireWriteAccess boolean indicating that write access to external storage is required
     * @return true if the device has external storage. If requireWriteAccess is set to true it will also check
     *         for write permissions on the external storage. If the media isn't mounted or it is mounted as read-only
     *         when writeAccess is required it returns false.
     */
    public static boolean hasExternalStorage(final boolean requireWriteAccess) {
        String state = Environment.getExternalStorageState();
        
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        else if (!requireWriteAccess && Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    
    /**
     * Save the given string to a file in external storage.
     * 
     * @param obj the object to save
     * @param directory the directory in the SD card to save it into
     * @param fileName the name of the file
     * @param overwrite if set to true the file will be overwritten if it already exists
     * @return true if the file was written successfully, false otherwise
     */
    public boolean saveStringToExternalStorage(final String obj, final String directory, final String fileName,
            final boolean overwrite) {
        String temporaryDirectory = directory;
        if (!temporaryDirectory.startsWith(File.separator)) {
            temporaryDirectory = File.separator + temporaryDirectory;
        }
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + temporaryDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, fileName);
        if (file.exists() && !overwrite) {
            return false;
        }
        BufferedOutputStream output = null;
        try {
            output = new BufferedOutputStream(new FileOutputStream(file));
            output.write(obj.getBytes());
            output.flush();
            return true;
        }
        catch (final Exception e) {
            e.printStackTrace(System.out);
        }
        finally {
            if (output != null) {
                try {
                    output.close();
                }
                catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}