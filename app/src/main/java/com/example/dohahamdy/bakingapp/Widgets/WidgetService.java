package com.example.dohahamdy.bakingapp.Widgets;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViewsService;

public class WidgetService  extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {


        //Toast.makeText(context, title1,Toast.LENGTH_LONG).show()
        return new WidgetDataProvider(this, intent);
    }
    @Override
    public void onStart(Intent intent, int startId) {
        this.onGetViewFactory(intent);
        //  WidgetDataProvider widgetDataProvider=new WidgetDataProvider(this, intent);
    }


}
