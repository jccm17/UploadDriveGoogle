/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sess.testapdrive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sess.testapdrive.GoogleDriveUtils;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

/**
 *
 * @author jccm1
 */
public class FindFilesByName {

    // com.google.api.services.drive.model.File

    public static final List<File> getGoogleFilesByName(String fileNameLike) throws IOException {

        Drive driveService = GoogleDriveUtils.getDriveService();

        String pageToken = null;
        List<File> list = new ArrayList<File>();

        String query = " name contains '" + fileNameLike + "' " //
                + " and mimeType != 'application/vnd.google-apps.folder' ";

        do {
            FileList result = driveService.files().list().setQ(query).setSpaces("drive") //
                    // Fields will be assigned values: id, name, createdTime, mimeType
                    .setFields("nextPageToken, files(id, name, createdTime, mimeType)")//
                    .setPageToken(pageToken).execute();
            for (File file : result.getFiles()) {
                list.add(file);
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);
        //
        return list;
    }

    public static void main(String[] args) throws IOException {

        List<File> rootGoogleFolders = getGoogleFilesByName("test.jpg");
        for (File folder : rootGoogleFolders) {

            System.out.println("Mime Type: " + folder.getMimeType() + " --- Name: " + folder.getName());
        }

        System.out.println("Done!");
    }

}
