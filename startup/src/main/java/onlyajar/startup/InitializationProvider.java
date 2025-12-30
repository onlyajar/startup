package onlyajar.startup;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class InitializationProvider extends ContentProvider {

    private static final String TAG = "InitializationProvider";
    @Override
    public final boolean onCreate() {
        Context context = getContext();
        if (context != null) {
            Context applicationContext = context.getApplicationContext();
            if (applicationContext != null) {
                AppInitializer.getInstance(context).discoverAndInitialize();
            } else {
                Log.w(TAG, "Deferring initialization because `applicationContext` is null.");
            }
        } else {
            throw new NullPointerException("Context cannot be null");
        }
        return true;
    }

    @Nullable
    @Override
    public final Cursor query(
            @NonNull Uri uri,
            @Nullable String[] projection,
            @Nullable String selection,
            @Nullable String[] selectionArgs,
            @Nullable String sortOrder) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Nullable
    @Override
    public final String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Nullable
    @Override
    public final Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public final int delete(
            @NonNull Uri uri,
            @Nullable String selection,
            @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public final int update(
            @NonNull Uri uri,
            @Nullable ContentValues values,
            @Nullable String selection,
            @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Not supported.");
    }
}
