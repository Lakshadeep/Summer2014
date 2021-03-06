package com.niointernshipproject;
import java.io.File;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class Custom_user_selection extends BaseAdapter{
    List<String> result,name;
    Context context;
 List<String> imageId;
      private static LayoutInflater inflater=null;
    public Custom_user_selection(User_selection user_selection, List<String> users, List<String> user_idArray,List<String> user_name) {
        // TODO Auto-generated constructor stub
        result=users;
        context=user_selection;
        imageId=user_idArray;
        name = user_name;
         inflater = ( LayoutInflater )context.
                 getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
   
 
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }
 
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }
 
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    
    public class Holder
    {
        TextView tv,tvd;
        ImageView img;
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;       
             rowView = inflater.inflate(R.layout.program_list, null);
             holder.tv=(TextView) rowView.findViewById(R.id.list_details);
             holder.img=(ImageView) rowView.findViewById(R.id.profile_pic);      
             holder.tv.setText(result.get(position));
             
             holder.tvd=(TextView) rowView.findViewById(R.id.list_heading);
             holder.tvd.setText(name.get(position));
             
             File imgFile = new  File(Environment.getExternalStorageDirectory() +"/NIOApp/img"+imageId.get(position));
             if(imgFile.exists()){

                 Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                // ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);
                // myImage.setImageBitmap(myBitmap);
                 holder.img.setImageBitmap(myBitmap);

             }
         
        
        return rowView;
    }


	
 
}