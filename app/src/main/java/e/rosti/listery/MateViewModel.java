package e.rosti.listery;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class MateViewModel extends AndroidViewModel {

    private LiveData<List<Mate>> mCurrentMate;

    private DaoAccess mDao;

    public MateViewModel(Application application) {
        super(application);

        ListeryDatabase listeryDB = ListeryDatabase.getDatabase(this.getApplication());

        mDao = listeryDB.mdaoAccess();

        mCurrentMate = mDao.loadAllMates();
    }

    public LiveData<List<Mate>> getmCurrentMate() {
        if (mCurrentMate == null) {
            mCurrentMate = new LiveData<List<Mate>>() {
            };
        }
        return mCurrentMate;
    }

    public void excludeYourself() {
        mCurrentMate = mDao.loadMatesExceptYourself();
    }

    public void insertMate(Mate mate) {
        new insertAsyncTask(mDao).execute(mate);
    }

    public void deleteMate(Mate mate) {
        new deleteAsyncTask(mDao).execute(mate);
    }

    public void updateMate(Mate[] mate) {
        new updateMateAsyncTask(mDao).execute(mate);
    }

    public LiveData<Float> getCompleteBalance() {
        return mDao.loadCompleteBalance();
    }

    private static class updateMateAsyncTask extends AsyncTask<Mate[], Void, Void> {

        private DaoAccess mDao;

        updateMateAsyncTask(DaoAccess mDao) {
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(final Mate[]... params) {
            mDao.updateMates(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Mate, Void, Void> {

        private DaoAccess mDao;

        deleteAsyncTask(DaoAccess mDao) {
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(final Mate... params) {
            mDao.deleteMate(params[0]);
            return null;
        }
    }

    private static class insertAsyncTask extends AsyncTask<Mate, Void, Void> {

        private DaoAccess mDao;

        insertAsyncTask(DaoAccess mDao) {
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(final Mate... params) {
            mDao.insertMates(params[0]);
            return null;
        }
    }
}
