package bj4.yhh.googledrivehelper.query;

import com.google.api.services.drive.model.FileList;

import java.util.ArrayList;
import java.util.List;

import bj4.yhh.googledrivehelper.GoogleDriveWrapper;
import bj4.yhh.googledrivehelper.QUtility;

/**
 * Created by s011208 on 2017/4/4.
 */

public class GoogleDriveAllFoldersInParentTask extends GoogleDriveTask {

    public GoogleDriveAllFoldersInParentTask(GoogleDriveWrapper wrapper, QueryCallback cb, String applicationNAme) {
        super(wrapper, cb, applicationNAme);
    }

    @Override
    protected FileList doInBackground(Void... voids) {
        try {
            List<String> conditions = new ArrayList<>();
            conditions.add("mimeType = 'application/vnd.google-apps.folder'");
            conditions.add("'" + DRIVE_FILE_ROOT + "' in parents");
            if (!getQueryTrash()) {
                conditions.add("trashed = false");
            }
            String finalCondition = QUtility.generateQCondition(conditions);
            return getDriveService().files().list()
                    .setOrderBy("folder")
                    .setQ(finalCondition)
                    .execute();
        } catch (Exception e) {
            setLastException(e);
            cancel(true);
            return null;
        }
    }
}
