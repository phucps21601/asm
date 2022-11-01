package com.example.ps21601.myapplication.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ps21601.myapplication.R;
import com.example.ps21601.myapplication.models.AppCourse;

import java.util.ArrayList;

public class CourseAdapter extends BaseAdapter {

    private ArrayList<AppCourse> list;
    public CourseAdapter( ArrayList<AppCourse> list){
        this.list = list;
    }
    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int _i, View _view, ViewGroup _viewgroup) {
        View view = _view;
        ImageButton btnEdit = null;
        if (view == null) {
            view = View.inflate(_viewgroup.getContext(), R.layout.layout_course_details_item, null);
            TextView txtCourseCode = view.findViewById(R.id.txtCourseCode);
            TextView txtCourseName = view.findViewById(R.id.txtCourseName);
            TextView txtCourseTime = view.findViewById(R.id.txtCourseTime);
            TextView txtCourseRoom = view.findViewById(R.id.txtCourseRoom);
            btnEdit = view.findViewById(R.id.btnEdit);
            ImageButton btnDelete = view.findViewById(R.id.btnDelete);
            ViewHolder holder = new ViewHolder(txtCourseCode, txtCourseName, txtCourseTime, txtCourseRoom, btnEdit, btnDelete);
            view.setTag(holder);
        }

        AppCourse course = (AppCourse) getItem(_i);
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.txtCourseCode.setText(course.getCode());
        holder.txtCourseName.setText(course.getName());
        holder.txtCourseTime.setText(course.getTime());
        holder.txtCourseRoom.setText(course.getRoom());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdapterClickEvent adapterClickEvent = (AdapterClickEvent) _viewgroup.getContext();
                adapterClickEvent.onDeleteCourseClick(course);
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdapterClickEvent adapterClickEvent = (AdapterClickEvent) _viewgroup.getContext();
                adapterClickEvent.onEditCourseClick(course);
            }
        });
        return view;
    }

    private static class ViewHolder{
        final TextView txtCourseCode,txtCourseName,txtCourseTime,txtCourseRoom;
        final ImageButton btnEdit,btnDelete;

        public ViewHolder(TextView txtCourseCode, TextView txtCourseName, TextView txtCourseTime, TextView txtCourseRoom,
                          ImageButton btnEdit,ImageButton btnDelete) {
            this.txtCourseCode = txtCourseCode;
            this.txtCourseName = txtCourseName;
            this.txtCourseTime = txtCourseTime;
            this.txtCourseRoom = txtCourseRoom;
            this.btnEdit = btnEdit;
            this.btnDelete = btnDelete;
        }
    }
}
