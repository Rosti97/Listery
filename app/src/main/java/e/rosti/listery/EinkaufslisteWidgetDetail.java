package e.rosti.listery;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class EinkaufslisteWidgetDetail extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text_detail);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.einkaufsliste_widget_detail);


        /**TODO DATENBANK*/
        //die Einkaufsliste holen
        ArrayList<Einkaufsitem> list = new ArrayList<>();
        //Testeinträge entfernen!
        list.add(new Einkaufsitem(false,"Eier", "Max"));
        list.add(new Einkaufsitem(true, "Milch", "Ich"));
        list.add(new Einkaufsitem(true, "ne Sexpuppe", "WG"));

        //hier in der Einkaufsliste nach checked suchen (Methode kann so bleiben)
        ArrayList<Einkaufsitem> checkedList = new ArrayList<>();
        for (Einkaufsitem item: list) {
            if(item.isChecked()) {
                checkedList.add(item);
            }
        }

        //Texte setzen
        String produkte = String.valueOf(list.size());
        String anzeige = context.getString(R.string.anzeige_widget_one) + "\n" + context.getString(R.string.anzeige_widget_two);

        String checkedProdukte = String.valueOf(checkedList.size());
        String anzeigeChecked = context.getString(R.string.anzeige_widget_detail_korb_one) + "\n" +
                context.getString(R.string.anzeige_widget_detail_korb_two);

        views.setTextViewText(R.id.appwidget_text_detail, anzeige);
        views.setTextViewText(R.id.appwidget_text_detail_anzahl, produkte);

        views.setTextViewText(R.id.appwidget_text_detail_korb_anzahl, checkedProdukte);
        views.setTextViewText(R.id.appwidget_text_detail_korb_text, anzeigeChecked);

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

