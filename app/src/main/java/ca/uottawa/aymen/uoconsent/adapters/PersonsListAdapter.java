package ca.uottawa.aymen.uoconsent.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.uottawa.aymen.uoconsent.R;
import ca.uottawa.aymen.uoconsent.Tools;
import ca.uottawa.aymen.uoconsent.activities.ProfileActivity;
import ca.uottawa.aymen.uoconsent.model.Person;

public class PersonsListAdapter extends RecyclerView.Adapter<PersonsListAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<Person> personList ;
    private List<Person> personListFull;

    public PersonsListAdapter(Context context) {
        this.context = context;
        personList = Tools.getPersonsList(context,"MyPref","jsonPersons");
        personListFull =new ArrayList<>(personList);
    }
    @NonNull
    @Override
    public PersonsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.person_item_layout, viewGroup, false);
        return new PersonsListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonsListAdapter.ViewHolder viewHolder, int i) {
        Person person = personList.get(i);
        viewHolder.name.setText(person.getName());
        viewHolder.image.setImageBitmap(BitmapFactory.decodeFile(person.getPicture()));


    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Person> filteredList = new ArrayList<>();
            if(constraint==null || constraint.length()==0){
                filteredList= personListFull;
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Person row : personListFull) {
                    if (row.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(row);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values=filteredList;
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            personList.clear();
            personList.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public ImageView image;
        public Button modify;
        public View lyt_parent;



        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            image = (ImageView) v.findViewById(R.id.image);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
            modify = (Button) v.findViewById(R.id.modify);
            modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,ProfileActivity.class);
                    intent.putExtra("position", getAdapterPosition());
                    context.startActivity(intent);
                }
            });




        }
    }
}
