package olx.olxtechathon.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListDataAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> issuesList = new ArrayList<>();


    public ListDataAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return issuesList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

//        if(convertView == null){
//            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
//            holder = new ViewHolder();
//            holder.issue_title = (TextView) convertView.findViewById(R.id.issue_title);
//            holder.issue_body = (TextView) convertView.findViewById(R.id.issue_body);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        holder.issue_title.setText(issuesList.get(position).getIssueTitle());
//        holder.issue_body.setText(issuesList.get(position).getIssueBody());
//
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!TextUtils.isEmpty(issuesList.get(position).getIssueCommentUrl())){
//                    getCommentData(issuesList.get(position).get_id(), issuesList.get(position).getIssueCommentUrl());
//                }
//            }
//        });

        return convertView;
    }


    static class ViewHolder {
        private TextView issue_title, issue_body;
    }


}
