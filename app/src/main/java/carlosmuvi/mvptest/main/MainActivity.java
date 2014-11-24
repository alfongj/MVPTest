package carlosmuvi.mvptest.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carlosmuvi.mvptest.R;
import carlosmuvi.mvptest.entities.Beer;


public class MainActivity extends Activity implements MainView, View.OnClickListener {

    @InjectView(R.id.button) Button showBeersButton;
    @InjectView(R.id.listView)ListView beersListView;

    private MainPresenter presenter;

    ProgressDialog progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        showBeersButton.setOnClickListener(this);

        presenter = new MainPresenterImpl(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading(String title, String desc) {
        progress = ProgressDialog.show(this, title,
                desc, true);
    }

    @Override
    public void hideLoading() {
        progress.dismiss();
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fillBeerList(List<Beer> beerList) {
        BeerAdapter adapter = new BeerAdapter(this,
                R.layout.list_item_beer, beerList);
        beersListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        presenter.getBeers();
    }



    public class BeerAdapter extends ArrayAdapter<Beer>{

        Context context;
        int layoutResourceId;
        List<Beer> data= null;

        public BeerAdapter(Context context, int layoutResourceId,  List<Beer> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            BeerHolder holder = null;

            if(row == null)
            {
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new BeerHolder(row);

                row.setTag(holder);
            }
            else
            {
                holder = (BeerHolder)row.getTag();
            }

            Beer beer = data.get(position);
            holder.txtTitle.setText(beer.getName());
            holder.txtDesc.setText(beer.getCategory());
            Picasso.with(context).load(beer.getImage_url()).error(R.drawable.placeholder).into(holder.img);
            return row;
        }

        class BeerHolder
        {
            @InjectView(R.id.txtTitle)TextView txtTitle;
            @InjectView(R.id.txtDesc)TextView txtDesc;
            @InjectView(R.id.imageView)ImageView img;

            public BeerHolder(View view){
                ButterKnife.inject(this, view);
            }
        }
    }


}
