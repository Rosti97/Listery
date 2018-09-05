package e.rosti.listery;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class EinkaufslisteWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.einkaufsliste_widget);


        /**TODO DATENBANK**/
        //Einkaufsliste holen
        ArrayList<Einkaufsitem> list = new ArrayList<>();
        list.add(new Einkaufsitem(false,"Eier", "Max"));
        list.add(new Einkaufsitem(true, "Milch", "Ich"));
        list.add(new Einkaufsitem(true, "ne Sexpuppe", "WG"));

        //Einkaufsliste nach checked suchen, Methode kann so bleiben
        ArrayList<Einkaufsitem> checkedList = new ArrayList<>();
        for (Einkaufsitem item: list) {
            if(item.isChecked()) {
                checkedList.add(item);
            }
        }

        String produkte = String.valueOf(list.size());
        String anzeige = context.getString(R.string.anzeige_widget_one) + "\n" + context.getString(R.string.anzeige_widget_two);

        views.setTextViewText(R.id.appwidget_text, anzeige);
        views.setTextViewText(R.id.appwidget_text_anzahl, produkte);

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

