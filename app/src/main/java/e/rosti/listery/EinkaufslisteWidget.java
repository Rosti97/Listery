package e.rosti.listery;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class EinkaufslisteWidget extends AppWidgetProvider {

    static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.einkaufsliste_widget);

        List<Item> allItems = null;


        /**TODO DATENBANK**/
        //Einkaufsliste holen

        new AsyncTask<Void, Void, List<Item>>(){
            @Override
            protected void onPostExecute(List<Item> items) {
                super.onPostExecute(items);

                if(items != null){
                    String produkte = "0";//String.valueOf(items.size());
                    String anzeige = context.getString(R.string.anzeige_widget_one) + "\n" + context.getString(R.string.anzeige_widget_two);

                    views.setTextViewText(R.id.appwidget_text, anzeige);
                    views.setTextViewText(R.id.appwidget_text_anzahl, produkte);
                }
            }

            @Override
            protected List<Item> doInBackground(Void... params) {
                List<Item> list = null;
                ListeryDatabase db = ListeryDatabase.getDatabase(context);
                list = db.mdaoAccess().getItems();
                return list;
            }
        }.execute();
        //Einkaufsliste nach checked suchen, Methode kann so bleiben
        /*ArrayList<Einkaufsitem> checkedList = new ArrayList<>();
        for (Einkaufsitem item: list) {
            if(item.isChecked()) {
                checkedList.add(item);
            }
        }*/
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

