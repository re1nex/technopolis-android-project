package com.example.technopolis.screens.grouplist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technopolis.group.model.Student;
import com.example.technopolis.screens.grouplist.row.GroupListRowMvpView;
import com.example.technopolis.screens.grouplist.row.GroupListRowMvpViewImpl;

import java.util.ArrayList;
import java.util.List;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.StudentViewHolder> implements
        GroupListRowMvpView.Listener {

    static class StudentViewHolder extends RecyclerView.ViewHolder {

        private GroupListRowMvpView groupListRowMvpView;

        public StudentViewHolder(GroupListRowMvpView groupListRowMvpView) {
            super(groupListRowMvpView.getRootView());
            this.groupListRowMvpView = groupListRowMvpView;
        }
    }

    private final LayoutInflater layoutInflater;
    private final GroupListRowMvpView.Listener listener;

    private List<Student> items = new ArrayList<>();

    public GroupListAdapter(LayoutInflater layoutInflater,
                            GroupListRowMvpView.Listener listener) {
        this.layoutInflater = layoutInflater;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        GroupListRowMvpView groupListRowMvpView = new GroupListRowMvpViewImpl(layoutInflater, viewGroup, this);
        return new StudentViewHolder(groupListRowMvpView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder studentViewHolder, int position) {
        Student student = items.get(position);
        studentViewHolder.groupListRowMvpView.bindData(student);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onStudentClicked(String username) {
        listener.onStudentClicked(username);
    }

    public void bindData(List<Student> students) {
        items.clear();
        items.addAll(students);
        notifyDataSetChanged();
    }

    public void updateList(List<Student> students) {
        items = students;
        notifyDataSetChanged();
    }
}
