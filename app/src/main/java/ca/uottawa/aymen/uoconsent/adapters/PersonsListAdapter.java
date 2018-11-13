package ca.uottawa.aymen.uoconsent.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.uottawa.aymen.uoconsent.ImageUtil;
import ca.uottawa.aymen.uoconsent.R;
import ca.uottawa.aymen.uoconsent.Tools;
import ca.uottawa.aymen.uoconsent.model.Person;

public class PersonsListAdapter extends RecyclerView.Adapter<PersonsListAdapter.ViewHolder> {

    private Context context;
    private List<Person> personList = new ArrayList<>();

    public PersonsListAdapter(Context context) {
        this.context = context;
        personList = Tools.getPersonsList(context);
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
        viewHolder.image.setImageBitmap(ImageUtil.convert(person.getPicture()));


    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

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




        }
    }
}
