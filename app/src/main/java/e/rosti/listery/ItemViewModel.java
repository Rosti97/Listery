package e.rosti.listery;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private LiveData<List<Item>> allItems;

    private DaoAccess mDao;

    public ItemViewModel(Application application){
        super(application);

        ListeryDatabase listeryDB = ListeryDatabase.getDatabase(this.getApplication());

        mDao = listeryDB.mdaoAccess();

        allItems = mDao.loadAllItems();

    }

    public LiveData<List<Item>> getAllItems(){
        return allItems;
    }

    public void deleteItem(Item item){
        new ItemViewModel.deleteAsyncTask(mDao).execute(item);
    }

    public void deleteItems(Item...items){new ItemViewModel.deleteManyAsyncTask(mDao).execute(items);}

    public void insertItem(Item item){
        new ItemViewModel.insertAsyncTask(mDao).execute(item);
    }

    private static class deleteAsyncTask extends AsyncTask<Item, Void, Void> {

        private DaoAccess mDao;

        deleteAsyncTask(DaoAccess mDao){
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(final Item... params){
            mDao.deleteItem(params[0]);
            return null;
        }
    }

    private static class deleteManyAsyncTask extends AsyncTask<Item[], Void, Void> {

        private DaoAccess mDao;

        deleteManyAsyncTask(DaoAccess mDao){
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(final Item[]... params){
            mDao.deleteItems(params[0]);
            return null;
        }
    }


    private static class insertAsyncTask extends AsyncTask<Item, Void, Void> {

        private DaoAccess mDao;

        insertAsyncTask(DaoAccess mDao){
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(final Item... params){
            mDao.insertItems(params[0]);
            return null;
        }
    }
}
