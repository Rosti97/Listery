package e.rosti.listery;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class EinkaufslisteWidget extends AppWidgetProvider {

    static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {

        // Construct the RemoteViews object
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.einkaufsliste_widget_detail);

        List<Item> allItems = null;

        new AsyncTask<Void, Void, List<Item>>(){
            @Override
            protected void onPostExecute(List<Item> items) {
                super.onPostExecute(items);

                if(items != null){
                    String produkte = String.valueOf(items.size());
                    String anzeige = context.getString(R.string.anzeige_widget_one) + "\n" + context.getString(R.string.anzeige_widget_two);

                    int counter = 0;
                    for(Item temp : items){
                        if(temp.isChecked()){
                            counter++;
                        }
                    }

                    String checkedProdukte = Integer.toString(counter);
                    String anzeigeChecked = context.getString(R.string.anzeige_widget_detail_korb_one) + "\n" +
                            context.getString(R.string.anzeige_widget_detail_korb_two);

                    views.setTextViewText(R.id.appwidget_text_detail, anzeige);
                    views.setTextViewText(R.id.appwidget_text_detail_anzahl, produkte);

                    views.setTextViewText(R.id.appwidget_text_detail_korb_anzahl, checkedProdukte);
                    views.setTextViewText(R.id.appwidget_text_detail_korb_text, anzeigeChecked);
                    appWidgetManager.updateAppWidget(new ComponentName(context, EinkaufslisteWidget.class), views);

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

