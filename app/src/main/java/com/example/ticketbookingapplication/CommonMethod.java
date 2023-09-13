package com.example.ticketbookingapplication;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CommonMethod {


    CommonMethod(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }

    CommonMethod(Context context, Class<?> nextclass){
        Intent intent = new Intent(context,nextclass);
        context.startActivity(intent);

    }

}
