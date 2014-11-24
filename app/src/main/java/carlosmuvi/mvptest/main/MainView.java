package carlosmuvi.mvptest.main;

import java.util.List;

import carlosmuvi.mvptest.entities.Beer;

/**
 * Created by Carlos on 24/11/2014.
 */
public interface MainView {

    public void showLoading(String title, String desc);

    public void hideLoading();

    public void showMsg(String msg);

    public void fillBeerList(List<Beer> beerList);

}
