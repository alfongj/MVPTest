package carlosmuvi.mvptest.main;

import java.util.List;

import carlosmuvi.mvptest.entities.Beer;

/**
 * Created by Carlos on 24/11/2014.
 */
public interface OnBeersFinishedListener {

    public void onBeersSuccess(List<Beer> beers);

    public void onBeersError(String errorMsg);
}
